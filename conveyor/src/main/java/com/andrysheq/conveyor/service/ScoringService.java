package com.andrysheq.conveyor.service;

import com.andrysheq.conveyor.dto.*;
import com.andrysheq.conveyor.enums.EmploymentStatus;
import com.andrysheq.conveyor.exception.AgeNotAllowedException;
import com.andrysheq.conveyor.exception.ExcessiveLoanAmountException;
import com.andrysheq.conveyor.exception.InsufficientWorkExperienceException;
import com.andrysheq.conveyor.exception.UnemployedException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public interface ScoringService {
    CreditDTO scoring(ScoringDataDTO request);
    public BigDecimal calculateMonthlyPayment(BigDecimal loanAmount, BigDecimal annualInterestRate, int termInMonths);
    public BigDecimal getPsk(List<PaymentScheduleElement> dates, BigDecimal loanAmount, BigDecimal rate);
    public List<PaymentScheduleElement> generatePaymentSchedule(BigDecimal loanAmount, int termInMonths, BigDecimal annualInterestRate, BigDecimal annuityPayment);
    public BigDecimal calculateRate(ScoringDataDTO request);
    public void checkLoanEligibility(ScoringDataDTO request);
    List<LoanOfferDTO> getLoanOffers(LoanApplicationRequestDTO request, Long applicationId);
}
