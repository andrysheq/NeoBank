package com.andrysheq.deal;

import com.andrysheq.conveyor.dto.LoanApplicationRequestDTO;
import com.andrysheq.conveyor.dto.LoanOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient()
public interface DealClient {
    @GetMapping("/conveyor/offers")
    public List<LoanOfferDTO> offer(@PathVariable("LoanApplicationRequestDTO") LoanApplicationRequestDTO request);

}
