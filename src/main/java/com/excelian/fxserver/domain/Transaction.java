package com.excelian.fxserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.excelian.fxserver.domain.enumeration.TransactionState;

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
    private TransactionState state = TransactionState.CREATED;

    @Column(name = "state_description")
    private String stateDescription;

    @NotNull
    @Size(min = 32, max = 32)
    @Column(name = "uuid", length = 32, nullable = false, unique = true)
    private String uuid;

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

    public Transaction fromAmount(BigDecimal fromAmount) {
        this.fromAmount = fromAmount;
        return this;
    }

    public void setFromAmount(BigDecimal fromAmount) {
        this.fromAmount = fromAmount;
    }

    public BigDecimal getToAmount() {
        return toAmount;
    }

    public Transaction toAmount(BigDecimal toAmount) {
        this.toAmount = toAmount;
        return this;
    }

    public void setToAmount(BigDecimal toAmount) {
        this.toAmount = toAmount;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public Transaction feeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
        return this;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public TransactionState getState() {
        return state;
    }

    public Transaction state(TransactionState state) {
        this.state = state;
        return this;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public Transaction stateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
        return this;
    }

    public void setStateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
    }

    public String getUuid() {
        return uuid;
    }

    public Transaction uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public BankAccount getFrom() {
        return from;
    }

    public Transaction from(BankAccount bankAccount) {
        this.from = bankAccount;
        return this;
    }

    public void setFrom(BankAccount bankAccount) {
        this.from = bankAccount;
    }

    public BankAccount getTo() {
        return to;
    }

    public Transaction to(BankAccount bankAccount) {
        this.to = bankAccount;
        return this;
    }

    public void setTo(BankAccount bankAccount) {
        this.to = bankAccount;
    }

    public Currency getFeeCurrency() {
        return feeCurrency;
    }

    public Transaction feeCurrency(Currency currency) {
        this.feeCurrency = currency;
        return this;
    }

    public void setFeeCurrency(Currency currency) {
        this.feeCurrency = currency;
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
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
