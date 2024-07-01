package com.andrysheq.conveyor.service.impl;

import com.andrysheq.conveyor.dto.CreditDTO;
import com.andrysheq.conveyor.dto.PaymentScheduleElement;
import com.andrysheq.conveyor.dto.ScoringDataDTO;
import com.andrysheq.conveyor.service.ScoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
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
        CreditDTO result = new CreditDTO();
        result.setAmount(request.getAmount());
        result.setTerm(request.getTerm());
        result.setIsInsuranceEnabled(request.getIsInsuranceEnabled());
        result.setIsSalaryClient(request.getIsSalaryClient());
        BigDecimal rate = defaultRate;

        switch(request.getEmployment().getEmploymentStatus()){
            case UNEMPLOYED ->{
                result=null;
            }
            case SELF_EMPLOYED -> {
                rate.add(new BigDecimal(1));
            }
            case BUSINESS_OWNER -> {
                rate.add(new BigDecimal(3));
            }
        }

        switch (request.getEmployment().getPosition()){
            case MIDDLE_MANAGER -> {
                rate.subtract(new BigDecimal(2));
            }
            case TOP_MANAGER -> {
                rate.subtract(new BigDecimal(4));
            }
        }

        if(request.getAmount().compareTo(request.getEmployment().getSalary().multiply(new BigDecimal(20)))>0)
            result=null;

        switch (request.getMaritalStatus()){
            case MARRIED -> {
                rate.subtract(new BigDecimal(3));
            }
            case DIVORCED -> {
                rate.add(new BigDecimal(1));
            }
        }

        int age = Period.between(request.getBirthDate(), LocalDate.now()).getYears();

        if(age<20 || age>60){
            result=null;
        }

        switch (request.getGender()){
            case MALE -> {
                if(age>=30 && age<=55)
                    rate.subtract(new BigDecimal(3));
            }
            case FEMALE -> {
                if(age>=35 && age<=60)
                    rate.subtract(new BigDecimal(3));
            }
            case NON_BINARY -> {
                rate.add(new BigDecimal(3));
            }
        }

        if(request.getEmployment().getWorkExperienceTotal()<12 || request.getEmployment().getWorkExperienceCurrent()<3)
            result=null;

        result.setRate(rate);
        BigDecimal monthlyPayment = calculateMonthlyPayment(request.getAmount(),rate,request.getTerm());//request.getAmount().multiply(rate.add(rate.divide(rate.add(new BigDecimal(1))).multiply(new BigDecimal(request.getTerm())).subtract(new BigDecimal(1))));

        result.setMonthlyPayment(monthlyPayment);

        List<PaymentScheduleElement> paymentSchedule = generatePaymentSchedule(result.getAmount(), result.getTerm(), rate);

        result.setPaymentSchedule(paymentSchedule);

        result.setPsk(getPsk(paymentSchedule,result.getMonthlyPayment()));

        return result;
    }

    public BigDecimal calculateMonthlyPayment(BigDecimal loanAmount, BigDecimal annualInterestRate, int termInMonths) {
        // Convert annual interest rate to monthly rate
        BigDecimal monthlyInterestRate = annualInterestRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        // Calculate (1 + monthlyInterestRate)^termInMonths
        BigDecimal onePlusRateToTerm = BigDecimal.ONE.add(monthlyInterestRate).pow(termInMonths);

        // Calculate monthly payment using annuity formula
        BigDecimal annuityPayment = loanAmount
                .multiply(monthlyInterestRate)
                .multiply(onePlusRateToTerm)
                .divide(onePlusRateToTerm.subtract(BigDecimal.ONE), 10, RoundingMode.HALF_UP);

        return annuityPayment.setScale(2, RoundingMode.HALF_UP); // Return rounded monthly payment
    }

    public BigDecimal getPsk(List<PaymentScheduleElement> dates, BigDecimal monthlyPayment) {
        int m = dates.size(); // число платежей

        // Заполним массив с количеством дней с даты выдачи до даты k-го платежа
        long[] days = new long[m];
        for (int k = 0; k < m; k++) {
            days[k] = ChronoUnit.DAYS.between(dates.get(0).getDate(), dates.get(k).getDate());
        }

        // Посчитаем Ek и Qk для каждого платежа
        BigDecimal[] e = new BigDecimal[m];
        long[] q = new long[m];
        for (int k = 0; k < m; k++) {
            e[k] = BigDecimal.valueOf(days[k] % basePeriod).divide(BigDecimal.valueOf(basePeriod), 10, RoundingMode.HALF_UP);
            q[k] = days[k] / basePeriod;
        }

        // Втупую методом перебора начиная с 0 ищем i до максимального приближения с шагом s
        BigDecimal i = BigDecimal.ZERO;
        BigDecimal x = BigDecimal.ONE;
        BigDecimal x_m = BigDecimal.ZERO;
        BigDecimal s = new BigDecimal("0.000001");
        BigDecimal maxPrecision = new BigDecimal("0.0001");

        while (x.compareTo(BigDecimal.ZERO) > 0) {
            x_m = x;
            x = BigDecimal.ZERO;

            for (int k = 0; k < m; k++) {
                BigDecimal numerator = monthlyPayment.divide(BigDecimal.ONE.add(e[k].multiply(i)), 10, RoundingMode.HALF_UP);
                BigDecimal denominator = BigDecimal.ONE.add(i).pow((int) q[k]);
                x = x.add(numerator.divide(denominator, 10, RoundingMode.HALF_UP));
            }
            i = i.add(s);

            // Ограничение для точности
            if (s.compareTo(maxPrecision) < 0) {
                break;
            }
        }

        // Считаем ПСК
        BigDecimal psk = i.multiply(BigDecimal.valueOf(basePeriodPerYear)).setScale(3, RoundingMode.DOWN);

        return psk;
    }

    public List<PaymentScheduleElement> generatePaymentSchedule(BigDecimal loanAmount, int termInMonths, BigDecimal annualInterestRate) {
        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

        // Конвертируем годовую процентную ставку в ежемесячную
        BigDecimal monthlyInterestRate = annualInterestRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        // Расчет аннуитетного платежа
        BigDecimal onePlusRateToTerm = monthlyInterestRate.add(BigDecimal.ONE).pow(termInMonths);
        BigDecimal annuityPayment = loanAmount.multiply(monthlyInterestRate).multiply(onePlusRateToTerm)
                .divide(onePlusRateToTerm.subtract(BigDecimal.ONE), 10, RoundingMode.HALF_UP);

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
}
