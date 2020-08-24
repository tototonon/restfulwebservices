package de.thro.inf.vv.OAService.payment;

public interface PaymentInterface {
    double getPayment(Long id);
    int postPayment(Payment payment);

}
