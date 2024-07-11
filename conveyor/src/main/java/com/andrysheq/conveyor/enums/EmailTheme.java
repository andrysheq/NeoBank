package com.andrysheq.conveyor.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum EmailTheme {
    @Schema(description = "Регистрация завершена")
    FINISH_REGISTRATION,
    @Schema(description = "Документы подготовлены")
    CREATE_DOCUMENTS,
    @Schema(description = "Отправлен код подтверждения")
    SEND_SES,
    @Schema(description = "Кредит выдан")
    CREDIT_ISSUED,
    @Schema(description = "Заявка отклонена")
    APPLICATION_DENIED,
    @Schema(description = "Документы отправлены")
    SEND_DOCUMENTS

}
