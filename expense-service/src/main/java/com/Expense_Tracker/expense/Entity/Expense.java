package com.Expense_Tracker.expense.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    @Column(name = "external_id")
    private String externalId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "amount" , nullable = false , updatable = true)
    private String amount;
    @Column(name = "currency" , nullable = false , updatable = true)
    private String currency;
    @Column(name = "merchant" , updatable = true)
    private String merchant;

    @Column(name = "created_at" , updatable = false , nullable = false)
    @CreationTimestamp
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Timestamp createdAt;

    @PrePersist
    @PreUpdate
    private void generateExternalId(){
        if (this.externalId == null) {
            this.externalId = UUID.randomUUID().toString();
        }
    }
}
