package com.mascarpone.delivery.repository.user;

import com.mascarpone.delivery.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void itShouldFindUserByLogin() {
        // given
        String login = "user_login";
        User expectedUser = new User(1L, login);
        userRepository.save(expectedUser);

        // when
        User actualUser = userRepository.findByLogin(login);

        // then
        assertThat(actualUser.getLogin()).isEqualTo(expectedUser.getLogin());
    }

    @Test
    void itShouldNotFindUserByLogin() {
        // given
        String login = "user_login";

        // when
        User actualUser = userRepository.findByLogin(login);

        // then
        assertThat(actualUser).isNull();
    }
}
