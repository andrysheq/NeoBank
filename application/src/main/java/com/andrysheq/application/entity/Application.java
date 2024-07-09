package com.andrysheq.application.entity;

import com.andrysheq.application.dto.ApplicationStatusHistoryDTO;
import com.andrysheq.application.dto.LoanOfferDTO;
import com.andrysheq.application.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

