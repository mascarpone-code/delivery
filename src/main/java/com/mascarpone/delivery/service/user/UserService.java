package com.mascarpone.delivery.service.user;

import com.mascarpone.delivery.security.Role;
import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.payload.user.UserNameAddressRequest;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface UserService extends GeneralService<User> {
    User findByLogin(String login);

    Boolean existsByLogin(String login);

    Optional<User> findByLoginAndShopId(String login, Long shopId);

    Optional<User> findByLoginAndShopPrefix(String login, String shopPrefix);

    Optional<User> findByUuidAndShopId(UUID uuid, Long shopId);

    Page<User> findAllByShopIdAndRoles_Role(User filter, int page, int size);

    Optional<User> findByShopPrefixAndPhoneNumber(User user);

    Page<User> findAllByShop(User user, int page, int size);

    List<User> findAllByShopIdAndRoles_Role(Long shopId, Role role);

    List<User> findAllByShopBranchIdAndRoles_Role(Long shopBranchId, Role role);

    List<User> findAllByShopIdAndRoles_RoleAndCourierActive(Long shopId, Role role);

    Optional<User> findByLoginAndRoles_Role(String login, Role role);

    User getOne(UUID uuid);

    ResponseEntity<?> findUsersByPhoneNumberAndShopPrefix(String phoneNumber, Optional<Integer> page, UUID shopAdminUuid);

    ResponseEntity<?> getUserProfile(UUID customerUuid);

    ResponseEntity<?> updateCustomerProfile(UserNameAddressRequest request, UUID customerUuid);

    Optional<User> findByUuid(UUID userUuid);

//    Optional<User> findById(Long userId);
}
