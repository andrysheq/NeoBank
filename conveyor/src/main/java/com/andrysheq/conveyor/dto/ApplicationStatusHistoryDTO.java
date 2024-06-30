package com.andrysheq.conveyor.dto;

import com.andrysheq.conveyor.enums.ChangeType;
import com.andrysheq.conveyor.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ApplicationStatusHistoryDTO {
    @Schema(description = "")
    @NotNull
    private Status status;

    @Schema(description = "")
    @NotNull
    private LocalDateTime time;

    @Schema(description = "")
    @NotNull
    private ChangeType changeType;
}
