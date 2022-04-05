package com.mascarpone.delivery.repository.productphoto;

import com.mascarpone.delivery.entity.product.Product;
import com.mascarpone.delivery.entity.productphoto.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {
    List<ProductPhoto> findAllByProductId(Long id);

    List<ProductPhoto> findAllByProduct(Product product);
}
