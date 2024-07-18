package com.andrysheq.application.entity;

import com.andrysheq.application.enums.ChangeType;
import com.andrysheq.application.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
@Entity
@Table(name = "application_status_history")
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "История статусов заявки")
public class ApplicationStatusHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Schema(description = "Статус кредита", example = "CC_DENIED")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Schema(description = "Время оставления заявки", example = "07.07.2017 07:00")
    @Column(name = "time")
    private LocalDateTime time;

    @Schema(description = "Тип изменения статуса", example = "DENIED")
    @Enumerated(EnumType.STRING)
    @Column(name = "change_type")
    private ChangeType changeType;

    @ManyToOne(optional = false)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "application_id", referencedColumnName = "id", nullable = false)
    private ApplicationEntity application;
}
