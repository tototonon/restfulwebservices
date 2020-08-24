package de.thro.inf.vv.OAService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OaServiceApplication {
    public static void main(String[] args) {

        SpringApplication.run(OaServiceApplication.class, args);

    }
}
/**
 * @Bean public ApplicationRunner runner(RabbitTemplate rabbitTemplate) {
 * String message = " payload is broadcast";
 * return args -> {
 * rabbitTemplate.convertAndSend(MQConfig.EXCHANGE_NAME, "", "fanout" + message);
 * <p>
 * };
 * }
 * @RabbitListener(queues = {APPROVED_CUSTOMERS})
 * public void receiveMessageFromFanout1(String message) {
 * System.out.println("Received fanout 1 message: " + message);
 * }
 * @RabbitListener(queues = {DECLINED_CUSTOMERS})
 * public void receiveMessageFromFanout2(String message) {
 * System.out.println("Received fanout 2 message: " + message);
 * }
 * }
 */