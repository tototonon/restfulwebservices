package de.thro.inf.vv.OAService;

import de.thro.inf.vv.OAService.orders.Orders;
import de.thro.inf.vv.OAService.orders.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Timon Tonon.
 */
@Api(produces = MediaType.APPLICATION_JSON_VALUE)

@RequestMapping(value = "api/v1")
@Service
public class OrdersController {

    @Autowired
    OrdersService ordersService;
    @ApiOperation(value = "Create new clothing", response = Orders.class)

    @ApiResponses(value = {@ApiResponse(code = 201, message = "new order"),

            @ApiResponse(code = 403, message = "order failed")})
    @PostMapping(value = "/order/send", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Orders> postOrder(@RequestBody Orders orders) {

        Orders post = ordersService.postOrder(orders);
        if (post != null) {
            try {
                return ResponseEntity.created(new URI("http://localhost:8080/order/" + orders.getCustomer().getId())).build();
            } catch (URISyntaxException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}

