package com.andrysheq.application.dto;

import com.andrysheq.application.enums.ChangeType;
import com.andrysheq.application.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "История статусов заявки")
public class ApplicationStatusHistoryDTO {

    @Schema(description = "Статус кредита")
    @NotNull
    private Status status;

    @Schema(description = "Время оставления заявки")
    @NotNull
    private LocalDateTime time;

    @Schema(description = "Тип изменения")
    @NotNull
    private ChangeType changeType;
}
