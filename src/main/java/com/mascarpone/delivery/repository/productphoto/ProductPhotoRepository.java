package com.mascarpone.delivery.repository.productphoto;

import com.mascarpone.delivery.entity.product.Product;
import com.mascarpone.delivery.entity.productphoto.ProductPhoto;
import com.mascarpone.delivery.entity.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {
    List<ProductPhoto> findAllByProductId(Long id);

    List<ProductPhoto> findAllByProduct(Product product);
}
