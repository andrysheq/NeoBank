package com.andrysheq.application.enums;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Статус трудоустроенного")
public enum EmploymentStatus {
    @Schema(description = "Нетрудоустроен")
    UNEMPLOYED,
    @Schema(description = "Самозанятый")
    SELF_EMPLOYED,
    @Schema(description = "Владелец бизнеса")
    BUSINESS_OWNER,
    @Schema(description = "Наемный рабочий")
    EMPLOYEE
}
