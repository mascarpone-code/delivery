package com.mascarpone.delivery.controller.shopbranch;

import com.mascarpone.delivery.entity.shopbranch.ShopBranch;
import com.mascarpone.delivery.security.UserPrincipal;
import com.mascarpone.delivery.service.shopbranch.ShopBranchService;
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
public class ShopBranchController {
    private final ShopBranchService shopBranchService;

    /**
     * Creating and updating a shop branch.
     *
     * @param userPrincipal - authenticated shop admin
     * @param branch        - shop branch entity
     * @return shop branch entity
     */
    @PostMapping("/api/shop/branch")
    public ResponseEntity<?> createShopBranch(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                              @RequestBody ShopBranch branch) {
        UserAuthChecker.checkAuth(userPrincipal);
        return shopBranchService.createOrUpdateShopBranch(branch, userPrincipal.getId());
    }

    /**
     * The shop gets a list of its branches. Searchable by name.
     *
     * @param userPrincipal - authenticated shop admin
     * @param page          - page number
     * @param name          - shop branch name
     * @return list of shop branches
     */
    @GetMapping("/api/shop/branch")
    public ResponseEntity<?> getBranchesByShop(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                               @RequestParam("page") Optional<Integer> page,
                                               @RequestParam("name") Optional<String> name) {
        UserAuthChecker.checkAuth(userPrincipal);
        return shopBranchService.getShopBranchesByShopAdmin(page, name, userPrincipal.getId());
    }

    /**
     * The shop gets its branch.
     *
     * @param userPrincipal - authenticated shop admin
     * @param id            - shop branch id
     * @return shop branch entity
     */
    @GetMapping("/api/shop/branch/{id}")
    public ResponseEntity<?> getBranchByShop(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @PathVariable("id") Long id) {
        UserAuthChecker.checkAuth(userPrincipal);
        return shopBranchService.getShopBranchByShopAdmin(id, userPrincipal.getId());
    }

    /**
     * Customer gets a list of shop branches
     *
     * @param shopId - shop id
     * @return list of shop branches
     */
    @GetMapping("/api/user/branch/{shopId}")
    public ResponseEntity<?> getBranchesByCustomer(@PathVariable("shopId") Long shopId) {
        return shopBranchService.getShopBranchesByCustomer(shopId);
    }

    /**
     * Customer gets a shop branch
     *
     * @param shopId - shop id
     * @param id     - shop branch id
     * @return shop branch entity
     */
    @GetMapping("/api/user/branch/{shopId}/{id}")
    public ResponseEntity<?> getBranchByCustomer(@PathVariable("shopId") Long shopId,
                                                 @PathVariable("id") Long id) {
        return shopBranchService.getShopBranchByCustomer(shopId, id);
    }
}
