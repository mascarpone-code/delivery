package com.mascarpone.delivery.service.modifier;

import com.mascarpone.delivery.entity.modifier.Modifier;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface ModifierService extends GeneralService<Modifier> {
    /**
     * Searching modifiers by modifier's name, pageable
     *
     * @param modifier - modifier entity
     * @param page     - page number
     * @param size     - page size
     * @return list of modifiers
     */
    Page<Modifier> findAllByShopIdAndName(Modifier modifier, int page, int size);

    /**
     * Shop creates and updates the modifier.
     *
     * @param modifier    - modifier entity
     * @param shopAdminUuid - shop admin uuid
     * @return modifier entity
     */
    ResponseEntity<?> createOrUpdateModifier(Modifier modifier, UUID shopAdminUuid);

    /**
     * Shop gets a list of its modifiers. Searchable by name.
     *
     * @param page        - page number
     * @param name        - modifier name
     * @param shopAdminUuid - shop admin uuid
     * @return list of modifiers
     */
    ResponseEntity<?> getAllModifiersShop(Optional<Integer> page, Optional<String> name, UUID shopAdminUuid);

    /**
     * Shop gets the modifier.
     *
     * @param id          - modifier id
     * @param shopAdminUuid - shop admin uuid
     * @return modifier entity
     */
    ResponseEntity<?> getModifierShop(Long id, UUID shopAdminUuid);

    /**
     * Shop deletes the modifier.
     *
     * @param id          - modifier id
     * @param shopAdminUuid - shop admin uuid
     * @return
     */
    GeneralAnswer<String> deleteModifier(Long id, UUID shopAdminUuid);

    /**
     * Customer gets modifiers of the product.
     *
     * @param productId - product id
     * @return list of modifiers
     */
    ResponseEntity<?> getProductModifiersByCustomer(Long productId);

    /**
     * Customer gets a modifier
     *
     * @param id - modifier id
     * @return modifier dto
     */
    ResponseEntity<?> getModifierByCustomer(Long id);
}
