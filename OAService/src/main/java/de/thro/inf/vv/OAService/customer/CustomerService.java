package de.thro.inf.vv.OAService.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CustomerService implements CustomerInterface {
    @Autowired
    CustomerRepo customerRepo;

    @Override
    public List<Customer> findByEmail(String email) {
        return customerRepo.findByEmail(email);
    }

    @Override
    public List<Customer> findAll() {
        Iterable<Customer> allCustomer = customerRepo.findAll();
        List<Customer> customerList = new ArrayList<>();
        allCustomer.forEach(customerList::add);
        return customerList;
    }

    @Override
    public Customer findById(Long id) {
        Optional<Customer> customer = customerRepo.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            return null;
        }
    }


    @Override
    public Customer putCustomer(Long id, Customer update) {
        if (findById(id) != null) {
            findById(id).setName(update.getName());
            findById(id).setLastname(update.getLastname());
            findById(id).setEmail(update.getEmail());
            findById(id).setSalutation(update.getSalutation());
            customerRepo.save(findById(id));
            return findById(id);
        } else {
            return null;
        }
    }

    /**
     * same email openamount to 0.
     * @param post posted amount.
     * @return
     */
    @Override
    public Customer postCustomer(Customer post) {
        if (findByEmail(post.getEmail()).isEmpty()) {
            post.setOpenAmount(0.0);
            return customerRepo.save(post);
        } else {
            return null;
        }
    }
    @Override
    public int deleteCustomer(Long id) {
        Customer customer = findById(id);
        if(customer != null){
            if(customer.getOpenAmount() == 0) {
                customerRepo.delete(customer);
                return 1;
            } else {
                return 2;
            }
        }
        return 0;
    }
}
