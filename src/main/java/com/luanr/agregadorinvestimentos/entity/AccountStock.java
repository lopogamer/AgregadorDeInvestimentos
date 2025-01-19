package com.luanr.agregadorinvestimentos.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "account_stock_tb")
public class AccountStock {

    @EmbeddedId
    private AccountStockId accountStockId;

    @ManyToOne
    @MapsId("account_id")
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @MapsId("stock_id")
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "quantity")
    private Long quantity;

    public AccountStock(AccountStockId accountStockId, Account account, Stock stock, Long quantity) {
        this.accountStockId = accountStockId;
        this.account = account;
        this.stock = stock;
        this.quantity = quantity;
    }

    public AccountStock() {
    }

    public AccountStockId getAccountStockId() {
        return accountStockId;
    }

    public void setAccountStockId(AccountStockId accountStockId) {
        this.accountStockId = accountStockId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}


