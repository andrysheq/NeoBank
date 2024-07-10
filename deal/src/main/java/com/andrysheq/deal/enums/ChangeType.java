package com.andrysheq.deal.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum ChangeType {
    @Schema(description = "Рассчитан")
    APPROVED,
    @Schema(description = "Выдан")
    DENIED
}
