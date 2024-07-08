package com.andrysheq.deal.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статус заявки")
public enum Status {
    @Schema(description = "Предодобрено")
    PREAPPROVAL,
    @Schema(description = "Одобрено")
    APPROVED,
    @Schema(description = "Отказано скоринговой системой")
    CC_DENIED,
    @Schema(description = "Одобрено скоринговой системой")
    CC_APPROVED,
    @Schema(description = "Документы подготавливаются")
    PREPARE_DOCUMENTS,
    @Schema(description = "Документы готовы")
    DOCUMENT_CREATED,
    @Schema(description = "Клиенту отказано в регистрации")
    CLIENT_DENIED,
    @Schema(description = "Кредит выдан")
    CREDIT_ISSUED,
    @Schema(description = "Документы подписаны")
    DOCUMENT_SIGNED
}
