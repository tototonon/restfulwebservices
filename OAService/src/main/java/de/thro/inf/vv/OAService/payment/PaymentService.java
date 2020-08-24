package de.thro.inf.vv.OAService.payment;

import de.thro.inf.vv.OAService.customer.Customer;
import de.thro.inf.vv.OAService.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements PaymentInterface {

    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private CustomerService customerService;

    @Override
    public double getPayment(Long id) {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            return customer.getOpenAmount();
        } else {
            return -1;
        }
    }

    @Override
    public int postPayment(Payment payment) {
        Customer customer = customerService.findById(payment.getCustomer().getId());
        if (customer != null) {
            if (payment.getPayedSum() <= customer.getOpenAmount()) {
                customer.setOpenAmount(customer.getOpenAmount() - payment.getPayedSum());
                customer.getPayment().add(payment);
                payment.setCustomer(customer);
                paymentRepo.save(payment);
                return 1;
            } else {
                return 2;
            }
        }
        return 3;
    }
}
