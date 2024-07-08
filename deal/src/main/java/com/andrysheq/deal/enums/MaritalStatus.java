package com.andrysheq.deal.enums;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Семейное положение")
public enum MaritalStatus {
    @Schema(description = "Холост/не замужем")
    SINGLE,
    @Schema(description = "В браке")
    MARRIED,
    @Schema(description = "Вдовец/вдова")
    WIDOWED,
    @Schema(description = "В разводе")
    DIVORCED
}
