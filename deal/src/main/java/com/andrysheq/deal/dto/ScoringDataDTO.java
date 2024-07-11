package com.andrysheq.deal.dto;

import com.andrysheq.deal.entity.Application;
import com.andrysheq.deal.enums.Gender;
import com.andrysheq.deal.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для скоринговой системы")
public class ScoringDataDTO {

    @Schema(description = "Сумма кредита", example = "1000000.00")
    @NotNull
    private BigDecimal amount;

    @Schema(description = "Срок кредитования", example = "12")
    @NotNull
    private Integer term;

    @Schema(description = "Имя клиента", example = "Andrey")
    @NotNull
    private String firstName;

    @Schema(description = "Фамилия клиента", example = "Andreev")
    @NotNull
    private String lastName;

    @Schema(description = "Отчество клиента", example = "Andreeevich")
    @NotNull
    private String middleName;

    @Schema(description = "Пол", example = "MALE")
    @NotNull
    private Gender gender;

    @Schema(description = "Дата рождения клиента", example = "2002-07-01")
    @NotNull
    private LocalDate birthDate;

    @Schema(description = "Серия паспорта", example = "1325")
    @NotNull
    private String passportSeries;

    @Schema(description = "Номер паспорта", example = "132435")
    @NotNull
    private String passportNumber;

    @Schema(description = "Дата выдачи паспорта", example = "2022-07-01")
    @NotNull
    private LocalDate passportIssueDate;

    @Schema(description = "Отделение выдачи паспорта", example = "ОМВД РОССИИ ПО ПЕНЗЕНСКОЙ ОБЛАСТИ")
    @NotNull
    private String passportIssueBranch;

    @Schema(description = "Семейное положение", example = "MARRIED")
    @NotNull
    private MaritalStatus maritalStatus;

    @Schema(description = "Количество иждивенцев", example = "1")
    @NotNull
    private Integer dependentAmount;

    @Schema(description = "Информация о трудоустройстве клиента",
            example = "[{\"employmentStatus\":\"UNEMPLOYED\",{\"employerInn\":\"123456789123\",\"salary\":12345.00}," +
                    "{\"position\":\"TOP_MANAGER\",\"workExperienceTotal\":24},\"workExperienceCurrent\":12}]")
    @NotNull
    private EmploymentDTO employment;

    @Schema(description = "Идентификатор аккаунта", example = "andrey11")
    @NotNull
    private String account;

    @Schema(description = "Наличие страхования кредита", example = "true")
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(description = "Наличие заработной платы", example = "true")
    @NotNull
    private Boolean isSalaryClient;

    public ScoringDataDTO(FinishRegistrationRequestDTO request, Application application){
        account = request.getAccount();
        amount = application.getClient().getAmount();
        gender = request.getGender();
        employment = request.getEmployment();
        birthDate = application.getClient().getBirthDate();
        firstName = application.getClient().getFirstName();
        middleName = application.getClient().getMiddleName();
        lastName = application.getClient().getLastName();
        dependentAmount = request.getDependentAmount();
        isInsuranceEnabled = application.getLoanOffer().getIsInsuranceEnabled();
        isSalaryClient = application.getLoanOffer().getIsSalaryClient();
        maritalStatus = request.getMaritalStatus();
        passportIssueBranch = request.getPassportIssueBranch();
        passportIssueDate = request.getPassportIssueDate();
        passportSeries = application.getClient().getPassportSeries();
        passportNumber = application.getClient().getPassportNumber();
        term = application.getClient().getTerm();
    }
}
