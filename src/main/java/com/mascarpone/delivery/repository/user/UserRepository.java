package com.mascarpone.delivery.repository.user;

import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    Optional<User> findByLoginAndShopId(String login, Long shopId);

    Optional<User> findByLoginAndShopPrefix(String login, String shopPrefix);

    Optional<User> findByUuidAndShopId(UUID uuid, Long shopId);

    List<User> findAllByShopIdAndRoles_Role(Long shopId, Role role);

    List<User> findAllByShopBranchIdAndRoles_Role(Long shopBranchId, Role role);

    List<User> findAllByShopIdAndRoles_RoleAndCourierActiveTrue(Long shopId, Role role);

    Optional<User> findByLoginAndRoles_Role(String login, Role role);

    Boolean existsByLogin(String login);

    User findByLogin(String login);
}
