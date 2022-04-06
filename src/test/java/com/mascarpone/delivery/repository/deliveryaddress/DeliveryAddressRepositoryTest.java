package com.mascarpone.delivery.repository.deliveryaddress;

import com.mascarpone.delivery.entity.deliveryaddress.DeliveryAddress;
import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
class DeliveryAddressRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

//    private final User user = new User();
//    private final DeliveryAddress address1 = new DeliveryAddress();
//    private final DeliveryAddress address2 = new DeliveryAddress();
//
//    @BeforeEach
//    void setUp() {
//        userRepository.deleteAll();
//        user.setUuid(1L);
//        userRepository.save(user);
//
//        System.err.println("USER ID = " + userRepository.findAll().get(0).getUuid());
//
//        deliveryAddressRepository.deleteAll();
//        address1.setUser(user);
//        address2.setUser(user);
//        address1.setCurrent(true);
//        deliveryAddressRepository.saveAll(Arrays.asList(address1, address2));
//    }

    @Test
    void it_should_find_all_delivery_addresses_by_user_uuid() {
        //given
        User user = new User();
        userRepository.save(user);

        DeliveryAddress address1 = new DeliveryAddress();
        DeliveryAddress address2 = new DeliveryAddress();
        address1.setUser(user);
        address2.setUser(user);
        deliveryAddressRepository.saveAll(Arrays.asList(address1, address2));

        //when
        List<DeliveryAddress> notEmptyList = deliveryAddressRepository.findAllByUserUuid(user.getUuid());
        List<DeliveryAddress> emptyList = deliveryAddressRepository.findAllByUserUuid(UUID.randomUUID());

        //then
        assertEquals(2, notEmptyList.size());
        assertEquals(0, emptyList.size());
    }

    @Test
    void findByUserIdAndCurrentIsTrue() {
        //given
        User user = new User();
        userRepository.save(user);

        DeliveryAddress address1 = new DeliveryAddress();
        DeliveryAddress address2 = new DeliveryAddress();
        address1.setCurrent(true);
        address1.setUser(user);
        address2.setUser(user);
        deliveryAddressRepository.saveAll(Arrays.asList(address1, address2));

        //when
        Optional<DeliveryAddress> current = deliveryAddressRepository.findByUserUuidAndCurrentIsTrue(user.getUuid());

        //then
        assertEquals(1L, current.get().getId());
        assertNotEquals(2L, current.get().getId());
    }

    @Test
    void itShouldFindDeliveryAddressByIdAndUserId() {
        //given
        User user = new User();
        userRepository.save(user);

        DeliveryAddress address1 = new DeliveryAddress();
        DeliveryAddress address2 = new DeliveryAddress();
        address1.setUser(user);
        address2.setUser(user);
        deliveryAddressRepository.saveAll(Arrays.asList(address1, address2));

        System.err.println("USER ID = " + userRepository.findAll().get(0).getUuid());
        System.err.println("ADDRESS ID = " + deliveryAddressRepository.findAll().get(0).getId());

        //when
        Optional<DeliveryAddress> current = deliveryAddressRepository.findByIdAndUserUuid(7L, user.getUuid());

        //then
        assertThat(current.isPresent()).isTrue();
    }

    @Test
    void itShouldNotFindDeliveryAddressByIdAndUserId() {
        //given
        User user = new User();
        userRepository.save(user);

        DeliveryAddress address1 = new DeliveryAddress();
        DeliveryAddress address2 = new DeliveryAddress();
        address1.setUser(user);
        address2.setUser(user);
        deliveryAddressRepository.saveAll(Arrays.asList(address1, address2));

        //when
        Optional<DeliveryAddress> current = deliveryAddressRepository.findByIdAndUserUuid(1L, user.getUuid());

        //then
        assertThat(current.isPresent()).isFalse();
    }
}