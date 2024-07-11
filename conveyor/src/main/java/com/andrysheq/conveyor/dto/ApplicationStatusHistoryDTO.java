package com.andrysheq.conveyor.dto;

import com.andrysheq.conveyor.enums.ChangeType;
import com.andrysheq.conveyor.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "История статусов заявки")
public class ApplicationStatusHistoryDTO {

    @Schema(description = "Статус кредита", example = "CC_DENIED")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @Schema(description = "Время оставления заявки", example = "07.07.2017 07:00")
    @NotNull
    private LocalDateTime time;

    @Schema(description = "Тип изменения статуса", example = "DENIED")
    @NotNull
    @Enumerated(EnumType.STRING)
    private ChangeType changeType;
}
