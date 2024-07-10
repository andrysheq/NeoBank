package com.andrysheq.deal.controller;

import com.andrysheq.deal.dto.*;
import com.andrysheq.deal.dto.error.ErrorResponse;
import com.andrysheq.deal.enums.*;
import com.andrysheq.deal.entity.Application;
import com.andrysheq.deal.entity.Client;
import com.andrysheq.deal.entity.Credit;
import com.andrysheq.deal.feign.DealFeignClient;
import com.andrysheq.deal.mapper.BaseMapper;
import com.andrysheq.deal.repo.service.ApplicationRepoService;
import com.andrysheq.deal.repo.service.ClientRepoService;
import com.andrysheq.deal.repo.service.CreditRepoService;
import com.andrysheq.deal.service.ApplicationService;
import com.andrysheq.deal.service.ClientService;
import com.andrysheq.deal.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@RequestMapping()
@Tag(
        name = "Контроллер для работы с deal-api",
        description = "Все методы для работы с deal-api"
)
public class DealController {

    private final ClientService clientService;
    private final DealFeignClient dealFeignClient;
    private final ApplicationService applicationService;
    private final ApplicationRepoService applicationRepoService;
    private final BaseMapper mapper;
    private final CreditRepoService creditRepoService;
    private final ClientRepoService clientRepoService;
    private static final String APPLICATION_URL = "/deal/application";
    private static final String OFFER_URL = "/deal/offer";
    private static final String CALCULATE_URL = "/deal/calculate";
    private static final String SIGN_DOCUMENTS_URL = "deal/document/{applicationId}/sign";
    private static final String CODE_DOCUMENTS_URL = "deal/document/{applicationId}/code";

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

        return dealFeignClient.getOffers(request, application.getId());
    }

    @Operation(
            summary = "Выбор одного из предложений."
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

        application.setLoanOffer(request);
        application.setStatus(Status.PREAPPROVAL);

        return applicationRepoService.update(application);
    }

    @Operation(
            summary = "Завершение регистрации + полный подсчёт кредита."
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
        Client client = application.getClient();

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
        scoringDataDTO.setIsInsuranceEnabled(application.getLoanOffer().getIsInsuranceEnabled());
        scoringDataDTO.setIsSalaryClient(application.getLoanOffer().getIsSalaryClient());
        scoringDataDTO.setMaritalStatus(request.getMaritalStatus());
        scoringDataDTO.setPassportIssueBranch(request.getPassportIssueBranch());
        scoringDataDTO.setPassportIssueDate(request.getPassportIssueDate());
        scoringDataDTO.setPassportSeries(application.getClient().getPassportSeries());
        scoringDataDTO.setPassportNumber(application.getClient().getPassportNumber());
        scoringDataDTO.setTerm(application.getClient().getTerm());

        CreditDTO creditDTO = dealFeignClient.scoring(scoringDataDTO);

        Credit credit = mapper.map(creditDTO, Credit.class);
        credit.setCreditStatus(CreditStatus.CALCULATED);

        creditRepoService.save(credit);
        application.setStatus(Status.CC_APPROVED);
        application.setCredit(credit);

        client.setGender(request.getGender());
        client.setAccount(request.getAccount());
        client.setEmployment(request.getEmployment());
        client.setDependentAmount(request.getDependentAmount());
        client.setIsSalaryClient(application.getLoanOffer().getIsSalaryClient());
        client.setIsInsuranceEnabled(application.getLoanOffer().getIsInsuranceEnabled());
        client.setMaritalStatus(request.getMaritalStatus());
        client.setPassportIssueDate(request.getPassportIssueDate());
        client.setPassportIssueBranch(request.getPassportIssueBranch());

        clientRepoService.update(client);
        application.setClient(client);

        signDocuments(application.getId());

        return applicationRepoService.update(application);
    }

    @Operation(
            summary = "Отправка кода подтверждения на почту клиента."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class))
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
            path = SIGN_DOCUMENTS_URL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> signDocuments(@PathVariable Long applicationId)
    {
        return applicationService.signDocuments(applicationId);
    }

    @Operation(
            summary = "Подписание документов."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class))
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
            path = CODE_DOCUMENTS_URL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Application codeDocuments(
            @PathVariable Long applicationId,
            @Parameter(description = "sesCode", required = true) @RequestParam String sesCode)
    {
        return applicationService.codeDocuments(applicationId,sesCode);
    }
}
