package com.restful.app.api.utils;

import com.restful.app.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface IEmailSender {

    void sendMessageWithActivationInstruction (User user, String subject);

    void sendMessageWithNewPassword (User user, String subject, String newPassword);

    void sendMessageToBookBack(User user, String subject);

}
