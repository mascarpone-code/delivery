package com.mascarpone.delivery.repository.nomenclature;

import com.mascarpone.delivery.entity.nomenclature.Nomenclature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

public interface NomenclatureRepository extends
        JpaRepository<Nomenclature, Long>,
        JpaSpecificationExecutor<Nomenclature>,
        QueryByExampleExecutor<Nomenclature> {
    /**
     * @return список номенклатуры на заказ, отсортированный по точкам и по поставщикам
     */
    @Query(value = "SELECT n FROM Nomenclature n WHERE n.quantity<n.minBalance ORDER BY n.shopBranch, n.supplier")
    List<Nomenclature> findNomenclatureForOrder();

    Optional<Nomenclature> findByName(String s);
}
