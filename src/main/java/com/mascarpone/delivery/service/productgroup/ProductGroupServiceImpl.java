package com.mascarpone.delivery.service.productgroup;

import com.mascarpone.delivery.entity.productgroup.ProductGroup;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.payload.productgroup.ProductGroupCountResponse;
import com.mascarpone.delivery.payload.productgroup.ProductGroupForUserResponse;
import com.mascarpone.delivery.repository.product.ProductRepository;
import com.mascarpone.delivery.repository.productgroup.ProductGroupRepository;
import com.mascarpone.delivery.repository.productgroup.specification.ProductGroupSpecification;
import com.mascarpone.delivery.repository.user.UserRepository;
import com.mascarpone.delivery.utils.shop.ShopUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mascarpone.delivery.exception.ExceptionConstants.*;
import static com.mascarpone.delivery.utils.Constants.DEFAULT_PAGE;
import static com.mascarpone.delivery.utils.Constants.FETCH_RECORD_COUNT;
import static com.mascarpone.delivery.utils.shop.ShopUtils.getShop;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductGroupServiceImpl implements ProductGroupService {
    private final ProductGroupRepository productGroupRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ProductGroup> getAll() {
        return productGroupRepository.findAll();
    }

    @Override
    public void save(ProductGroup object) {
        productGroupRepository.save(object);
    }

    @Override
    public Page<ProductGroup> findAllByShopIdPageable(Long shopId, int page, int size) {
        return productGroupRepository.findAllByShopId(shopId, PageRequest.of(page, size, Sort.Direction.ASC, "ordinalNumber"));
    }

    @Override
    public Page<ProductGroup> findAllByShopIdAndName(ProductGroup group, int page, int size) {
        Specification<ProductGroup> specification = Specification.where(new ProductGroupSpecification(group));

        return productGroupRepository.findAll(specification, PageRequest.of(page, size, Sort.Direction.ASC, "ordinalNumber"));
    }

    @Override
    public Optional<ProductGroup> findById(Long id) {
        return productGroupRepository.findById(id);
    }

    @Override
    public List<ProductGroup> findAllByShopIdAndActiveTrueOrderByOrdinalNumber(Long shopId) {
        return productGroupRepository.findAllByShopIdAndActiveTrueOrderByOrdinalNumber(shopId);
    }

    /**
     * Creating and updating a product group.
     *
     * @param productGroup - product group entity
     * @param shopAdminId  - shop admin id
     * @return product group entity
     */
    @Override
    public ResponseEntity<?> createOrUpdateProductGroup(ProductGroup productGroup, Long shopAdminId) {
        if (productGroup.getId() != null) {
            Shop shop = getShop(shopAdminId);
            ProductGroup requestedProductGroup = productGroupRepository.findByIdAndShop(productGroup.getId(), shop)
                    .orElseThrow(() -> new BadRequestException(GROUP_NOT_FOUND));

            productGroup.setCreator(requestedProductGroup.getCreator());
            productGroup.setShop(requestedProductGroup.getShop());
        } else {
            User creator = userRepository.getOne(shopAdminId);
            productGroup.setCreator(creator);
            productGroup.setShop(creator.getShop());
        }

        productGroupRepository.save(productGroup);

        return ResponseEntity.ok(productGroup);
    }

    /**
     * Shop gets a list of its product groups. Searchable by name.
     *
     * @param page        - page number
     * @param name        - product group name
     * @param shopAdminId - shop admin id
     * @return list of product groups
     */
    @Override
    public ResponseEntity<?> getAllProductGroupsByShopAdmin(Optional<Integer> page, Optional<String> name, Long shopAdminId) {
        ProductGroup productGroup = new ProductGroup();
        name.ifPresent(productGroup::setName);
        Shop shop = getShop(shopAdminId);
        productGroup.setShop(shop);

        Page<ProductGroup> productGroups = findAllByShopIdAndName(
                productGroup,
                page.orElse(DEFAULT_PAGE),
                FETCH_RECORD_COUNT);

        List<ProductGroup> productGroupList = productGroups.getContent();
        long totalProductGroupCount = productGroups.getTotalElements();
        ProductGroupCountResponse response = new ProductGroupCountResponse(productGroupList, totalProductGroupCount);

        return ResponseEntity.ok(response);
    }

    /**
     * Shop gets its product group.
     *
     * @param id - product group id
     * @return product group entity
     */
    @Override
    public ResponseEntity<?> getProductGroupByShopAdmin(Long id, Long shopAdminId) {
        Shop shop = getShop(shopAdminId);
        ProductGroup productGroup = productGroupRepository.findByIdAndShop(id, shop)
                .orElseThrow(() -> new BadRequestException(GROUP_NOT_FOUND));

        return ResponseEntity.ok(productGroup);
    }

    /**
     * Deleting an empty product group.
     *
     * @param id - product group id
     * @return
     */
    @Override
    public GeneralAnswer deleteProductGroup(Long id, Long shopAdminId) {
        Shop shop = getShop(shopAdminId);
        ProductGroup productGroup = productGroupRepository.findByIdAndShop(id, shop)
                .orElseThrow(() -> new BadRequestException(GROUP_NOT_FOUND));

        if (!productRepository.findAllByProductGroup(productGroup).isEmpty()) {
            throw new BadRequestException(PRODUCT_GROUP_NOT_EMPTY);
        }

        productGroupRepository.delete(productGroup);

        return new GeneralAnswer<String>("OK", null, null);
    }

    /**
     * Customer gets a list of product groups.
     *
     * @param shopId - shop id
     * @return list of product groups
     */
    @Override
    public ResponseEntity<?> getProductGroupsByCustomer(Long shopId) {
        ShopUtils.checkShop(shopId);
        List<ProductGroup> productGroups = productGroupRepository.findAllByShopIdAndActiveTrueOrderByOrdinalNumber(shopId);
        List<ProductGroupForUserResponse> response = productGroups
                .stream()
                .map(ProductGroupForUserResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * Checking an ordinal number
     *
     * @param shopAdminId  - shop admin id
     * @param productGroup - product group entity
     */
    private void checkOrdinalNumber(Long shopAdminId, ProductGroup productGroup) {
        if (productGroupRepository.existsByShopIdAndOrdinalNumber(
                userRepository.getOne(shopAdminId).getShop().getId(), productGroup.getOrdinalNumber())) {
            throw new BadRequestException(WRONG_ORDINAL_NUMBER);
        }
    }
}
