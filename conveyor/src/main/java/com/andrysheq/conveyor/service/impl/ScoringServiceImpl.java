package com.andrysheq.conveyor.service.impl;

import com.andrysheq.conveyor.dto.*;
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
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScoringServiceImpl implements ScoringService {

    @Value("${default-rate}")
    private BigDecimal defaultRate;
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
        BigDecimal monthlyInterestRate = annualInterestRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP).divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        BigDecimal onePlusRateToTerm = BigDecimal.ONE.add(monthlyInterestRate).pow(termInMonths);

        BigDecimal numerator = loanAmount.multiply(monthlyInterestRate).multiply(onePlusRateToTerm);
        BigDecimal denominator = onePlusRateToTerm.subtract(BigDecimal.ONE);

        BigDecimal annuityPayment = numerator.divide(denominator, 10, RoundingMode.HALF_UP);

        return annuityPayment.setScale(2, RoundingMode.HALF_UP);
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

        BigDecimal monthlyInterestRate = annualInterestRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP).divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

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

    public List<LoanOfferDTO> getLoanOffers(LoanApplicationRequestDTO request, Long applicationId){
        List<LoanOfferDTO> result = new ArrayList<>();

        BigDecimal amount = request.getAmount();
        Integer term = request.getTerm();

        LoanOfferDTO notInsuranceNotSalary = getLoanOffer(applicationId, amount, term, false,false);
        LoanOfferDTO notInsuranceIsSalary = getLoanOffer(applicationId, amount, term, false,true);
        LoanOfferDTO isInsuranceNotSalary = getLoanOffer(applicationId, amount, term, true,false);
        LoanOfferDTO isInsuranceIsSalary = getLoanOffer(applicationId, amount, term, true,true);

        Collections.addAll(result, notInsuranceIsSalary, notInsuranceNotSalary, isInsuranceIsSalary, isInsuranceNotSalary);
        return result;
    }

    public LoanOfferDTO getLoanOffer(Long applicationId, BigDecimal amount, Integer term, Boolean isInsuranceEnabled, Boolean isSalaryClient){
        LoanOfferDTO result = new LoanOfferDTO();

        result.setApplicationId(applicationId);
        result.setIsInsuranceEnabled(isInsuranceEnabled);
        result.setIsSalaryClient(isSalaryClient);
        result.setRequestedAmount(amount);
        result.setTerm(term);

        BigDecimal rate = defaultRate;
        BigDecimal totalAmount;

        if (isInsuranceEnabled) {
            rate = rate.subtract(new BigDecimal(3));
            totalAmount = amount.add(new BigDecimal(100000));
        } else {
            totalAmount = amount;
        }

        if(isSalaryClient){
            rate = rate.subtract(new BigDecimal(1));
        }

        BigDecimal monthlyPayment = calculateMonthlyPayment(totalAmount, rate, term);

        result.setRate(rate);
        result.setTotalAmount(totalAmount);
        result.setMonthlyPayment(monthlyPayment);
        return result;
    }
}
