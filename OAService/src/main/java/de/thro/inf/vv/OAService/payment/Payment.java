package de.thro.inf.vv.OAService.payment;


import de.thro.inf.vv.OAService.customer.Customer;

import javax.persistence.*;
import java.util.Objects;

//hat eine eindeutige PaymentId, eine Referenz auf die OrderId,
// sowie die KundenId und einen bezahlten Betrag
@Entity
public class Payment {
    @Id
    @GeneratedValue
    private Long paymentId;
    @Column(name = "payedSum")
    private float payedSum;
    @Version
    private Integer version;

    @Column(name = "payment")
    private String payment;

     @ManyToOne(cascade = CascadeType.ALL)
     private Customer customer;

    public Payment() {

    }

    public Payment(float payedSum) {
        this.payedSum = payedSum;


    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }


    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public float getPayedSum() {
        return payedSum;
    }

    public void setPayedSum(float payedSum) {
        this.payedSum = payedSum;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment1 = (Payment) o;
        return Float.compare(payment1.payedSum, payedSum) == 0 &&
                Objects.equals(paymentId, payment1.paymentId);

    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, payedSum, payment);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", payedSum=" + payedSum +
                '}';
    }

}
