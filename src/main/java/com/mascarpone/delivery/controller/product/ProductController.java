package com.mascarpone.delivery.controller.product;

import com.mascarpone.delivery.entity.product.Product;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.security.UserPrincipal;
import com.mascarpone.delivery.service.product.ProductService;
import com.mascarpone.delivery.utils.UserAuthChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {
    private final ProductService productService;

    /**
     * Creating and updating a product.
     *
     * @param userPrincipal - authenticated shop admin
     * @param product       - product entity
     * @return product entity
     */
    @PostMapping("/api/shop/product")
    public ResponseEntity<?> createOrUpdateProduct(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                   @RequestBody Product product) {
        UserAuthChecker.checkAuth(userPrincipal);
        return productService.createOrUpdateProduct(product, userPrincipal.getId());
    }

    /**
     * Shop gets a list of its products. Searchable by name and product group id.
     *
     * @param userPrincipal  - authenticated shop admin
     * @param productName    - product name
     * @param productGroupId - product group id
     * @param page           - page number
     * @return list of products
     */
    @GetMapping("/api/shop/product")
    public ResponseEntity<?> getAllProductsByShopAdmin(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                       @RequestParam("productName") Optional<String> productName,
                                                       @RequestParam("productGroupId") Optional<Long> productGroupId,
                                                       @RequestParam("page") Optional<Integer> page) {
        UserAuthChecker.checkAuth(userPrincipal);
        return productService.getAllProductsByShopAdmin(page, productName, productGroupId, userPrincipal.getId());
    }

    /**
     * Shop gets a product.
     *
     * @param userPrincipal - authenticated shop admin
     * @param id            - product id
     * @return product dto
     */
    @GetMapping("/api/shop/product/{id}")
    public ResponseEntity<?> getProductByShopAdmin(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                   @PathVariable("id") Long id) {
        UserAuthChecker.checkAuth(userPrincipal);
        return productService.getProductByShopAdmin(id, userPrincipal.getId());
    }

    /**
     * Deleting a product.
     *
     * @param userPrincipal - authenticated shop admin
     * @param id            - product id
     * @return
     */
    @DeleteMapping("/api/shop/product/{id}")
    public GeneralAnswer<String> deleteProduct(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                               @PathVariable("id") Long id) {
        UserAuthChecker.checkAuth(userPrincipal);
        return productService.deleteProduct(id, userPrincipal.getId());
    }

    /**
     * Customer gets a list of a shop's products.
     *
     * @param shopId - shop id
     * @return list of a products
     */
    @GetMapping("/api/user/product/shop")
    public ResponseEntity<?> getShopProductsByCustomer(@RequestParam("shopId") Long shopId) {
        return productService.getShopProductsByCustomer(shopId);
    }

    /**
     * Customer gets a list of a products of one product group.
     *
     * @param productGroupId - product group id
     * @return list of a products
     */
    @GetMapping("/api/user/product/group")
    public ResponseEntity<?> getProductGroupProductsByCustomer(@RequestParam("productGroupId") Long productGroupId) {
        return productService.getProductGroupProductsByCustomer(productGroupId);
    }

    /**
     * Customer gets a product
     *
     * @param id - product id
     * @return product dto
     */
    @GetMapping("/api/user/product/{id}")
    public ResponseEntity<?> getProductByCustomer(@PathVariable("id") Long id) {
        return productService.getProductByCustomer(id);
    }
}
