package com.andrysheq.conveyor.dto;

import com.andrysheq.conveyor.enums.EmploymentStatus;
import com.andrysheq.conveyor.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentDTO {
    @Schema(description = "")
    @NotNull
    private EmploymentStatus employmentStatus;

    @Schema(description = "")
    @NotNull
    private String employerInn;

    @Schema(description = "")
    @NotNull
    private BigDecimal salary;

    @Schema(description = "")
    @NotNull
    private Position position;

    @Schema(description = "")
    @NotNull
    private Integer WorkExperienceTotal;

    @Schema(description = "")
    @NotNull
    private Integer WorkExperienceCurrent;

}
