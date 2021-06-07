package com.mascarpone.delivery.repository.nomenclature;

import com.mascarpone.delivery.entity.nomenclature.Nomenclature;
import com.mascarpone.delivery.entity.shopbranch.ShopBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NomenclatureRepository extends
        JpaRepository<Nomenclature, Long>,
        JpaSpecificationExecutor<Nomenclature>,
        QueryByExampleExecutor<Nomenclature> {
    /**
     * @return список номенклатуры на заказ, отсортированный по точкам и по поставщикам
     */
    @Query(value = "SELECT n FROM Nomenclature n WHERE n.quantity<n.minBalance ORDER BY n.shopBranch, n.supplier")
    List<Nomenclature> findNomenclatureForOrder();

    /**
     * @return список номенклатуры на заказ для определенной точки, отсортированный по поставщикам
     */
    @Query(value = "SELECT n FROM Nomenclature n WHERE n.quantity<n.minBalance AND n.shopBranch = :shopBranch ORDER BY n.supplier")
    List<Nomenclature> findNomenclatureForOrderForShopBranchId(@Param("shopBranch") ShopBranch shopBranch);

    Optional<Nomenclature> findByName(String s);
}
