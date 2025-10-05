package com.darkness.coreBanking.dto;

import com.darkness.coreBanking.domain.AccountCurrency;
import com.darkness.coreBanking.domain.AccountType;
import com.darkness.coreBanking.domain.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountDto {

    private Long pk;
    private String accountNumber;

    private String type;

    private String currency;

    private BigDecimal balance;

    private String status;

    private Long version;

    private UserDto user;

    private List<Transaction> transactions = new ArrayList<>();

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
