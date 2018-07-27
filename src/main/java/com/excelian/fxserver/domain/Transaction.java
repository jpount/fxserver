package com.excelian.fxserver.domain;

import com.excelian.fxserver.domain.enumeration.TransactionState;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "from_amount", precision = 16, scale = 6, nullable = false)
    private BigDecimal fromAmount;

    @NotNull
    @Column(name = "to_amount", precision = 16, scale = 6, nullable = false)
    private BigDecimal toAmount;

    @Column(name = "fee_amount", precision = 16, scale = 6)
    private BigDecimal feeAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private TransactionState state;

    @Column(name = "state_description")
    private String stateDescription;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private BankAccount from;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private BankAccount to;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Currency feeCurrency;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private User createdBy;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User updatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(BigDecimal fromAmount) {
        this.fromAmount = fromAmount;
    }

    public Transaction fromAmount(BigDecimal fromAmount) {
        this.fromAmount = fromAmount;
        return this;
    }

    public BigDecimal getToAmount() {
        return toAmount;
    }

    public void setToAmount(BigDecimal toAmount) {
        this.toAmount = toAmount;
    }

    public Transaction toAmount(BigDecimal toAmount) {
        this.toAmount = toAmount;
        return this;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Transaction feeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
        return this;
    }

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

    public Transaction state(TransactionState state) {
        this.state = state;
        return this;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public void setStateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
    }

    public Transaction stateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
        return this;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Transaction createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Transaction updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public BankAccount getFrom() {
        return from;
    }

    public void setFrom(BankAccount bankAccount) {
        this.from = bankAccount;
    }

    public Transaction from(BankAccount bankAccount) {
        this.from = bankAccount;
        return this;
    }

    public BankAccount getTo() {
        return to;
    }

    public void setTo(BankAccount bankAccount) {
        this.to = bankAccount;
    }

    public Transaction to(BankAccount bankAccount) {
        this.to = bankAccount;
        return this;
    }

    public Currency getFeeCurrency() {
        return feeCurrency;
    }

    public void setFeeCurrency(Currency currency) {
        this.feeCurrency = currency;
    }

    public Transaction feeCurrency(Currency currency) {
        this.feeCurrency = currency;
        return this;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public Transaction createdBy(User user) {
        this.createdBy = user;
        return this;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User user) {
        this.updatedBy = user;
    }

    public Transaction updatedBy(User user) {
        this.updatedBy = user;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        if (transaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", fromAmount=" + getFromAmount() +
            ", toAmount=" + getToAmount() +
            ", feeAmount=" + getFeeAmount() +
            ", state='" + getState() + "'" +
            ", stateDescription='" + getStateDescription() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
