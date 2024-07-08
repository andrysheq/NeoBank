package com.andrysheq.deal.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Должность")
public enum Position {
    @Schema(description = "Менеджер среднего звена")
    MIDDLE_MANAGER,
    @Schema(description = "Топ-менеджер")
    TOP_MANAGER,
    @Schema(description = "Рабочий")
    WORKER,
    @Schema(description = "Владелец бизнеса")
    OWNER
}
