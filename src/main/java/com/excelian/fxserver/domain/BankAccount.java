package com.excelian.fxserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.excelian.fxserver.domain.enumeration.BackAccountState;

/**
 * A BankAccount.
 */
@Entity
@Table(name = "bank_account", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"bsb", "jhi_number"}),
    @UniqueConstraint(columnNames = {"bic", "jhi_number"}),
})
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 6)
    @Column(name = "bsb", nullable = false)
    private String bsb;

    @NotNull
    @Size(min = 8)
    @Column(name = "bic", nullable = false)
    private String bic;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "amount", precision = 16, scale = 6, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private BackAccountState state = BackAccountState.CREATED;

    @Column(name = "state_description")
    private String stateDescription;

    @NotNull
    @Size(min = 4)
    @Column(name = "jhi_number", nullable = false)
    private String number;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Currency currency;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBsb() {
        return bsb;
    }

    public BankAccount bsb(String bsb) {
        this.bsb = bsb;
        return this;
    }

    public void setBsb(String bsb) {
        this.bsb = bsb;
    }

    public String getBic() {
        return bic;
    }

    public BankAccount bic(String bic) {
        this.bic = bic;
        return this;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getName() {
        return name;
    }

    public BankAccount name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BankAccount amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BackAccountState getState() {
        return state;
    }

    public BankAccount state(BackAccountState state) {
        this.state = state;
        return this;
    }

    public void setState(BackAccountState state) {
        this.state = state;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public BankAccount stateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
        return this;
    }

    public void setStateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
    }

    public String getNumber() {
        return number;
    }

    public BankAccount number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BankAccount currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public User getUser() {
        return user;
    }

    public BankAccount user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        BankAccount bankAccount = (BankAccount) o;
        if (bankAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bankAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BankAccount{" +
            "id=" + getId() +
            ", bsb='" + getBsb() + "'" +
            ", bic='" + getBic() + "'" +
            ", name='" + getName() + "'" +
            ", amount=" + getAmount() +
            ", state='" + getState() + "'" +
            ", stateDescription='" + getStateDescription() + "'" +
            ", number='" + getNumber() + "'" +
            "}";
    }
}
