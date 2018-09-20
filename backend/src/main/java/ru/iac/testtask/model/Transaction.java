package ru.iac.testtask.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    private int quantity;

    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public static List<String> getSortableColumns() {
        String[] columns = {
                "product.name",
                "quantity",
                "amount",
                "date"
        };

        return Arrays.asList(columns);
    }

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

    public int getQuantity() {
        return quantity;
    }

    public Transaction setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction setAmount(BigDecimal amount) {
        this.amount = amount;
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
