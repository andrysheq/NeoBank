package com.andrysheq.application.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payment_schedule")
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о платеже")
public class PaymentScheduleElementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Schema(description = "Номер платежа", example = "1")
    @Column(name = "number")
    private Integer number;

    @Schema(description = "Дата платежа", example = "2024-07-01")
    @Column(name = "date")
    private LocalDate date;

    @Schema(description = "Общая сумма платежа", example = "10000.00")
    @Column(name = "total_payment")
    private BigDecimal totalPayment;

    @Schema(description = "Сумма выплаты процентов", example = "2000.00")
    @Column(name = "interest_payment")
    private BigDecimal interestPayment;

    @Schema(description = "Сумма выплаты долга", example = "8000.00")
    @Column(name = "debt_payment")
    private BigDecimal debtPayment;

    @Schema(description = "Оставшаяся сумма долга", example = "100000.00")
    @Column(name = "remaining_debt")
    private BigDecimal remainingDebt;

    @ManyToOne(optional = false)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "credit_id", referencedColumnName = "id", nullable = false)
    private CreditEntity credit;
}
