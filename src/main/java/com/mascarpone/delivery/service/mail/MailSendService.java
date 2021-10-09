package com.mascarpone.delivery.service.mail;

import javax.mail.MessagingException;

public interface MailSendService {
    /**
     * Sending a new password to the shop
     *
     * @param email       - the shop e-mail
     * @param newPassword - the shop new password
     * @throws MessagingException
     */
    void sendNewShopPassword(String email, String newPassword) throws MessagingException;

    /**
     * Sending an order to the supplier
     *
     * @param supplierEmail - the supplier e-mail
     * @param pdf           - pdf document
     * @throws MessagingException
     */
    void sendNomenclatureForOrder(String supplierEmail, byte[] pdf) throws MessagingException;
}
