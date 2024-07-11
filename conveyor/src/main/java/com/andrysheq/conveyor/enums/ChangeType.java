package com.andrysheq.conveyor.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum ChangeType {
    @Schema(description = "Одобрено")
    APPROVED,
    @Schema(description = "Отказано")
    DENIED
}
