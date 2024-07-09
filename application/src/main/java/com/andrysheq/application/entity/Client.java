package com.andrysheq.application.entity;

import com.andrysheq.application.dto.EmploymentDTO;
import com.andrysheq.application.enums.Gender;
import com.andrysheq.application.enums.MaritalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "term")
    private Integer term;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "Gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "passport_issue_date")
    private LocalDate passportIssueDate;

    @Column(name = "passport_issue_branch")
    private String passportIssueBranch;

    @Column(name = "marital_status")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "dependent_amount")
    private Integer dependentAmount;

    @Column(name = "employment")
    @Embedded
    private EmploymentDTO employment;

    @Column(name = "account")
    private String account;

    @Column(name = "email")
    private String email;

    @Column(name = "is_insurance_enabled")
    private Boolean isInsuranceEnabled;

    @Column(name = "is_salary_client")
    private Boolean isSalaryClient;

}
