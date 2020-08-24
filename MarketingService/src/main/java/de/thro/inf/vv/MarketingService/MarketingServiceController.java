package de.thro.inf.vv.MarketingService;

import com.google.gson.Gson;
import com.rabbitmq.client.DeliverCallback;
import de.thro.inf.vv.OAService.RabbitMQConnection.MQConnection;
import de.thro.inf.vv.OAService.customer.Customer;
import de.thro.inf.vv.core.Queues;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.OptimisticLockException;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MarketingServiceController {
    private static final List<Customer> approvedCustomerList = new ArrayList<>();
    private static final List<Customer> declinedCustomerList = new ArrayList<>();
    private final String URL = "https://vvdemomailserviceprovider.azurewebsites.net/";
    private final String apiVersion = "api/v1";
    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private String customerCollection;
    private String baseURI;

    private static final Logger logger = Logger.getLogger(MarketingServiceController.class.getName());

    public MarketingServiceController() {

        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setBearerAuth(getBearerToken());

    }

    private String getBearerToken() {
        BearerToken bearerToken = new BearerToken();
        HttpEntity<BearerToken> entity = new HttpEntity<>(bearerToken, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + apiVersion + "authenticate", HttpMethod.POST, entity, String.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            String token = responseEntity.getBody();
            return token.substring(1, token.length() - 1);
        } else {
            throw new RuntimeException();
        }
    }

    public static boolean containsCustomer(List<Customer> list, String email) {
        return list.stream().anyMatch(o -> o.getEmail().equals(email));
    }

    public static void removeCustomer(List<Customer> list, String email) {
        list.removeIf(customer -> customer.getEmail().equals(email));

    }

    public Optional<Customer> getCustomer(UUID id) {
        HttpEntity<String> entity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<Customer> responseEntity = restTemplate.exchange(URL + apiVersion + "Account/" + id.toString(), HttpMethod.GET, entity, Customer.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return Optional.of(responseEntity.getBody());
        } else if (responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            return Optional.empty();
        } else {
            throw new RuntimeException();
        }
    }

    public List<Customer> getAllCustomer() {
        HttpEntity<String> entity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<List> responseEntity = restTemplate.exchange(URL + apiVersion + "Account/", HttpMethod.GET, entity, List.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            System.out.println(responseEntity.getBody());
            return responseEntity.getBody();
        } else {
            throw new RuntimeException();
        }
    }

    public List<Customer> getAllCustomers(int limit) {
        if (limit <= 0 || limit > 20) {
            throw new RuntimeException("Limit must be between 1 and 20");
        }
        HttpEntity<String> entity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<List> responseEntity = restTemplate.exchange(URL + apiVersion + "Account/" + "?limit=" + limit, HttpMethod.GET, entity, List.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException();
        }
    }

    public void postCustomer(Customer customer) {
        HttpEntity<Customer> entity = new HttpEntity<>(customer, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + apiVersion + "Account/", HttpMethod.POST, entity, String.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.CREATED)) {
            URI location = responseEntity.getHeaders().getLocation();
            logger.log(Level.INFO, "Customer posted at: " + location);
        } else {
            throw new RuntimeException();
        }
    }

    public void deleteCustomer(UUID id) throws OptimisticLockException {
        try {

            HttpEntity<String> entity = new HttpEntity<>("", httpHeaders);
            ResponseEntity<String> responseEntity = restTemplate.exchange(URL + apiVersion + "Account/" + id.toString(), HttpMethod.DELETE, entity, String.class);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                logger.log(Level.INFO, "Customer deleted");
            } else {
                throw new RuntimeException();
            }

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.CONFLICT)) {
                throw new OptimisticLockException("Customer" + id"changed by another party");
            }
        }
    }

    public static void consumeApprovedCustomers() throws IOException {
        MarketingServiceController marketingServiceController = new MarketingServiceController();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String json = new String(message.getBody(), StandardCharsets.UTF_8);
            Gson gson = new Gson();
            Customer fromJson = gson.fromJson(json, Customer.class);
            if (!containsCustomer(approvedCustomerList, fromJson.getEmail())) {
                fromJson.setAccountID(UUID.randomUUID());
                approvedCustomerList.add(fromJson);
                removeCustomer(declinedCustomerList, fromJson.getEmail());
                marketingServiceController.deleteCustomer(fromJson.getAccountID());
            }
        };
        MQConnection.getChannel().basicConsume(Queues.getApprovedCustomers(), true, deliverCallback, (consumerTag -> {
        }));

    }

    public static void consumeDeclinedCustomers() throws IOException {
        MarketingServiceController marketingServiceController = new MarketingServiceController();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String json = new String(message.getBody(), StandardCharsets.UTF_8);
            Gson gson = new Gson();
            Customer fromJson = gson.fromJson(json, Customer.class);
            approvedCustomerList.forEach(customer -> System.out.println(customer.getEmail()));
            if (!containsCustomer(declinedCustomerList, fromJson.getEmail())) {
                fromJson.setAccountID(UUID.randomUUID());
                declinedCustomerList.add(fromJson);
                removeCustomer(approvedCustomerList, fromJson.getEmail());
                approvedCustomerList.forEach(customer -> System.out.println(customer.getEmail()));
                marketingServiceController.deleteCustomer(fromJson.getAccountID());
            }
        };
        MQConnection.getChannel().basicConsume(Queues.getDeclinedCustomers(), true, deliverCallback, (consumerTag -> {
        }));

    }
}


