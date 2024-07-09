package com.andrysheq.application.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Пол")
public enum Gender {
    @Schema(description = "Мужчина")
    MALE,
    @Schema(description = "Женщина")
    FEMALE,
    @Schema(description = "Небинарная личность")
    NON_BINARY
}
