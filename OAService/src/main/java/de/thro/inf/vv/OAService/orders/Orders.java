package de.thro.inf.vv.OAService.orders;

import ch.qos.logback.classic.pattern.DateConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import de.thro.inf.vv.OAService.customer.Customer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

//hat zwingend eine eindeutige OrderId, einen Amount („Bestellsumme“),
// ein Erstelldatum und einen zugeordneten Kunden.
@Entity
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "amount",nullable = false)
    private float amount;

    @Column(name = "date")
    private Date date;

    @Version
    private Integer version;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(nullable = false)
    private Customer customer;

    public Orders() {
        this.date = new Date();
    }


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return Float.compare(orders.amount, amount) == 0 &&
                Objects.equals(orderId, orders.orderId) &&
                Objects.equals(date, orders.date) &&
                Objects.equals(customer, orders.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, amount, date, customer);
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", amount=" + amount +
                ", date=" + date +
                ", customer=" + customer.getId() +
                '}';
    }
}