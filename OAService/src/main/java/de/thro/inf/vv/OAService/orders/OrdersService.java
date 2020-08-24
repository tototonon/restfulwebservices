package de.thro.inf.vv.OAService.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService implements OrdersInterface {
    @Autowired
    OrdersRepo ordersRepo;

    @Override
    public Orders postOrder(Orders orders) {
        if (orders.getAmount() < 1000) {
            return ordersRepo.save(orders);
        } else {
            return null;
        }
    }
}
