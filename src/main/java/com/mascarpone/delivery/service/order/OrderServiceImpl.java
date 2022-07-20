package com.mascarpone.delivery.service.order;

import com.mascarpone.delivery.entity.enums.OrderStatus;
import com.mascarpone.delivery.entity.enums.UserOrderType;
import com.mascarpone.delivery.entity.order.UserOrder;
import com.mascarpone.delivery.entity.orderaccessory.OrderAccessory;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.payload.order.CreatedOrderResponse;
import com.mascarpone.delivery.payload.order.CreatedOrderWithShopBranchResponse;
import com.mascarpone.delivery.payload.order.UserOrderListResponse;
import com.mascarpone.delivery.payload.order.UserOrderResponse;
import com.mascarpone.delivery.payload.shopbranch.ShopBranchResponse;
import com.mascarpone.delivery.repository.accessory.AccessoryRepository;
import com.mascarpone.delivery.repository.deliveryarea.DeliveryAreaRepository;
import com.mascarpone.delivery.repository.modifier.ModifierRepository;
import com.mascarpone.delivery.repository.order.OrderRepository;
import com.mascarpone.delivery.repository.orderaccessory.OrderAccessoryRepository;
import com.mascarpone.delivery.repository.orderproduct.OrderProductRepository;
import com.mascarpone.delivery.repository.product.ProductRepository;
import com.mascarpone.delivery.repository.shopbranch.ShopBranchRepository;
import com.mascarpone.delivery.repository.user.UserRepository;
import com.mascarpone.delivery.service.websocketpush.WebSocketPushService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.mascarpone.delivery.entity.enums.AccountActionType.NEWORDER;
import static com.mascarpone.delivery.entity.enums.OrderType.DELIVERY;
import static com.mascarpone.delivery.exception.ExceptionConstants.*;
import static com.mascarpone.delivery.utils.Constants.*;
import static com.mascarpone.delivery.utils.Utils.formatOrderDate;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModifierRepository modifierRepository;
    private final DeliveryAreaRepository deliveryAreaRepository;
    private final OrderProductRepository orderProductRepository;
    private final AccessoryRepository accessoryRepository;
    private final OrderAccessoryRepository orderAccessoryRepository;
    private final ShopBranchRepository shopBranchRepository;
    private final WebSocketPushService webSocketPushService;

    @Override
    public List<UserOrder> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public void save(UserOrder object) {
        orderRepository.save(object);
    }

    @Override
    public Page<UserOrder> findAllByShopIdOrderByDateCreateDesc(Long shopId, int page, int size) {
        return orderRepository.findAllByShopIdOrderByDateCreateDesc(shopId, PageRequest.of(page, size));
    }

    @Override
    public Page<UserOrder> findAllByCreatorUuidOrderByDateCreateDesc(UUID userUuid, int page, int size) {
        return orderRepository.findAllByCreatorUuidOrderByDateCreateDesc(userUuid, PageRequest.of(page, size));
    }

    @Override
    public Optional<UserOrder> findByIdAndCreatorUuid(Long id, UUID userUuid) {
        return orderRepository.findByIdAndCreatorUuid(id, userUuid);
    }

    @Override
    public Long countAllByShopId(Long shopId) {
        return orderRepository.countAllByShopId(shopId);
    }

    @Override
    public Optional<UserOrder> findByUuid(String uuid) {
        return orderRepository.findByUUID(uuid);
    }

    @Override
    public Optional<UserOrder> findByUuidIos(Long uuidIos) {
        return orderRepository.findByUUIDIos(uuidIos);
    }

    @Override
    public Page<UserOrder> findAllByCourierUuidAndStatusOrderByDateCreateDesc(UUID userUuid, OrderStatus orderStatus, int page, int size) {
        return orderRepository.findAllByCourierUuidAndStatusOrderByDateCreateDesc(userUuid, orderStatus, PageRequest.of(page, size));
    }

    @Override
    public Page<UserOrder> findAllByShopIdAndStatusOrderByDateCreateDesc(Long shopId, OrderStatus orderStatus, int page, int size) {
        return orderRepository.findAllByShopIdAndStatusOrderByDateCreateDesc(shopId, orderStatus, PageRequest.of(page, size));
    }

    @Override
    public Optional<UserOrder> findByIdAndCourierId(Long id, UUID courierUuid) {
        return orderRepository.findByIdAndCourierUuid(id, courierUuid);
    }

    @Override
    public Optional<UserOrder> findByIdAndShopId(Long id, Long shopId) {
        return orderRepository.findByIdAndShopId(id, shopId);
    }

    @Override
    public List<UserOrder> findAllByIsPaidFalse() {
        return orderRepository.findAllByPaidIsFalse();
    }

    @Override
    public Optional<UserOrder> findByCreatorUuidAndUserOrderType(UUID creatorUuid, UserOrderType userOrderType) {
        return orderRepository.findByCreatorUuidAndUserOrderType(creatorUuid, userOrderType);
    }

    @Override
    public Optional<UserOrder> findByCreatorUuidAndUserOrderTypeAndPaidIsTrue(UUID creatorUuid, UserOrderType userOrderType) {
        return orderRepository.findByCreatorUuidAndUserOrderTypeAndPaidIsTrue(creatorUuid, userOrderType);
    }

    @Override
    public UserOrder findFirstByShopIdOrderByDateCreateDesc(Long shopId) {
        return orderRepository.findFirstByShopIdOrderByDateCreateDesc(shopId);
    }

    @Override
    public List<UserOrder> findAllByShopIdAndOrderNumberNotNullOrderByDateCreateDesc(Long shopId) {
        return orderRepository.findAllByShopIdAndOrderNumberNotNullOrderByDateCreateDesc(shopId);
    }

    @Override
    public Page<UserOrder> findAllByShopBranchIdAndStatusAndPaidIsTrueOrderByDateCreateDesc(Long branchId, OrderStatus status, int page, int size) {
        return orderRepository.findAllByShopBranchIdAndStatusAndPaidIsTrueOrderByDateCreateDesc(branchId, status, PageRequest.of(page, size));
    }

    @Override
    public Page<UserOrder> findAllByShopIdAndStatusAndPaidIsTrueOrderByDateCreateDesc(Long shopId, OrderStatus status, int page, int size) {
        return orderRepository.findAllByShopIdAndStatusAndPaidIsTrueOrderByDateCreateDesc(shopId, status, PageRequest.of(page, size));
    }

    @Override
    public Page<UserOrder> getOrdersForKitchen(Shop shop, boolean paid, OrderStatus status1, OrderStatus status2, int page, int size) {
        return orderRepository.getOrdersForKitchen(shop, paid, status1, status2, PageRequest.of(page, size, Sort.Direction.DESC, "dateCreate"));
    }

    @Override
    public List<UserOrder> findAllByShopId(Long shopId) {
        return orderRepository.findAllByShopId(shopId);
    }

    @Override
    public List<UserOrder> findAllByShopIdAndDateCreateBetween(Long shopId, Date dateFrom, Date dateTo) {
        return orderRepository.findAllByShopIdAndDateCreateBetween(shopId, dateFrom, dateTo);
    }

    @Override
    public Long countAllByShopIdAndDateCreateBetween(Long shopId, Date dateFrom, Date dateTo) {
        return orderRepository.countAllByShopIdAndDateCreateBetween(shopId, dateFrom, dateTo);
    }

    @Override
    public void delete(UserOrder order) {
        orderRepository.delete(order);
    }

    @Override
    public ResponseEntity<?> createOrder(UserOrder order, UUID customerUuid) throws IOException {
        var customer = userRepository.getOne(customerUuid);
        var currentShop = customer.getShop();
        var orderProducts = order.getOrderProducts();
        var orderCreateDate = new Date();

        if (currentShop.getOrderBeginTime() != null && currentShop.getOrderEndTime() != null
                && !(currentShop.getOrderBeginTime().equals(currentShop.getOrderEndTime()))) {
            int hourBegin = Integer.parseInt(currentShop.getOrderBeginTime().substring(0, 2));
            int minuteBegin = Integer.parseInt(currentShop.getOrderBeginTime().substring(3, 5));

            int hourEnd = Integer.parseInt(currentShop.getOrderEndTime().substring(0, 2));
            int minuteEnd = Integer.parseInt(currentShop.getOrderEndTime().substring(3, 5));

            var calendarNow = Calendar.getInstance();
            calendarNow.setTime(orderCreateDate);

            if (currentShop.getTimeZone() != null) {
                calendarNow.setTimeZone(TimeZone.getTimeZone(currentShop.getTimeZone()));
            }

            int hourNow = calendarNow.get(Calendar.HOUR_OF_DAY);
            int minuteNow = calendarNow.get(Calendar.MINUTE);

            var a = hourNow > hourEnd;
            var b = hourNow < hourBegin;
            var c = (hourNow == hourBegin) && (minuteNow < minuteBegin);
            var d = (hourNow == hourEnd) && (minuteNow >= minuteEnd);

            if (hourBegin <= hourEnd) {
                if (a || b || c || d) {
                    throw new BadRequestException(NOT_ORDER_TIME);
                }
            } else {
                if (a && b || d || c) {
                    throw new BadRequestException(NOT_ORDER_TIME);
                }
            }
        }

        if ((order.getType() == DELIVERY) && (order.getAddress() == null)) {
            throw new BadRequestException(NO_DELIVERY_ADDRESS);
        }

        if (orderProducts == null) {
            throw new BadRequestException(ORDER_IS_EMPTY);
        }

        var totalOrderPrice = BigDecimal.valueOf(0);
        double totalOrderProteins = 0D;
        double totalOrderFats = 0D;
        double totalOrderCarbohydrates = 0D;
        double totalOrderKiloCalories = 0D;
        double totalOrderWeight = 0D;

        for (var orderProduct : orderProducts) {
            if (orderProduct.getCount() == null) {
                throw new BadRequestException(NO_COUNT);
            }

            int productCount = orderProduct.getCount();
            long productId = orderProduct.getProductId();
            var orderModifiers = orderProduct.getOrderModifiers();

            var product = productRepository.findByIdAndShop(productId, currentShop)
                    .orElseThrow(() -> new BadRequestException(PRODUCT_NOT_FOUND));
            totalOrderPrice = totalOrderPrice.add((product.getPrice().multiply(BigDecimal.valueOf(productCount))));
            totalOrderProteins += product.getProteins() * productCount;
            totalOrderFats += product.getFats() * productCount;
            totalOrderCarbohydrates += product.getCarbohydrates() * productCount;
            totalOrderKiloCalories += product.getKiloCalories() * productCount;
            totalOrderWeight += product.getWeight() * productCount;

            orderProduct.setName(product.getName());
            orderProduct.setDescription(product.getDescription());
            orderProduct.setUnit(product.getUnit());

            orderProduct.setPrice(product.getPrice());
            orderProduct.setProteins(product.getProteins());
            orderProduct.setFats(product.getFats());
            orderProduct.setCarbohydrates(product.getCarbohydrates());
            orderProduct.setKiloCalories(product.getKiloCalories());
            orderProduct.setWeight(product.getWeight());
            orderProduct.setCreator(customer);
            orderProduct.setProductGroupId(product.getProductGroup().getId());
            orderProduct.setShop(product.getShop());

            if (orderModifiers != null) {
                for (var orderModifier : orderModifiers) {
                    orderModifier.setOrderProduct(orderProduct);

                    if (orderModifier.getCount() == null) {
                        throw new BadRequestException(NO_COUNT);
                    }

                    int modifierCount = orderModifier.getCount();
                    long modifierId = orderModifier.getModifierId();
                    var modifier = modifierRepository.findByIdAndProducts(modifierId, product)
                            .orElseThrow(() -> new BadRequestException(MODIFIER_NOT_FOUND));
                    var modifierOrderPrice =
                            modifier.getPrice()
                                    .multiply(BigDecimal.valueOf(modifierCount))
                                    .multiply(BigDecimal.valueOf(productCount));
                    double modifierProteins = modifier.getProteins() * modifierCount * productCount;
                    double modifierFats = modifier.getFats() * modifierCount * productCount;
                    double modifierCarbohydrates = modifier.getCarbohydrates() * modifierCount * productCount;
                    double modifierKiloCalories = modifier.getKiloCalories() * modifierCount * productCount;
                    double modifierWeight = modifier.getWeight() * modifierCount * productCount;

                    orderModifier.setPrice(modifier.getPrice());
                    orderModifier.setProteins(modifier.getProteins());
                    orderModifier.setFats(modifier.getFats());
                    orderModifier.setCarbohydrates(modifier.getCarbohydrates());
                    orderModifier.setKiloCalories(modifier.getKiloCalories());
                    orderModifier.setWeight(modifier.getWeight());
                    orderModifier.setName(modifier.getName());
                    orderModifier.setDescription(modifier.getDescription());
                    orderModifier.setUnit(modifier.getUnit());
                    orderModifier.setCreator(customer);
                    orderModifier.setShop(currentShop);
                    orderModifier.setDateCreate(orderCreateDate);

                    totalOrderPrice = totalOrderPrice.add(modifierOrderPrice);
                    totalOrderProteins += modifierProteins;
                    totalOrderFats += modifierFats;
                    totalOrderCarbohydrates += modifierCarbohydrates;
                    totalOrderKiloCalories += modifierKiloCalories;
                    totalOrderWeight += modifierWeight;
                }
            }
        }

        order.setFullPrice(totalOrderPrice);

        if (currentShop.isBonusSystem()) {
            var customerPayByBonus = order.getPaidByBonus();
            var maximumBonusPay = order.getFullPrice()
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                    .multiply(currentShop.getBonusPayAmount());

            if (customerPayByBonus.compareTo(maximumBonusPay) > 0) {
                throw new BadRequestException(OUT_OFF_BONUS_LIMIT);
            }

            var customerBonusAccountAmount = customer.getBonusAccount().getBonusAmount();

            if (customerPayByBonus.compareTo(customerBonusAccountAmount) > 0) {
                throw new BadRequestException(NOT_ENOUGH_BONUSES);
            }

            order.setPaidByBonus(customerPayByBonus);
            order.setPrice(totalOrderPrice.subtract(customerPayByBonus));
        } else {
            order.setPrice(totalOrderPrice);
        }

        order.setProteins(totalOrderProteins);
        order.setFats(totalOrderFats);
        order.setCarbohydrates(totalOrderCarbohydrates);
        order.setKiloCalories(totalOrderKiloCalories);
        order.setWeight(totalOrderWeight);

        order.setDateCreate(orderCreateDate);
        order.setCreator(customer);
        order.setShop(currentShop);
        order.setStatus(OrderStatus.FORMED);

        if (order.getType() == DELIVERY && order.getDeliveryAreaId() != null) {
            var deliveryArea = deliveryAreaRepository.getOne(order.getDeliveryAreaId());

            if (totalOrderPrice.compareTo(deliveryArea.getMinimumOrderAmount()) < 0) {
                throw new BadRequestException(LOW_ORDER_PRICE);
            }
        }

        if (orderRepository.findFirstByShopOrderByDateCreateDesc(currentShop) != null) {
            order.setOrderNumber(orderRepository.findFirstByShopOrderByDateCreateDesc(currentShop).getOrderNumber() + 1);
        } else {
            order.setOrderNumber(DEFAULT_ORDER_NUMBER);
        }

        if (order.getPayType() != null) {
            order.setPayType(order.getPayType());
            order.setPaid(true);

            if (orderRepository.findByCreatorUuidAndUserOrderTypeAndPaidIsTrue(customer.getUuid(), UserOrderType.FIRST).isEmpty()) {
                order.setUserOrderType(UserOrderType.FIRST);
            }
        }

        orderRepository.save(order);

        order.setUUID(currentShop.getPrefix().toUpperCase() + formatOrderDate(orderCreateDate) + order.getOrderNumber());

        orderProducts
                .forEach(orderProduct -> {
                    orderProduct.setOrder(order);
                    orderProductRepository.save(orderProduct);
                });

        var orderAccessories = order.getOrderAccessories();
        var currentOrderAccessories = new ArrayList<OrderAccessory>();

        if (orderAccessories != null) {
            orderAccessories
                    .forEach(orderAccessory -> {
                        orderAccessory.setName(accessoryRepository.getOne(orderAccessory.getAccessoryId()).getName());
                        orderAccessory.setOrder(order);
                        orderAccessoryRepository.save(orderAccessory);
                        currentOrderAccessories.add(orderAccessory);
                    });
        }

        order.setOrderAccessories(currentOrderAccessories);

        if (shopBranchRepository.findByIdAndShop(order.getShopBranchId(), currentShop).isPresent()) {
            var shopBranch = shopBranchRepository.findByIdAndShop(order.getShopBranchId(), currentShop).get();
            webSocketPushService.sendMessageToShop(order, NEWORDER);

            var shopBranchResponse = new ShopBranchResponse(shopBranch);
            var response = new CreatedOrderWithShopBranchResponse(shopBranchResponse, order);

            return ResponseEntity.ok(response);
        } else {
            webSocketPushService.sendMessageToShop(order, NEWORDER);
            var response = new CreatedOrderResponse(order);

            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<?> getOrdersByUser(Optional<Integer> page, UUID customerUuid) {
        var orders = orderRepository.findAllByCreatorUuidOrderByDateCreateDesc(customerUuid,
                PageRequest.of(page.orElse(DEFAULT_PAGE), FETCH_RECORD_COUNT));

        var userOrderResponses = new ArrayList<UserOrderResponse>();

        for (var order : orders) {
            var branch = shopBranchRepository.findByIdAndShop(order.getShopBranchId(), order.getShop())
                    .orElseThrow(() -> new BadRequestException(BRANCH_NOT_FOUND));
            var shopBranchResponse = new ShopBranchResponse(branch);
            var userOrderResponse = new UserOrderResponse(shopBranchResponse, order);

            userOrderResponses.add(userOrderResponse);
        }

        var response = new UserOrderListResponse(userOrderResponses, orders.getTotalElements());

        return ResponseEntity.ok(response);
    }
}
