package com.andrysheq.deal.entity;

import com.andrysheq.deal.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "application")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private ClientEntity client;

    @OneToOne
    @JoinColumn(name = "credit_id", referencedColumnName = "id")
    private CreditEntity credit;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "sign_date")
    private LocalDateTime signDate;

    @Column(name = "ses_code")
    private String sesCode;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "loan_offer_id", referencedColumnName = "id")
    private LoanOfferEntity loanOffer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "status_history_id", referencedColumnName = "id")
    private List<ApplicationStatusHistoryEntity> statusHistory = new ArrayList<>();

    public ApplicationEntity(ClientEntity client) {
        this.client = client;
    }

    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
    }
    public void addStatusHistory(ApplicationStatusHistoryEntity applicationStatusHistory){
        statusHistory.add(applicationStatusHistory);
    }
}

