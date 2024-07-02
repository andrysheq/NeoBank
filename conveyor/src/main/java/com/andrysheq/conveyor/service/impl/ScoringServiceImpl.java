package com.andrysheq.conveyor.service.impl;

import com.andrysheq.conveyor.dto.CreditDTO;
import com.andrysheq.conveyor.dto.PaymentScheduleElement;
import com.andrysheq.conveyor.dto.ScoringDataDTO;
import com.andrysheq.conveyor.enums.EmploymentStatus;
import com.andrysheq.conveyor.exception.AgeNotAllowedException;
import com.andrysheq.conveyor.exception.ExcessiveLoanAmountException;
import com.andrysheq.conveyor.exception.InsufficientWorkExperienceException;
import com.andrysheq.conveyor.exception.UnemployedException;
import com.andrysheq.conveyor.service.ScoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScoringServiceImpl implements ScoringService {

    @Value("${default-rate}")
    private BigDecimal defaultRate;

    @Value("${base-period-per-year}")
    private Integer basePeriodPerYear;

    @Value("${base-period}")
    private Integer basePeriod;
    public CreditDTO scoring(ScoringDataDTO request){
        checkLoanEligibility(request);
        CreditDTO result = new CreditDTO();

        BigDecimal amount = request.getAmount();
        Integer term = request.getTerm();
        Boolean isInsuranceEnabled = request.getIsInsuranceEnabled(), isSalaryClient = request.getIsSalaryClient();
        BigDecimal rate = calculateRate(request);
        BigDecimal monthlyPayment = calculateMonthlyPayment(amount,rate,term);
        List<PaymentScheduleElement> paymentSchedule = generatePaymentSchedule(amount, term, rate,monthlyPayment);
        BigDecimal psk = getPsk(paymentSchedule, amount, rate);

        result.setAmount(amount);
        result.setTerm(term);
        result.setIsInsuranceEnabled(isInsuranceEnabled);
        result.setIsSalaryClient(isSalaryClient);
        result.setRate(rate);
        result.setMonthlyPayment(monthlyPayment);
        result.setPaymentSchedule(paymentSchedule);
        result.setPsk(psk);

        return result;
    }

    public BigDecimal calculateMonthlyPayment(BigDecimal loanAmount, BigDecimal annualInterestRate, int termInMonths) {
        // Преобразуем годовую процентную ставку в месячную (учитывая, что ставка дана в процентах)
        BigDecimal monthlyInterestRate = annualInterestRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP).divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        // (1 + r)^n
        BigDecimal onePlusRateToTerm = BigDecimal.ONE.add(monthlyInterestRate).pow(termInMonths);

        // P * (r * (1 + r)^n) / ((1 + r)^n - 1)
        BigDecimal numerator = loanAmount.multiply(monthlyInterestRate).multiply(onePlusRateToTerm);
        BigDecimal denominator = onePlusRateToTerm.subtract(BigDecimal.ONE);

        // Аннуитетный платеж
        BigDecimal annuityPayment = numerator.divide(denominator, 10, RoundingMode.HALF_UP);

        return annuityPayment.setScale(2, RoundingMode.HALF_UP); // Возвращаем округленный ежемесячный платеж
    }

    public BigDecimal getPsk(List<PaymentScheduleElement> schedule, BigDecimal loanAmount, BigDecimal annualInterestRate) {
        if (schedule == null || schedule.isEmpty()) {
            return BigDecimal.ZERO;
        }

        int periodsPerYear = 12; // предположим, что периодичность годовая
        BigDecimal high = new BigDecimal("1.0");
        BigDecimal low = BigDecimal.ZERO;
        BigDecimal precision = new BigDecimal("0.000001");
        BigDecimal periodPerYear = BigDecimal.valueOf(365);

        while (high.subtract(low).compareTo(precision) > 0) {
            BigDecimal mid = low.add(high).divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP);
            BigDecimal npv = BigDecimal.ZERO;

            for (PaymentScheduleElement payment : schedule) {
                long daysBetween = ChronoUnit.DAYS.between(schedule.get(0).getDate(), payment.getDate());
                BigDecimal days = BigDecimal.valueOf(daysBetween);
                BigDecimal denominator = BigDecimal.ONE.add(mid).pow(days.divide(periodPerYear, 10, RoundingMode.HALF_UP).intValue(), new MathContext(10, RoundingMode.HALF_UP));
                npv = npv.add(payment.getTotalPayment().divide(denominator, 10, RoundingMode.HALF_UP));
            }

            // Учитываем сумму кредита как начальный денежный поток (отрицательный)
            npv = npv.subtract(loanAmount);

            if (npv.compareTo(BigDecimal.ZERO) > 0) {
                low = mid;
            } else {
                high = mid;
            }
        }

        BigDecimal psk = low.multiply(BigDecimal.valueOf(periodsPerYear)).setScale(3, RoundingMode.HALF_UP);
        return psk;
    }




    public List<PaymentScheduleElement> generatePaymentSchedule(BigDecimal loanAmount, int termInMonths, BigDecimal annualInterestRate, BigDecimal annuityPayment) {
        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

        // Преобразуем годовую процентную ставку в месячную (учитывая, что ставка дана в процентах)
        BigDecimal monthlyInterestRate = annualInterestRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP).divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        // (1 + r)^n
        BigDecimal onePlusRateToTerm = BigDecimal.ONE.add(monthlyInterestRate).pow(termInMonths);

        BigDecimal remainingDebt = loanAmount;

        for (int i = 1; i <= termInMonths; i++) {
            LocalDate paymentDate = LocalDate.now().plusMonths(i);
            BigDecimal interestPayment = remainingDebt.multiply(monthlyInterestRate);
            BigDecimal debtPayment = annuityPayment.subtract(interestPayment);
            remainingDebt = remainingDebt.subtract(debtPayment);

            PaymentScheduleElement element = PaymentScheduleElement.builder()
                    .number(i)
                    .date(paymentDate)
                    .totalPayment(annuityPayment.setScale(2, RoundingMode.HALF_UP))
                    .interestPayment(interestPayment.setScale(2, RoundingMode.HALF_UP))
                    .debtPayment(debtPayment.setScale(2, RoundingMode.HALF_UP))
                    .remainingDebt(remainingDebt.setScale(2, RoundingMode.HALF_UP))
                    .build();

            paymentSchedule.add(element);
        }

        return paymentSchedule;
    }

    public BigDecimal calculateRate(ScoringDataDTO request){
        BigDecimal result = defaultRate;
        int age = Period.between(request.getBirthDate(), LocalDate.now()).getYears();

        switch(request.getEmployment().getEmploymentStatus()){
            case SELF_EMPLOYED -> {
                result = result.add(new BigDecimal(1));
            }
            case BUSINESS_OWNER -> {
                result = result.add(new BigDecimal(3));
            }
        }

        switch (request.getEmployment().getPosition()){
            case MIDDLE_MANAGER -> {
                result = result.subtract(new BigDecimal(2));
            }
            case TOP_MANAGER -> {
                result = result.subtract(new BigDecimal(4));
            }
        }

        switch (request.getMaritalStatus()){
            case MARRIED -> {
                result = result.subtract(new BigDecimal(3));
            }
            case DIVORCED -> {
                result = result.add(new BigDecimal(1));
            }
        }

        switch (request.getGender()){
            case MALE -> {
                if(age>=30 && age<=55)
                    result = result.subtract(new BigDecimal(3));
            }
            case FEMALE -> {
                if(age>=35 && age<=60)
                    result = result.subtract(new BigDecimal(3));
            }
            case NON_BINARY -> {
                result = result.add(new BigDecimal(3));
            }
        }

        if(request.getDependentAmount()>1){
            result = result.add(new BigDecimal(1));
        }

        return result;
    }

    public void checkLoanEligibility(ScoringDataDTO request) {
        if (request.getEmployment().getEmploymentStatus() == EmploymentStatus.UNEMPLOYED) {
            throw new UnemployedException();
        }

        if (request.getAmount().compareTo(request.getEmployment().getSalary().multiply(new BigDecimal(20))) > 0) {
            throw new ExcessiveLoanAmountException();
        }

        int age = Period.between(request.getBirthDate(), LocalDate.now()).getYears();
        if (age < 20 || age > 60) {
            throw new AgeNotAllowedException();
        }

        if (request.getEmployment().getWorkExperienceTotal() < 12 || request.getEmployment().getWorkExperienceCurrent() < 3) {
            throw new InsufficientWorkExperienceException();
        }
    }
}
