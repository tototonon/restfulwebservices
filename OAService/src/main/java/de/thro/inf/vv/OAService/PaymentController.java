package de.thro.inf.vv.OAService;


import de.thro.inf.vv.OAService.payment.Payment;
import de.thro.inf.vv.OAService.payment.PaymentInterface;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
@Api(produces = MediaType.APPLICATION_JSON_VALUE)

@RequestMapping(value = "api/v1")
@RestController
public class PaymentController {

    @Autowired
    private PaymentInterface paymentInterface;
    @ApiResponses(value = {

            @ApiResponse(code = 200, message = "get amount"),

            @ApiResponse(code = 404, message = "error")})
    @GetMapping(value = "/payment/{id}")
    public ResponseEntity<Double> getOpenAmount(@PathVariable("id") Long id) {

        if (paymentInterface.getPayment(id) != -1) {
            return ResponseEntity.ok(paymentInterface.getPayment(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @ApiOperation(value = "pay Sum", response = Payment.class)

    @ApiResponses(value = {@ApiResponse(code = 201, message = "payment succesfull"),

            @ApiResponse(code = 403, message = "error")})
    @PostMapping(value = "/payment")
    public ResponseEntity<Payment> payAmount(@RequestBody Payment payment) {

        if (paymentInterface.postPayment(payment) == 1) {
            try {
                return ResponseEntity.created(new URI("http:/localhost:8080/v1/payment/" + payment.getPayedSum() + "/payment/" + payment.getPaymentId())).build();

            } catch (URISyntaxException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

        } else if (paymentInterface.postPayment(payment) == 2) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
