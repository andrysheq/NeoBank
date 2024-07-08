package com.andrysheq.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FeignTargetResponse {
    @Builder.Default
    private int httpStatus = HttpStatus.OK.value();

    private String senderMessage;

}
