package com.andrysheq.deal.entity;

import com.andrysheq.conveyor.dto.ApplicationStatusHistoryDTO;
import com.andrysheq.conveyor.dto.LoanOfferDTO;
import com.andrysheq.conveyor.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CollectionType;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "application")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime creationDate;
    private LocalDateTime signDate;
    private String sesCode;
    @Embedded
    private LoanOfferDTO appliedOffer;
    @ElementCollection
    private List<ApplicationStatusHistoryDTO> statusHistory;

    public Application(Client client) {
        this.client = client;
    }

    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
    }
}

