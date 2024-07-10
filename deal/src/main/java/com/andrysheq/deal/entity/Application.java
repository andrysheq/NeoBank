package com.andrysheq.deal.entity;

import com.andrysheq.deal.dto.ApplicationStatusHistoryDTO;
import com.andrysheq.deal.dto.LoanOfferDTO;
import com.andrysheq.deal.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "credit_id", referencedColumnName = "id")
    private Credit credit;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;
    @Column(name = "signDate")
    private LocalDateTime signDate;
    @Column(name = "sesCode")
    private String sesCode;
    @Embedded
    private LoanOfferDTO loanOffer;

    @ElementCollection
    @CollectionTable(name = "status_history", joinColumns = {@JoinColumn(name = "application_id", referencedColumnName = "id")})
    private List<ApplicationStatusHistoryDTO> statusHistory;

    public Application(Client client) {
        this.client = client;
    }

    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
    }
}

