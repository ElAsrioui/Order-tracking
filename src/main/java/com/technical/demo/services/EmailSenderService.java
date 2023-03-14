package com.technical.demo.services;

import com.technical.demo.dto.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final ITemplateEngine templateEngine;
    private String transmitter;

    public EmailSenderService(JavaMailSender javaMailSender, ITemplateEngine templateEngine,
                              @Value("${spring.mail.username}") String transmitter
    ) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.transmitter = transmitter;
    }

    /**
     * Sends an email to a customer with updated order status using the provided EmailRequest.
     *
     * @param request the EmailRequest containing the necessary information to send the email
     * @return true if the email was sent successfully, false otherwise
     * @throws MessagingException           if an error occurs while sending the email
     * @throws UnsupportedEncodingException if the specified encoding is not supported
     */
    public boolean updateOrderStatusEmail(EmailRequest request) throws MessagingException, UnsupportedEncodingException {
        log.info("-----EmailSenderService--updateOrderStatusEmail-Method--Start-----");

        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put("request", request);
            Context context = new Context();
            context.setVariables(variables);
            String process = templateEngine.process("updatedOrderStatusTemplate", context);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
            helper.setSubject("Your order status changed");
            helper.setFrom(new InternetAddress(transmitter, "Order Tracker"));
            helper.setText(process, true);
            helper.setTo(request.getCustomerEmail());
            javaMailSender.send(mimeMessage);
            log.info("<-----EmailSenderService--updateOrderStatusEmail--End----->");
        } catch (Exception ex) {
            log.error("Exception while sending current order status email {} ", request.getCustomerEmail(), ex);
        }
        return true;
    }

    @Async
    public void sendNotification(EmailRequest emailRequest) {
        try {
            this.updateOrderStatusEmail(emailRequest);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
