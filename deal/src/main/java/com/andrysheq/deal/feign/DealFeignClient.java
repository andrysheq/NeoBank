package com.andrysheq.deal.feign;

import com.andrysheq.deal.dto.CreditDTO;
import com.andrysheq.deal.dto.LoanApplicationRequestDTO;
import com.andrysheq.deal.dto.LoanOfferDTO;
import com.andrysheq.deal.dto.ScoringDataDTO;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "conveyor", url = "http://localhost:8001/")
public interface DealFeignClient {
    String OFFERS_URL = "/conveyor/offers";
    String CALCULATION_URL = "/conveyor/calculation";
    @PostMapping(
            path = OFFERS_URL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    List<LoanOfferDTO> preScoring(
            @Parameter(description = "Loan Application Request DTO", required = true) @Valid @RequestBody LoanApplicationRequestDTO request,
            @Parameter(description = "Application ID", required = true) @RequestParam Long applicationId);

    @PostMapping(
            path = CALCULATION_URL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    CreditDTO scoring(
            @Parameter(name = "ScoringDataDTO", required = true) @Valid @RequestBody ScoringDataDTO request);

}
