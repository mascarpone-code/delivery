package com.mascarpone.delivery.repository.prescriptioncard;

import com.mascarpone.delivery.entity.prescriptioncard.PrescriptionCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionCardRepository extends JpaRepository<PrescriptionCard, Long> {
}
