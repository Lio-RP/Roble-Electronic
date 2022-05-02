package com.roble.springproject.robleelectronic.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "payment")
public class Payment extends BaseEntity{

    @Column(name = "card_name")
    private String cardName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "security_code")
    private int securityCode;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    public Payment() {
    }

    public Payment(String cardName, String cardNumber, int securityCode, LocalDate expireDate) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
        this.expireDate = expireDate;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }
}
