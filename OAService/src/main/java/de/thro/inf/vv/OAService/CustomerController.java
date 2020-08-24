package de.thro.inf.vv.OAService;


import de.thro.inf.vv.OAService.customer.Customer;
import de.thro.inf.vv.OAService.customer.CustomerRepo;
import de.thro.inf.vv.OAService.customer.CustomerService;
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
import java.util.ArrayList;
import java.util.List;

@Api(produces = MediaType.APPLICATION_JSON_VALUE)

@RequestMapping(value = "api/v1")
@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepo customerRepo;


    @ApiResponses(value = {@ApiResponse(code = 200, message = "Customers found")})
    @GetMapping(value = "/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        Iterable<Customer> allCustomers = customerService.findAll();
        List<Customer> allCustomersList = new ArrayList<>();
        allCustomers.forEach(allCustomersList::add);

        return ResponseEntity.ok(allCustomersList);
    }
    @ApiOperation(value = "Shows customer with specific ID", response = Customer.class)

    @ApiResponses(value = {

            @ApiResponse(code = 200, message = "Customer found "),

            @ApiResponse(code = 404, message = "No customer found")})
    @GetMapping(value = "customer/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id) {
        Customer customer = customerService.findById(id);
        //200
        if (customer != null) {
            return ResponseEntity.ok(customer);

        } else {
            //notFound 404
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Update a given customer", response = Customer.class)

    @ApiResponses(value = {

            @ApiResponse(code = 200, message = "Customer found and updated"),

            @ApiResponse(code = 404, message = "No customer found")})
    @PutMapping(value = "/customer/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer toUpdate) {
        Customer customer = customerService.putCustomer(id, toUpdate);

        if (customer != null) {
            customerService.putCustomer(id, toUpdate);
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Create new customer", response = Customer.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "customer created"),
            @ApiResponse(code = 304, message = "nothing new created")})
    @PostMapping(value = "/customer/create", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {

        Customer post = customerService.postCustomer(customer);
        if (post != null) {
            try {
                return ResponseEntity.created(new URI("http://localhost:8080/customer/" + customer.getId())).build();
            } catch (URISyntaxException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @ApiResponses(value = {

            @ApiResponse(code = 200, message = "Customer found and deleted"),

            @ApiResponse(code = 404, message = "No customer found")})
    @DeleteMapping(value = "customer/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long id) {
        if (customerService.deleteCustomer(id) == 1) {
            return ResponseEntity.noContent().build();
        } else if (customerService.deleteCustomer(id) == 2) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
