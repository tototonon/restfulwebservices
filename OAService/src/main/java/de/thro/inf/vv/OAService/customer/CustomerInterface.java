package de.thro.inf.vv.OAService.customer;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Timon Tonon
 */
public interface CustomerInterface {
    List<Customer> findByEmail(String email);
    List<Customer>findAll();
    Customer findById(Long id);
    Customer putCustomer(Long id, Customer update);
    Customer postCustomer(Customer post);
    int deleteCustomer(Long id);

}
