package com.mascarpone.delivery.controller.productgroup;

import com.mascarpone.delivery.entity.productgroup.ProductGroup;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.security.UserPrincipal;
import com.mascarpone.delivery.service.productgroup.ProductGroupService;
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
public class ProductGroupController {
    private final ProductGroupService productGroupService;

    /**
     * Creating and updating a product group.
     *
     * @param userPrincipal - authenticated shop admin
     * @param productGroup  - product group entity
     * @return product group entity
     */
    @PostMapping("/api/shop/productgroup")
    public ResponseEntity<?> createProductGroup(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @RequestBody ProductGroup productGroup) {
        UserAuthChecker.checkAuth(userPrincipal);
        return productGroupService.createOrUpdateProductGroup(productGroup, userPrincipal.getId());
    }

    /**
     * Shop gets a list of its product groups. Searchable by name.
     *
     * @param userPrincipal - authenticated shop admin
     * @param page          - page number
     * @param name          - product group name
     * @return list of product groups
     */
    @GetMapping("/api/shop/productgroup")
    public ResponseEntity<?> getAllProductGroups(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                 @RequestParam("page") Optional<Integer> page,
                                                 @RequestParam("name") Optional<String> name) {
        UserAuthChecker.checkAuth(userPrincipal);
        return productGroupService.getAllProductGroupsByShopAdmin(page, name, userPrincipal.getId());
    }

    /**
     * Shop gets its product group.
     *
     * @param userPrincipal - authenticated shop admin
     * @param id            - product group id
     * @return product group entity
     */
    @GetMapping("/api/shop/productgroup/{id}")
    public ResponseEntity<?> getProductGroup(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @PathVariable("id") Long id) {
        UserAuthChecker.checkAuth(userPrincipal);
        return productGroupService.getProductGroupByShopAdmin(id, userPrincipal.getId());
    }

    /**
     * Deleting an empty product group.
     *
     * @param userPrincipal - authenticated shop admin
     * @param id            - product group id
     * @return
     */
    @DeleteMapping("/api/shop/productgroup/{id}")
    public GeneralAnswer deleteProductGroup(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @PathVariable("id") Long id) {
        UserAuthChecker.checkAuth(userPrincipal);
        return productGroupService.deleteProductGroup(id, userPrincipal.getId());
    }

    /**
     * Customer gets a list of product groups.
     *
     * @param shopId - shop id
     * @return list of product groups
     */
    @GetMapping("/api/user/productgroup/{shopId}")
    public ResponseEntity<?> getProductGroupsByCustomer(@PathVariable("shopId") Long shopId) {
        return productGroupService.getProductGroupsByCustomer(shopId);
    }
}
