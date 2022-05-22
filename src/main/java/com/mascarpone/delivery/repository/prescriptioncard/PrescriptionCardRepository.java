package com.mascarpone.delivery.repository.prescriptioncard;

import com.mascarpone.delivery.entity.prescriptioncard.PrescriptionCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionCardRepository extends JpaRepository<PrescriptionCard, Long> {
}
