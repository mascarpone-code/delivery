package com.mascarpone.delivery.service.payment.tinkoff;

import com.google.common.base.Joiner;
import com.mascarpone.delivery.entity.bonustransaction.BonusTransaction;
import com.mascarpone.delivery.entity.enums.BonusTransactionType;
import com.mascarpone.delivery.entity.enums.PayTerminalType;
import com.mascarpone.delivery.entity.enums.UserOrderType;
import com.mascarpone.delivery.entity.order.UserOrder;
import com.mascarpone.delivery.entity.payment.tinkoff.PaymentTinkoff;
import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.payload.order.OrderForCookResponse;
import com.mascarpone.delivery.payload.socket.GeneralSocketResponse;
import com.mascarpone.delivery.payload.socket.sendmessage.SendMessageToCookResponse;
import com.mascarpone.delivery.repository.bonustransaction.BonusTransactionRepository;
import com.mascarpone.delivery.repository.order.OrderRepository;
import com.mascarpone.delivery.repository.payment.tinkoff.PaymentTinkoffRepository;
import com.mascarpone.delivery.repository.payterminal.PayTerminalRepository;
import com.mascarpone.delivery.repository.user.UserRepository;
import com.mascarpone.delivery.repository.userbonusaccount.UserBonusAccountRepository;
import com.mascarpone.delivery.security.Role;
import com.mascarpone.delivery.service.websocketpush.WebSocketPushService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.mascarpone.delivery.entity.enums.AccountActionType.NEWORDER;
import static com.mascarpone.delivery.entity.enums.AccountActionType.ORDERPAID;
import static com.mascarpone.delivery.utils.Constants.COOK_NEW_ORDER_MESSAGE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentTinkoffServiceImpl implements PaymentTinkoffService {
    private final PaymentTinkoffRepository paymentTinkoffRepository;
    private final OrderRepository orderRepository;
    private final PayTerminalRepository payTerminalRepository;
    private final BonusTransactionRepository bonusTransactionRepository;
    private final UserBonusAccountRepository userBonusAccountRepository;
    private final UserRepository userRepository;
    private final WebSocketPushService webSocketPushService;

    private final static String PASSWORD_KEY = "Password";
    private static final String TOKEN = "Token";

    @Override
    public List<PaymentTinkoff> getAll() {
        return paymentTinkoffRepository.findAll();
    }

    @Override
    public void save(PaymentTinkoff object) {
        paymentTinkoffRepository.save(object);
    }

    @Override
    public Optional<PaymentTinkoff> findByOrderIdAndStatusTinkoff(String orderId, String status) {
        return paymentTinkoffRepository.findByOrderIdAndStatusTinkoff(orderId, status);
    }

    @Override
    public List<PaymentTinkoff> findAllByOrderIdAndStatusTinkoffOrderByPaymentDate(String orderId, String status) {
        return paymentTinkoffRepository.findAllByOrderIdAndStatusTinkoffOrderByPaymentDate(orderId, status);
    }

    @Override
    public String tinkoffNotification(String tinkoffPaymentRequest, PayTerminalType payTerminalType) {
        var hashMapPaymentRequest = new HashMap<String, String>();

        var jObject = new JSONObject(tinkoffPaymentRequest); // json
        var terminalKey = jObject.getString("TerminalKey");
        var orderId = jObject.getString("OrderId");
        boolean success = jObject.getBoolean("Success");
        var status = jObject.getString("Status");
        long paymentId = jObject.getLong("PaymentId");
        var errorCode = jObject.getString("ErrorCode");
        long amount = jObject.getLong("Amount");
        long cardId = jObject.getLong("CardId");
        var pan = jObject.getString("Pan");
        var expDate = jObject.getString("ExpDate");
        var token = jObject.getString("Token");

        hashMapPaymentRequest.put("Amount", String.valueOf(amount));
        hashMapPaymentRequest.put("CardId", String.valueOf(cardId));
        hashMapPaymentRequest.put("ErrorCode", errorCode);
        hashMapPaymentRequest.put("ExpDate", expDate);
        hashMapPaymentRequest.put("OrderId", orderId);
        hashMapPaymentRequest.put("Pan", pan);
        hashMapPaymentRequest.put("PaymentId", String.valueOf(paymentId));
        hashMapPaymentRequest.put("Status", status);
        hashMapPaymentRequest.put("Success", String.valueOf(success));
        hashMapPaymentRequest.put("TerminalKey", terminalKey);
        hashMapPaymentRequest.put("Token", token);

        var userOrder = new UserOrder();

        if (orderRepository.findByUUID(orderId).isPresent()) {
            userOrder = orderRepository.findByUUID(orderId).get();
        } else if (orderRepository.findByUUIDIos(Long.parseLong(orderId)).isPresent()) {
            userOrder = orderRepository.findByUUIDIos(Long.parseLong(orderId)).get();
        }

        var payTerminal = payTerminalRepository.findByShopAndTerminalType(userOrder.getShop(), payTerminalType);
        var password = payTerminal.getPassword();

        try {
            var tokenSHA_256 = generateToken(hashMapPaymentRequest, password); //собранный токен из массива
            boolean validationToken = checkToken(hashMapPaymentRequest, tokenSHA_256); // сравнение токенов

            if (validationToken) {
                tokenValidated(jObject, userOrder);

                return "OK";
            } else {
                return "ERROR";
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "ERROR";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    /**
     * Сравнение двух токенов
     */
    private boolean checkToken(final Map<String, String> params, final String expectedToken) {
        final var actualToken = params.get(TOKEN);

        return !(expectedToken == null || !expectedToken.equals(actualToken));
    }

    /**
     * Генерация токена из Map
     */
    private String generateToken(final Map<String, String> parameters, String passwordValue) throws NoSuchAlgorithmException {
        final var sortedParameters = new TreeMap<>(parameters);
        sortedParameters.remove(TOKEN);
        sortedParameters.put(PASSWORD_KEY, passwordValue);
        final var paramString = Joiner.on("").skipNulls().join(sortedParameters.values());

        return sha256(paramString);
    }

    /**
     * Вычисление SHA-256
     */
    private String sha256(String base) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            var hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            var hexString = new StringBuilder();

            for (var b : hash) {
                var hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void orderBonusTransactions(User customer, UserOrder userOrder, Date date) {
        var bonusAccount = customer.getBonusAccount();
        var currentShop = userOrder.getShop();
        var userBonusAccountAmount = bonusAccount.getBonusAmount();
        var newBonus = userOrder.getPrice()
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                .multiply(currentShop.getOrderAmountPercent());

        if (newBonus.compareTo(BigDecimal.valueOf(0)) != 0) {
            var newBonusTransaction = new BonusTransaction();
            newBonusTransaction.setDateCreate(date);
            newBonusTransaction.setShop(currentShop);
            newBonusTransaction.setCustomer(customer);
            newBonusTransaction.setOrder(userOrder);
            newBonusTransaction.setType(BonusTransactionType.ENROLLMENT);
            newBonusTransaction.setBonusAmount(newBonus);
            bonusTransactionRepository.save(newBonusTransaction);

            bonusAccount.setBonusAmount(userBonusAccountAmount.add(newBonus));
            userBonusAccountRepository.save(bonusAccount);
        }

        var paidByBonus = userOrder.getPaidByBonus();

        if (paidByBonus.compareTo(BigDecimal.valueOf(0)) != 0) {
            var newBonusTransaction = new BonusTransaction();
            newBonusTransaction.setDateCreate(date);
            newBonusTransaction.setShop(currentShop);
            newBonusTransaction.setCustomer(customer);
            newBonusTransaction.setOrder(userOrder);
            newBonusTransaction.setType(BonusTransactionType.WRITEOFF);
            newBonusTransaction.setBonusAmount(paidByBonus);
            bonusTransactionRepository.save(newBonusTransaction);

            bonusAccount.setBonusAmount(userBonusAccountAmount.subtract(paidByBonus));
            userBonusAccountRepository.save(bonusAccount);
        }
    }

    private void paymentConfirmed(UserOrder userOrder, Date date) throws IOException {
        var currentShop = userOrder.getShop();
        var customer = userOrder.getCreator();

        if (currentShop.isBonusSystem()) {
            orderBonusTransactions(customer, userOrder, date);
        }

        userOrder.setPaid(true);

        if (orderRepository.findByCreatorUuidAndUserOrderTypeAndPaidIsTrue(customer.getUuid(), UserOrderType.FIRST).isEmpty()) {
            userOrder.setUserOrderType(UserOrderType.FIRST);
        }

        orderRepository.save(userOrder);

        var sendMessageResponse = new SendMessageToCookResponse();
        var response = new GeneralSocketResponse<>();
        sendMessageResponse.setOrder(new OrderForCookResponse(userOrder));
        response.setResult(sendMessageResponse);
        response.setAccountActionType(NEWORDER);
        sendMessageResponse.setMessage(COOK_NEW_ORDER_MESSAGE);

        // отправка пуша магазину
        webSocketPushService.sendMessageToShop(userOrder, ORDERPAID);
        // отправка пуша поварам магазина
        webSocketPushService.sendMessageToUsers(userRepository.findAllByShopIdAndRoles_Role(userOrder.getShop().getId(), Role.COOK),
                response,
                currentShop.getTokenForPushKitchen());

    }

    private void tokenValidated(JSONObject jObject, UserOrder userOrder) throws IOException {
        var nowDate = new Date();
        var paymentTinkoff = new PaymentTinkoff();

        paymentTinkoff.setAmount(String.valueOf(jObject.getLong("Amount")));
        paymentTinkoff.setCardId(String.valueOf(jObject.getLong("CardId")));
        paymentTinkoff.setErrorCode(jObject.getString("ErrorCode"));
        paymentTinkoff.setExpDate(jObject.getString("ExpDate"));
        paymentTinkoff.setPan(jObject.getString("Pan"));
        paymentTinkoff.setPaymentId(String.valueOf(jObject.getLong("PaymentId")));
        paymentTinkoff.setStatusTinkoff(jObject.getString("Status"));
        paymentTinkoff.setSuccess(String.valueOf(jObject.getBoolean("Success")));
        paymentTinkoff.setTerminalKey(jObject.getString("TerminalKey"));
        paymentTinkoff.setValidationToken(true);
        paymentTinkoff.setToken(jObject.getString("Token"));
        paymentTinkoff.setOrderId(jObject.getString("OrderId"));
        paymentTinkoff.setPaymentDate(nowDate);

        paymentTinkoffRepository.save(paymentTinkoff);

        switch (paymentTinkoff.getStatusTinkoff()) {
            //Оплата прошла
            case "CONFIRMED":
                paymentConfirmed(userOrder, nowDate);
                break;
            //оплата не прошла
            case "REJECTED":
                break;
        }
    }
}
