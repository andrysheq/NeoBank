package com.andrysheq.conveyor.dto;

import com.andrysheq.conveyor.enums.ChangeType;
import com.andrysheq.conveyor.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Статус кредита")
    @NotNull
    private Status status;

    @Schema(description = "Время оставления заявки")
    @NotNull
    private LocalDateTime time;

    @Schema(description = "")
    @NotNull
    private ChangeType changeType;
}
