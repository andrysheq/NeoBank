package com.andrysheq.conveyor.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Request<T> {

    @NotNull
    @Valid
    private T payload;
}
