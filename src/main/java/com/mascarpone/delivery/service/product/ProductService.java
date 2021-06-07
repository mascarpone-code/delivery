package com.mascarpone.delivery.service.product;

import com.mascarpone.delivery.entity.product.Product;
import com.mascarpone.delivery.entity.productgroup.ProductGroup;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface ProductService extends GeneralService<Product> {
    Page<Product> findAllByShopIdPageable(Long shopId, int page, int size);

    Optional<Product> findById(Long id);

    Optional<Product> findByIdAndShop(Long id, Shop shop);

    List<Product> findAllByProductGroup(ProductGroup productGroup);

    List<Product> findAllByShop(Shop shop);

    Page<Product> findAllByShopIdAndNamePageable(Product product, int page, int size);

    ResponseEntity<?> createOrUpdateProduct(Product product, Long shopAdminId);

    ResponseEntity<?> getAllProductsByShopAdmin(Optional<Integer> page, Optional<String> productName, Optional<Long> productGroupId, Long shopAdminId);

    ResponseEntity<?> getProductByShopAdmin(Long id, Long shopAdminId);

    GeneralAnswer<String> deleteProduct(Long id, Long shopAdminId);

    ResponseEntity<?> getShopProductsByCustomer(Long shopId);

    ResponseEntity<?> getProductGroupProductsByCustomer(Long productGroupId);

    ResponseEntity<?> getProductByCustomer(Long id);
}
