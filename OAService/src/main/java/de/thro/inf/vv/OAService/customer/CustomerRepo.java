package de.thro.inf.vv.OAService.customer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;

public interface CustomerRepo extends CrudRepository<Customer, Long> {
    List<Customer> findByEmail(String email);

}