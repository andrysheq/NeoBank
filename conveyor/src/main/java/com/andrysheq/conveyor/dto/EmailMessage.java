package com.andrysheq.conveyor.dto;

import com.andrysheq.conveyor.enums.ChangeType;
import com.andrysheq.conveyor.enums.EmailTheme;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class EmailMessage {
    @Schema(description = "")
    @NotNull
    private String address;

    @Schema(description = "")
    @NotNull
    private EmailTheme theme;

    @Schema(description = "")
    @NotNull
    private Long applicationId;
}
