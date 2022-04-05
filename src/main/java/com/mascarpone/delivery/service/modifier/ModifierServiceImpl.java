package com.mascarpone.delivery.service.modifier;

import com.mascarpone.delivery.entity.modifier.Modifier;
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
import java.util.UUID;
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
    public Page<Modifier> findAllByShopIdAndName(Modifier modifier, int page, int size) {
        var specification = Specification.where(new ModifierSpecification(modifier));
        return modifierRepository.findAll(specification, PageRequest.of(page, size, Sort.Direction.DESC, "dateCreate"));
    }

    @Override
    public ResponseEntity<?> createOrUpdateModifier(Modifier modifier, UUID shopAdminUuid) {
        checkUnit(modifier.getUnit().getId());

        if (modifier.getId() != null) {
            var currentShop = getShop(shopAdminUuid);
            var requestedModifier = modifierRepository.findByIdAndShop(modifier.getId(), currentShop)
                    .orElseThrow(() -> new BadRequestException(MODIFIER_NOT_FOUND));

            modifier.setCreator(requestedModifier.getCreator());
            modifier.setDateCreate(requestedModifier.getDateCreate());
            modifier.setShop(requestedModifier.getShop());
        } else {
            var creator = userRepository.getOne(shopAdminUuid);
            modifier.setCreator(creator);
            modifier.setDateCreate(new Date());
            modifier.setShop(creator.getShop());
        }

        modifierRepository.save(modifier);

        return ResponseEntity.ok(new ModifierResponse(modifier));
    }

    @Override
    public ResponseEntity<?> getAllModifiersShop(Optional<Integer> page, Optional<String> name, UUID shopAdminUuid) {
        var modifierFilter = new Modifier();
        name.ifPresent(modifierFilter::setName);
        var currentShop = getShop(shopAdminUuid);
        modifierFilter.setShop(currentShop);

        var modifiers = findAllByShopIdAndName(
                modifierFilter,
                page.orElse(DEFAULT_PAGE),
                FETCH_RECORD_COUNT);

        var response = new ModifierListResponse(modifiers.stream()
                .map(ModifierResponse::new)
                .collect(Collectors.toList()),
                modifiers.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getModifierShop(Long id, UUID shopAdminUuid) {
        var currentShop = getShop(shopAdminUuid);
        var currentModifier = modifierRepository.findByIdAndShop(id, currentShop)
                .orElseThrow(() -> new BadRequestException(MODIFIER_NOT_FOUND));
        var response = new ModifierResponse(currentModifier);

        return ResponseEntity.ok(response);
    }

    @Override
    public GeneralAnswer<String> deleteModifier(Long id, UUID shopAdminUuid) {
        var currentShop = getShop(shopAdminUuid);
        var currentModifier = modifierRepository.findByIdAndShop(id, currentShop)
                .orElseThrow(() -> new BadRequestException(MODIFIER_NOT_FOUND));
        modifierRepository.delete(currentModifier);

        return new GeneralAnswer<>("OK", null, null);
    }

    @Override
    public ResponseEntity<?> getProductModifiersByCustomer(Long productId) {
        var currentProduct = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException(PRODUCT_NOT_FOUND));
        var modifierList = currentProduct.getModifiers();
        var response = modifierList
                .stream()
                .map(ModifierClientResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getModifierByCustomer(Long id) {
        var modifier = modifierRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(MODIFIER_NOT_FOUND));
        var response = new ModifierClientResponse(modifier);

        return ResponseEntity.ok(response);
    }
}
