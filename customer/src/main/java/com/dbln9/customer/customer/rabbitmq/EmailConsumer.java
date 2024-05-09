package com.dbln9.customer.customer.rabbitmq;

import com.dbln9.customer.customer.Customer;
import com.dbln9.customer.customer.CustomerMessage;
import com.dbln9.customer.customer.email.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@AllArgsConstructor
@Slf4j
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "${rabbitmq.queues.email}")
    public void consumer(CustomerMessage customerMessage)
            throws MessagingException, UnsupportedEncodingException {
        log.info("Consumed {} from queue", customerMessage);
        emailService.sendVerificationMessage(customerMessage);
    }
}
