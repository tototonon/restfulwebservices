package de.thro.inf.vv.OAService.orders;

import de.thro.inf.vv.OAService.customer.Customer;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepo extends CrudRepository<Orders, Long> {

}