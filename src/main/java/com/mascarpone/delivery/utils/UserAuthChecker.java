package com.mascarpone.delivery.utils;

import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.security.UserPrincipal;
import com.mascarpone.delivery.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.mascarpone.delivery.exception.ExceptionConstants.USER_BLOCKED;
import static com.mascarpone.delivery.exception.ExceptionConstants.USER_NOT_FOUND;

@Slf4j
@Component
public class UserAuthChecker {
    private static UserService userService;

    public UserAuthChecker(UserService userService) {
        UserAuthChecker.userService = userService;
    }

    public static void checkAuth(UserPrincipal userPrincipal) {
        try {
            if (userPrincipal == null) {
                throw new BadRequestException(USER_NOT_FOUND);
            }

            User user = userService.getOne(userPrincipal.getId());
            if (user == null) {
                throw new BadRequestException(USER_NOT_FOUND);
            }

            if (!user.getEnabled()) {
                throw new BadRequestException(USER_BLOCKED);
            }
        } catch (BadRequestException ex) {
            log.error("Authentication error", ex);
            throw ex;
        }
    }
}
