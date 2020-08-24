package de.thro.inf.vv.OAService.customer;

import de.thro.inf.vv.OAService.payment.Payment;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Timon Tonon, on 09.10.20
 */
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private UUID accountID;

    @Embedded
    private Salutation salutation;

    @Column(name = "openamount")
    private double openAmount;

    @Version
    private Integer version;

    @OneToMany(targetEntity = Payment.class, mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Payment> payment;

    @OneToMany
    private List<Customer> customerList;

    public Customer(){
    }

    public Customer(Salutation salutation, String name, String lastname, String email) {
        this.salutation = salutation;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public double getOpenAmount() {
        return openAmount;
    }

    public void setOpenAmount(double openAmount) {
        this.openAmount = openAmount;
    }


    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public List<Payment> getPayment() {
        return payment;
    }

    public void setPayment(List<Payment> payment) {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Double.compare(customer.openAmount, openAmount) == 0 &&
                Objects.equals(id, customer.id) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(lastname, customer.lastname) &&
                Objects.equals(email, customer.email) &&
                salutation == customer.salutation &&
                Objects.equals(version, customer.version) &&
                Objects.equals(customerList, customer.customerList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname, email, salutation, version);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", salutation='" + salutation + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    public UUID getAccountID() {
        return accountID;
    }

    public void setAccountID(UUID accountID) {
        this.accountID = accountID;
    }
}
