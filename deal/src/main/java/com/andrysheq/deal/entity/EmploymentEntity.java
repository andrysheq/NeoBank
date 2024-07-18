package com.andrysheq.deal.entity;

import com.andrysheq.deal.enums.EmploymentStatus;
import com.andrysheq.deal.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "employment")
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Schema(description = "Трудовой статус", example = "UNEMPLOYED")
    @Enumerated(EnumType.STRING)
    @Column(name = "employment_status")
    private EmploymentStatus employmentStatus;

    @Schema(description = "ИНН", example = "123456789123")
    @Column(name = "inn")
    private String employerInn;

    @Schema(description = "Заработная плата", example = "12345.00")
    @Column(name = "salary")
    private BigDecimal salary;

    @Schema(description = "Должность", example = "TOP_MANAGER")
    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Position position;

    @Schema(description = "Общий стаж работы", example = "24")
    @Column(name = "total_experience")
    private Integer workExperienceTotal;

    @Schema(description = "Текущий стаж работы", example = "12")
    @Column(name = "current_experience")
    private Integer workExperienceCurrent;
}
