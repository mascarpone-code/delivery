package com.mascarpone.delivery.controller.modifier;

import com.mascarpone.delivery.entity.modifier.Modifier;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.security.UserPrincipal;
import com.mascarpone.delivery.service.modifier.ModifierService;
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
public class ModifierController {
    private final ModifierService modifierService;

    /**
     * Creating and updating a modifier.
     *
     * @param userPrincipal - authenticated shop admin
     * @param modifier      - modifier entity
     * @return modifier entity
     */
    @PostMapping("/api/shop/modifier")
    public ResponseEntity<?> createOrUpdateModifier(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                    @RequestBody Modifier modifier) {
        UserAuthChecker.checkAuth(userPrincipal);
        return modifierService.createOrUpdateModifier(modifier, userPrincipal.getId());
    }

    /**
     * Shop gets a list of its modifiers. Searchable by name.
     *
     * @param userPrincipal - authenticated shop admin
     * @param page          - page number
     * @param name          - modifier name
     * @return list of modifiers
     */
    @GetMapping("/api/shop/modifier")
    public ResponseEntity<?> getAllModifiersShop(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                 @RequestParam("page") Optional<Integer> page,
                                                 @RequestParam("name") Optional<String> name) {
        UserAuthChecker.checkAuth(userPrincipal);
        return modifierService.getAllModifiersShop(page, name, userPrincipal.getId());
    }

    /**
     * Shop gets a modifier.
     *
     * @param userPrincipal - authenticated shop admin
     * @param id            - modifier id
     * @return modifier entity
     */
    @GetMapping("/api/shop/modifier/{id}")
    public ResponseEntity<?> getModifierShop(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @PathVariable("id") Long id) {
        UserAuthChecker.checkAuth(userPrincipal);
        return modifierService.getModifierShop(id, userPrincipal.getId());
    }

    /**
     * Deleting a modifier.
     *
     * @param userPrincipal - authenticated shop admin
     * @param id            - modifier id
     * @return
     */
    @DeleteMapping("/api/shop/modifier/{id}")
    public GeneralAnswer deleteModifier(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                        @PathVariable("id") Long id) {
        UserAuthChecker.checkAuth(userPrincipal);
        return modifierService.deleteModifier(id, userPrincipal.getId());
    }

    /**
     * Customer gets modifiers of one product.
     *
     * @param productId - product id
     * @return list of modifiers
     */
    @GetMapping("/api/user/modifier")
    public ResponseEntity<?> getProductModifiersByCustomer(@RequestParam("productId") Long productId) {
        return modifierService.getProductModifiersByCustomer(productId);
    }

    /**
     * Customer gets a modifier
     *
     * @param id - modifier id
     * @return modifier dto
     */
    @GetMapping("/api/user/modifier/{id}")
    public ResponseEntity<?> getModifierByCustomer(@PathVariable("id") Long id) {
        return modifierService.getModifierByCustomer(id);
    }
}
