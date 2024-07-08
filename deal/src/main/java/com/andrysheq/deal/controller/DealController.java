package com.andrysheq.deal.controller;

import com.andrysheq.conveyor.dto.*;
import com.andrysheq.conveyor.dto.error.ErrorResponse;
import com.andrysheq.conveyor.enums.CreditStatus;
import com.andrysheq.conveyor.enums.Status;
import com.andrysheq.deal.entity.Application;
import com.andrysheq.deal.entity.Client;
import com.andrysheq.deal.entity.Credit;
import com.andrysheq.deal.feign.DealFeignClient;
import com.andrysheq.deal.repo.service.ApplicationRepoService;
import com.andrysheq.deal.repo.service.ClientRepoService;
import com.andrysheq.deal.repo.service.CreditRepoService;
import com.andrysheq.deal.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@RequestMapping()
@Tag(
        name = "Контроллер для работы с авторами",
        description = "Все методы для работы с авторами"
)
public class DealController {

    private final ClientService clientService;
    private final DealFeignClient dealFeignClient;
    private final ApplicationRepoService applicationRepoService;
    private final CreditRepoService creditRepoService;
    private final ClientRepoService clientRepoService;
    private static final String APPLICATION_URL = "/deal/application";
    private static final String OFFER_URL = "/deal/offer";
    private static final String CALCULATE_URL = "/deal/calculate";

    @Operation(
            summary = "Расчёт возможных условий кредита."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CreditDTO.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @PostMapping(
            path = APPLICATION_URL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public List<LoanOfferDTO> application(
            @Parameter(name = "LoanApplicationRequestDTO", required = true) @Valid @RequestBody LoanApplicationRequestDTO request) {

        Client client = clientService.getClient(request);
        clientRepoService.save(client);

        Application application = new Application(client);
        applicationRepoService.save(application);

        return dealFeignClient.preScoring(request, application.getId());
    }

    @Operation(
            summary = "Расчёт возможных условий кредита."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CreditDTO.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @PostMapping(
            path = OFFER_URL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Application offer(
            @Parameter(name = "LoanOfferDTO", required = true) @Valid @RequestBody LoanOfferDTO request) {

        Application application = applicationRepoService.findById(request.getApplicationId());

        application.setAppliedOffer(request);
        application.setStatus(Status.PREAPPROVAL);


        return applicationRepoService.save(application);
    }

    @Operation(
            summary = "Расчёт возможных условий кредита."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CreditDTO.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @PostMapping(
            path = CALCULATE_URL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Application calculate(
            @Parameter(description = "FinishRegistrationRequestDTO", required = true) @Valid @RequestBody FinishRegistrationRequestDTO request,
            @Parameter(description = "ApplicationID", required = true) @RequestParam Long applicationId) {

        Application application = applicationRepoService.findById(applicationId);

        ScoringDataDTO scoringDataDTO = new ScoringDataDTO();

        scoringDataDTO.setAccount(request.getAccount());
        scoringDataDTO.setAmount(application.getClient().getAmount());
        scoringDataDTO.setGender(request.getGender());
        scoringDataDTO.setEmployment(request.getEmployment());
        scoringDataDTO.setBirthDate(application.getClient().getBirthDate());
        scoringDataDTO.setFirstName(application.getClient().getFirstName());
        scoringDataDTO.setMiddleName(application.getClient().getMiddleName());
        scoringDataDTO.setLastName(application.getClient().getLastName());
        scoringDataDTO.setDependentAmount(request.getDependentAmount());
        scoringDataDTO.setIsInsuranceEnabled(application.getClient().getIsInsuranceEnabled());
        scoringDataDTO.setIsSalaryClient(application.getClient().getIsSalaryClient());
        scoringDataDTO.setMaritalStatus(request.getMaritalStatus());
        scoringDataDTO.setPassportIssueBranch(request.getPassportIssueBranch());
        scoringDataDTO.setPassportIssueDate(request.getPassportIssueDate());
        scoringDataDTO.setPassportSeries(application.getClient().getPassportSeries());
        scoringDataDTO.setPassportNumber(application.getClient().getPassportNumber());
        scoringDataDTO.setTerm(application.getClient().getTerm());

        CreditDTO creditDTO = dealFeignClient.scoring(scoringDataDTO);

        Credit credit = new Credit();
        credit.setAmount(creditDTO.getAmount());
        credit.setCreditStatus(CreditStatus.CALCULATED);
        credit.setIsSalaryClient(creditDTO.getIsSalaryClient());
        credit.setTerm(creditDTO.getTerm());
        credit.setRate(creditDTO.getRate());
        credit.setMonthlyPayment(creditDTO.getMonthlyPayment());
        credit.setIsInsuranceEnabled(creditDTO.getIsInsuranceEnabled());
        credit.setPaymentSchedule(creditDTO.getPaymentSchedule());
        credit.setPsk(creditDTO.getPsk());

        creditRepoService.save(credit);
        application.setStatus(Status.CC_APPROVED);
        application.setCredit(credit);

        return applicationRepoService.update(application);
    }
}
