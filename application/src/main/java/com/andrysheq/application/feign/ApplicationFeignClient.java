package com.andrysheq.application.feign;

import com.andrysheq.application.entity.ApplicationEntity;
import com.andrysheq.application.dto.LoanApplicationRequestDTO;
import com.andrysheq.application.dto.LoanOfferDTO;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "deal", url = "http://localhost:8002/")
public interface ApplicationFeignClient {
    String APPLICATION_URL = "/deal/application";
    String OFFER_URL = "/deal/offer";
    @PostMapping(
            path = APPLICATION_URL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    List<LoanOfferDTO> application(
            @Parameter(name = "LoanApplicationRequestDTO", required = true) @Valid @RequestBody LoanApplicationRequestDTO request);

    @PostMapping(
            path = OFFER_URL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ApplicationEntity offer(
            @Parameter(name = "LoanOfferDTO", required = true) @Valid @RequestBody LoanOfferDTO request);
}
