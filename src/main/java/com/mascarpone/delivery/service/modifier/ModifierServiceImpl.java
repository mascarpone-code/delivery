package com.mascarpone.delivery.service.modifier;

import com.mascarpone.delivery.entity.modifier.Modifier;
import com.mascarpone.delivery.entity.product.Product;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.payload.modifier.ModifierClientResponse;
import com.mascarpone.delivery.payload.modifier.ModifierListResponse;
import com.mascarpone.delivery.payload.modifier.ModifierResponse;
import com.mascarpone.delivery.repository.modifier.ModifierRepository;
import com.mascarpone.delivery.repository.modifier.specification.ModifierSpecification;
import com.mascarpone.delivery.repository.product.ProductRepository;
import com.mascarpone.delivery.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mascarpone.delivery.exception.ExceptionConstants.MODIFIER_NOT_FOUND;
import static com.mascarpone.delivery.exception.ExceptionConstants.PRODUCT_NOT_FOUND;
import static com.mascarpone.delivery.utils.Constants.DEFAULT_PAGE;
import static com.mascarpone.delivery.utils.Constants.FETCH_RECORD_COUNT;
import static com.mascarpone.delivery.utils.shop.ShopUtils.checkUnit;
import static com.mascarpone.delivery.utils.shop.ShopUtils.getShop;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ModifierServiceImpl implements ModifierService {
    private final ModifierRepository modifierRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Modifier> getAll() {
        return modifierRepository.findAll();
    }

    @Override
    public void save(Modifier object) {
        modifierRepository.save(object);
    }

    @Override
    public Page<Modifier> findAllByShopId(Long shopId, int page, int size) {
        return modifierRepository.findAllByShopIdOrderByDateCreateDesc(shopId, PageRequest.of(page, size));
    }

    @Override
    public Page<Modifier> findAllByShopIdAndName(Modifier modifier, int page, int size) {
        var specification = Specification.where(new ModifierSpecification(modifier));
        return modifierRepository.findAll(specification, PageRequest.of(page, size, Sort.Direction.DESC, "dateCreate"));
    }

    @Override
    public Optional<Modifier> findById(Long id) {
        return modifierRepository.findById(id);
    }

    @Override
    public Optional<Modifier> findByIdAndProduct(Long id, Product product) {
        return modifierRepository.findByIdAndProducts(id, product);
    }

    /**
     * Creating and updating a modifier.
     *
     * @param modifier    - modifier entity
     * @param shopAdminId - shop admin id
     * @return modifier entity
     */
    @Override
    public ResponseEntity<?> createOrUpdateModifier(Modifier modifier, Long shopAdminId) {
        checkUnit(modifier.getUnit().getId());

        if (modifier.getId() != null) {
            var shop = getShop(shopAdminId);
            var requestedModifier = modifierRepository.findByIdAndShop(modifier.getId(), shop)
                    .orElseThrow(() -> new BadRequestException(MODIFIER_NOT_FOUND));

            modifier.setCreator(requestedModifier.getCreator());
            modifier.setDateCreate(requestedModifier.getDateCreate());
            modifier.setShop(requestedModifier.getShop());
        } else {
            var creator = userRepository.getOne(shopAdminId);
            modifier.setCreator(creator);
            modifier.setDateCreate(new Date());
            modifier.setShop(creator.getShop());
        }

        modifierRepository.save(modifier);

        return ResponseEntity.ok(new ModifierResponse(modifier));
    }

    /**
     * Shop gets a list of its modifiers. Searchable by name.
     *
     * @param page        - page number
     * @param name        - modifier name
     * @param shopAdminId - shop admin id
     * @return list of modifiers
     */
    @Override
    public ResponseEntity<?> getAllModifiersShop(Optional<Integer> page, Optional<String> name, Long shopAdminId) {
        var modifier = new Modifier();
        name.ifPresent(modifier::setName);
        var shop = getShop(shopAdminId);
        modifier.setShop(shop);

        var modifiers = findAllByShopIdAndName(
                modifier,
                page.orElse(DEFAULT_PAGE),
                FETCH_RECORD_COUNT);

        return ResponseEntity.ok(
                new ModifierListResponse(modifiers
                        .stream()
                        .map(ModifierResponse::new)
                        .collect(Collectors.toList()), modifiers.getTotalElements()));
    }

    /**
     * Shop gets a modifier.
     *
     * @param id          - modifier id
     * @param shopAdminId - shop admin id
     * @return modifier entity
     */
    @Override
    public ResponseEntity<?> getModifierShop(Long id, Long shopAdminId) {
        var shop = getShop(shopAdminId);
        var modifier = modifierRepository.findByIdAndShop(id, shop)
                .orElseThrow(() -> new BadRequestException(MODIFIER_NOT_FOUND));

        return ResponseEntity.ok(new ModifierResponse(modifier));
    }

    /**
     * Deleting a modifier.
     *
     * @param id          - modifier id
     * @param shopAdminId - shop admin id
     * @return
     */
    @Override
    public GeneralAnswer<String> deleteModifier(Long id, Long shopAdminId) {
        var shop = getShop(shopAdminId);
        var modifier = modifierRepository.findByIdAndShop(id, shop)
                .orElseThrow(() -> new BadRequestException(MODIFIER_NOT_FOUND));
        modifierRepository.delete(modifier);

        return new GeneralAnswer<>("OK", null, null);
    }

    /**
     * Customer gets modifiers of one product.
     *
     * @param productId - product id
     * @return list of modifiers
     */
    @Override
    public ResponseEntity<?> getProductModifiersByCustomer(Long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException(PRODUCT_NOT_FOUND));
        var modifiers = product.getModifiers();
        var response = modifiers
                .stream()
                .map(ModifierClientResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * Customer gets a modifier
     *
     * @param id - modifier id
     * @return modifier dto
     */
    @Override
    public ResponseEntity<?> getModifierByCustomer(Long id) {
        var modifier = modifierRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(MODIFIER_NOT_FOUND));
        var response = new ModifierClientResponse(modifier);

        return ResponseEntity.ok(response);
    }
}
