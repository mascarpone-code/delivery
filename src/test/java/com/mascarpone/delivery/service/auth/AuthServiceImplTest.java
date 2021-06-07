package com.mascarpone.delivery.service.auth;

import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.entity.userrole.UserRole;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.payload.user.UserLoginPasswordRequest;
import com.mascarpone.delivery.repository.shop.ShopRepository;
import com.mascarpone.delivery.repository.shopbranch.ShopBranchRepository;
import com.mascarpone.delivery.repository.user.UserRepository;
import com.mascarpone.delivery.repository.userbonusaccount.UserBonusAccountRepository;
import com.mascarpone.delivery.repository.userrole.UserRoleRepository;
import com.mascarpone.delivery.security.TokenProvider;
import com.mascarpone.delivery.service.mail.MailSendService;
import com.mascarpone.delivery.service.smssender.SmsSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.mascarpone.delivery.exception.ExceptionConstants.NO_ACCESS;
import static com.mascarpone.delivery.exception.ExceptionConstants.USER_ALREADY_EXISTS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRoleRepository userRoleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ShopRepository shopRepository;
    @Mock
    private MailSendService mailSendService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private SmsSenderService smsSenderService;
    @Mock
    private UserBonusAccountRepository userBonusAccountRepository;
    @Mock
    private ShopBranchRepository shopBranchRepository;
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(
                userRepository,
                userRoleRepository,
                passwordEncoder,
                shopRepository,
                mailSendService,
                authenticationManager,
                tokenProvider,
                smsSenderService,
                userBonusAccountRepository,
                shopBranchRepository);
    }

    @Test
    void should_register_root_admin() {
        // given
        String login = "login";
        String password = "password";
        UserLoginPasswordRequest request =
                new UserLoginPasswordRequest(login, password, "11228888");
        // when
        authService.registerRootAdmin(request);
        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(2)).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser.getLogin()).isEqualTo(login);

        ArgumentCaptor<UserRole> userRoleArgumentCaptor = ArgumentCaptor.forClass(UserRole.class);
        verify(userRoleRepository).save(userRoleArgumentCaptor.capture());
        UserRole capturedUserRole = userRoleArgumentCaptor.getValue();
        assertThat(capturedUserRole.getUser().getLogin()).isEqualTo(login);
    }

    @Test
    void should_throw_because_of_wrong_secret_word() {
        // given
        String login = "login";
        String password = "password";
        UserLoginPasswordRequest request =
                new UserLoginPasswordRequest(login, password, "11228887");
        // when
        // then
        assertThatThrownBy(() -> authService.registerRootAdmin(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(NO_ACCESS);
    }

    @Test
    void should_throw_because_of_email_taken() {
        // given
        String login = "login";
        String password = "password";
        UserLoginPasswordRequest request =
                new UserLoginPasswordRequest(login, password, "11228888");

        given(userRepository.existsByLogin(anyString())).willReturn(true);
        // when
        // then
        assertThatThrownBy(() -> authService.registerRootAdmin(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(USER_ALREADY_EXISTS);
    }
}
