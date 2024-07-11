package com.andrysheq.application.dto;

import com.andrysheq.application.enums.EmploymentStatus;
import com.andrysheq.application.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Schema(description = "Трудовой статус", example = "UNEMPLOYED")
    @Enumerated(EnumType.STRING)
    @NotNull
    private EmploymentStatus employmentStatus;

    @Schema(description = "ИНН", example = "123456789123")
    @NotNull
    private String employerInn;

    @Schema(description = "Заработная плата", example = "12345.00")
    @NotNull
    private BigDecimal salary;

    @Schema(description = "Должность", example = "TOP_MANAGER")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Position position;

    @Schema(description = "Общий стаж работы", example = "24")
    @NotNull
    private Integer workExperienceTotal;

    @Schema(description = "Текущий стаж работы", example = "12")
    @NotNull
    private Integer workExperienceCurrent;

}
