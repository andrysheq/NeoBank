package com.andrysheq.conveyor.controllers;

import com.andrysheq.conveyor.dto.*;
import com.andrysheq.conveyor.dto.error.ErrorResponse;
import com.andrysheq.conveyor.enums.EmploymentStatus;
import com.andrysheq.conveyor.service.ScoringService;
import com.andrysheq.conveyor.utils.RestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static com.andrysheq.conveyor.enums.MaritalStatus.DIVORCED;
import static com.andrysheq.conveyor.enums.MaritalStatus.MARRIED;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping()
@Tag(
        name = "Контроллер для работы с авторами",
        description = "Все методы для работы с авторами"
)
public class ConveyorController {

    private ScoringService scoringService;
    private static final String OFFERS_URL = "/conveyor/offers";
    private static final String CALCULATION_URL = "/conveyor/calculation";

    @Operation(
            summary = "Расчёт возможных условий кредита"
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
            path = OFFERS_URL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public List<LoanOfferDTO> preScoring(
            @Parameter(name = "ScoringDataDTO", required = true) @Valid @RequestBody LoanApplicationRequestDTO request) {

        return null;
    }

    @Operation(
            summary = "Валидация и скоринг данных, полный расчет параметров кредита"
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
            path = CALCULATION_URL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public CreditDTO scoring(
            @Parameter(name = "ScoringDataDTO", required = true) @Valid @RequestBody ScoringDataDTO request) {

        return scoringService.scoring(request);
    }
}
