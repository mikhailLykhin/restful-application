package com.restful.app.util.mail;

import com.restful.app.api.utils.IEmailSender;
import com.restful.app.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EmailSender implements IEmailSender {

    private static final String EMAIL_ADDRESS = "L3v1strauss@gmail.com";
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine springTemplateEngine;

    public EmailSender(JavaMailSender emailSender, SpringTemplateEngine springTemplateEngine) {
        this.emailSender = emailSender;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    public void sendMessageWithActivationInstruction(User user, String subject) {
        Map<String, Object> templateModel = new HashMap<>();
        addParametersToContext(templateModel, user);
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = springTemplateEngine.process("mailMessageTemplate.html", thymeleafContext);
        try {
            sendHtmlMessage(user.getEmail(), subject, htmlBody);
        } catch (MessagingException e) {
            log.error("Failed to send message. Error message: {}", e.getMessage());
        }
    }

    @Override
    public void sendMessageWithNewPassword(User user, String subject, String newPassword) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("password", newPassword);
        templateModel.put("firstName", user.getFirstName());
        templateModel.put("lastName", user.getLastName());
        templateModel.put("adminEmail", EMAIL_ADDRESS);
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = springTemplateEngine.process("mailMessageNewPassword.html", thymeleafContext);
        try {
            sendHtmlMessage(user.getEmail(), subject, htmlBody);
        } catch (MessagingException e) {
            log.error("Failed to send message. Error message: {}", e.getMessage());
        }
    }

    @Override
    public void sendMessageToBookBack(User user, String subject) {
        Map<String, Object> templateModel = new HashMap<>();
        addParametersToContext(templateModel, user);
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = springTemplateEngine.process("mailMessageBackBookPlease.html", thymeleafContext);
        try {
            sendHtmlMessage(user.getEmail(), subject, htmlBody);
        } catch (MessagingException e) {
            log.error("Failed to send message. Error message: {}", e.getMessage());
        }
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        helper.setFrom(EMAIL_ADDRESS);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
//        helper.addInline("logo", resourceFile);
        emailSender.send(message);
    }

    private void addParametersToContext(Map<String, Object> templateModel, User user) {
        templateModel.put("userId", user.getId());
        templateModel.put("firstName", user.getFirstName());
        templateModel.put("lastName", user.getLastName());
        templateModel.put("adminEmail", EMAIL_ADDRESS);
    }
}
