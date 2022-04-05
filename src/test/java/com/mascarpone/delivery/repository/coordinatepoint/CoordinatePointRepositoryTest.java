package com.mascarpone.delivery.repository.coordinatepoint;

import com.mascarpone.delivery.entity.coordinatepoint.CoordinatePoint;
import com.mascarpone.delivery.entity.deliveryarea.DeliveryArea;
import com.mascarpone.delivery.repository.deliveryarea.DeliveryAreaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CoordinatePointRepositoryTest {
    @Autowired
    private CoordinatePointRepository coordinatePointRepository;

    @Autowired
    private DeliveryAreaRepository deliveryAreaRepository;

    @Test
    void it_should_find_all_coordinate_points_by_delivery_area_id() {
        //given
        DeliveryArea area = new DeliveryArea();
        deliveryAreaRepository.save(area);

        CoordinatePoint point1 = new CoordinatePoint();
        CoordinatePoint point2 = new CoordinatePoint();
        point1.setDeliveryArea(area);
        point2.setDeliveryArea(area);
        coordinatePointRepository.saveAll(Arrays.asList(point1, point2));

        //when
        List<CoordinatePoint> notEmptyList = coordinatePointRepository.findAllByDeliveryAreaId(1L);
        List<CoordinatePoint> emptyList = coordinatePointRepository.findAllByDeliveryAreaId(2L);

        //then
        assertThat(notEmptyList.size()).isEqualTo(2);
        assertThat(emptyList.size()).isEqualTo(0);
    }

    @Test
    void it_should_not_find_all_coordinate_points_by_delivery_area_id() {
        //given
        DeliveryArea area = new DeliveryArea();
        deliveryAreaRepository.save(area);

        CoordinatePoint point1 = new CoordinatePoint();
        CoordinatePoint point2 = new CoordinatePoint();
        point1.setDeliveryArea(area);
        point2.setDeliveryArea(area);
        coordinatePointRepository.saveAll(Arrays.asList(point1, point2));

        //when
        List<CoordinatePoint> notEmptyList = coordinatePointRepository.findAllByDeliveryAreaId(1L);
        List<CoordinatePoint> emptyList = coordinatePointRepository.findAllByDeliveryAreaId(2L);

        //then
        assertThat(notEmptyList.size()).isEqualTo(2);
        assertThat(emptyList.size()).isEqualTo(0);
    }
}