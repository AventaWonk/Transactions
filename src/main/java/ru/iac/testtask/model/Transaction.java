package ru.iac.testtask.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "Store_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id")
    private Product product;

    private int count;

    private BigDecimal total;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public int getId() {
        return id;
    }

    public Transaction setId(int id) {
        this.id = id;
        return this;
    }

    public Shop getShop() {
        return shop;
    }

    public Transaction setShop(Shop shop) {
        this.shop = shop;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public Transaction setProduct(Product product) {
        this.product = product;
        return this;
    }

    public int getCount() {
        return count;
    }

    public Transaction setCount(int count) {
        this.count = count;
        return this;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Transaction setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Transaction setDate(Date date) {
        this.date = date;
        return this;
    }
}
