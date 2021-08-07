package com.mascarpone.delivery.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MailSendServiceImpl implements MailSendService {
    @Value("${spring.mail.username}")
    private String rootAdminEmail;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendNewShopPassword(String email, String newPassword) {
        var message = new SimpleMailMessage();
        message.setFrom(rootAdminEmail);
        message.setTo(email);
        message.setSubject("Пароль от Вашей учётной записи на DELIVERY");
        message.setText(newPassword);
        javaMailSender.send(message);
    }

    @Override
    public void requestNewPassword(String shopPrefix, String shopEmail) {
        var message = new SimpleMailMessage();
        message.setFrom(rootAdminEmail);
        message.setTo(rootAdminEmail);
        message.setSubject("Запрос на сброс пароля");
        message.setText("Префикс магазина: " + shopPrefix + ". Email магазина: " + shopEmail);
        javaMailSender.send(message);
    }

    @Override
    public void sendNomenclatureForOrder(String supplierEmail, byte[] pdf) throws MessagingException {
        var message = javaMailSender.createMimeMessage();

        var helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("no-reply@food-assist.ru");
        helper.setTo(supplierEmail);
        helper.setSubject("Заказ");
        helper.setText("Информация по заказу находится в приложенном файле: Заказ.pdf");
        helper.addAttachment("Заказ.pdf", new ByteArrayResource(pdf));

        javaMailSender.send(message);
    }
}
