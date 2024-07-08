package com.andrysheq.conveyor.enums;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Статус кредита")
public enum CreditStatus {
    @Schema(description = "Рассчитан")
    CALCULATED,
    @Schema(description = "Выдан")
    ISSUED
}
