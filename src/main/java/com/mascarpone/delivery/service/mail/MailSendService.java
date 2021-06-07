package com.mascarpone.delivery.service.mail;

import javax.mail.MessagingException;

public interface MailSendService {
    void sendNewShopPassword(String email, String newPassword) throws MessagingException;

    void requestNewPassword(String shopName, String shopEmail);

    void sendNomenclatureForOrder(String supplierEmail, byte [] pdf) throws MessagingException;
}
