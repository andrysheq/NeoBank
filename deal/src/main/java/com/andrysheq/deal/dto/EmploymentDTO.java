package com.andrysheq.deal.dto;

import com.andrysheq.deal.enums.EmploymentStatus;
import com.andrysheq.deal.enums.Position;
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
@Schema(description = "Трудовая книга")
public class EmploymentDTO {

    @Schema(description = "Трудовой статус")
    @NotNull
    private EmploymentStatus employmentStatus;

    @Schema(description = "ИНН")
    @NotNull
    private String employerInn;

    @Schema(description = "Заработная плата")
    @NotNull
    private BigDecimal salary;

    @Schema(description = "Должность")
    @NotNull
    private Position position;

    @Schema(description = "Общий стаж работы")
    @NotNull
    private Integer WorkExperienceTotal;

    @Schema(description = "Текущий стаж работы")
    @NotNull
    private Integer WorkExperienceCurrent;

}
