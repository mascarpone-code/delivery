package com.mascarpone.delivery.service.product;

import com.mascarpone.delivery.entity.product.Product;
import com.mascarpone.delivery.entity.productgroup.ProductGroup;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.payload.product.CreatedProductResponse;
import com.mascarpone.delivery.payload.product.ProductListResponse;
import com.mascarpone.delivery.payload.product.ProductResponse;
import com.mascarpone.delivery.repository.modifier.ModifierRepository;
import com.mascarpone.delivery.repository.product.ProductRepository;
import com.mascarpone.delivery.repository.product.specification.ProductSpecification;
import com.mascarpone.delivery.repository.productgroup.ProductGroupRepository;
import com.mascarpone.delivery.repository.shop.ShopRepository;
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

import static com.mascarpone.delivery.exception.ExceptionConstants.*;
import static com.mascarpone.delivery.utils.Constants.DEFAULT_PAGE;
import static com.mascarpone.delivery.utils.Constants.FETCH_RECORD_COUNT;
import static com.mascarpone.delivery.utils.shop.ShopUtils.checkUnit;
import static com.mascarpone.delivery.utils.shop.ShopUtils.getShop;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductGroupRepository productGroupRepository;
    private final ModifierRepository modifierRepository;
    private final ShopRepository shopRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public void save(Product object) {
        productRepository.save(object);
    }

    @Override
    public Page<Product> findAllByShopIdPageable(Long shopId, int page, int size) {
        return productRepository.findAllByShopId(shopId, PageRequest.of(page, size));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByIdAndShop(Long id, Shop shop) {
        return productRepository.findByIdAndShop(id, shop);
    }

    @Override
    public List<Product> findAllByProductGroup(ProductGroup productGroup) {
        return productRepository.findAllByProductGroupAndActiveIsTrue(productGroup);
    }

    @Override
    public List<Product> findAllByShop(Shop shop) {
        return productRepository.findAllByShop(shop);
    }

    @Override
    public Page<Product> findAllByShopIdAndNamePageable(Product product, int page, int size) {
        var specification = Specification.where(new ProductSpecification(product));

        return productRepository.findAll(specification, PageRequest.of(page, size, Sort.Direction.ASC, "name"));
    }

    /**
     * Creating and updating a product.
     *
     * @param product     - product entity
     * @param shopAdminId - shop admin id
     * @return product entity
     */
    @Override
    public ResponseEntity<?> createOrUpdateProduct(Product product, Long shopAdminId) {
        checkProductGroup(product);
        checkModifiers(product);
        checkUnit(product.getUnit().getId());

        if (product.getId() != null) {
            var shop = getShop(shopAdminId);
            var requestedProduct = productRepository.findByIdAndShop(product.getId(), shop)
                    .orElseThrow(() -> new BadRequestException(PRODUCT_NOT_FOUND));

            product.setCreator(requestedProduct.getCreator());
            product.setShop(requestedProduct.getShop());
            product.setPhotos(requestedProduct.getPhotos());
        } else {
            var creator = userRepository.getOne(shopAdminId);
            product.setDateCreate(new Date());
            product.setCreator(creator);
            product.setShop(creator.getShop());
            product.setActive(true);
        }

        productRepository.save(product);

        return ResponseEntity.ok(new CreatedProductResponse(product));
    }

    /**
     * Shop gets a list of its products. Searchable by name and product group id.
     *
     * @param page           - page number
     * @param productName    - product name
     * @param productGroupId - product group id
     * @param shopAdminId    - shop admin id
     * @return list of products
     */
    @Override
    public ResponseEntity<?> getAllProductsByShopAdmin(Optional<Integer> page, Optional<String> productName, Optional<Long> productGroupId, Long shopAdminId) {
        var product = new Product();
        var shop = getShop(shopAdminId);
        product.setShop(shop);
        productName.ifPresent(product::setName);
        productGroupId.ifPresent(groupId -> product.setProductGroup(productGroupRepository.getOne(groupId)));

        var products = findAllByShopIdAndNamePageable(
                product,
                page.orElse(DEFAULT_PAGE),
                FETCH_RECORD_COUNT);

        var productResponses = products
                .stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());

        long totalProductCount = products.getTotalElements();
        var response = new ProductListResponse(productResponses, totalProductCount);

        return ResponseEntity.ok(response);
    }

    /**
     * Shop gets a product.
     *
     * @param id          - product id
     * @param shopAdminId - shop admin id
     * @return product dto
     */
    @Override
    public ResponseEntity<?> getProductByShopAdmin(Long id, Long shopAdminId) {
        var shop = getShop(shopAdminId);
        var product = productRepository.findByIdAndShop(id, shop)
                .orElseThrow(() -> new BadRequestException(PRODUCT_NOT_FOUND));
        var response = new ProductResponse(product);

        return ResponseEntity.ok(response);
    }

    /**
     * Deleting a product.
     *
     * @param id          - product id
     * @param shopAdminId - shop admin id
     * @return
     */
    @Override
    public GeneralAnswer<String> deleteProduct(Long id, Long shopAdminId) {
        var shopId = getShop(shopAdminId);
        var product = productRepository.findByIdAndShop(id, shopId)
                .orElseThrow(() -> new BadRequestException(PRODUCT_NOT_FOUND));
        productRepository.delete(product);

        return new GeneralAnswer<>("OK", null, null);
    }

    /**
     * Customer gets a list of a shop's products.
     *
     * @param shopId - shop id
     * @return list of a products
     */
    @Override
    public ResponseEntity<?> getShopProductsByCustomer(Long shopId) {
        var shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new BadRequestException(SHOP_NOT_FOUND));
        var products = productRepository.findAllByShop(shop);
        var responses = products
                .stream()
                .map(CreatedProductResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    /**
     * Customer gets a list of a products of one product group.
     *
     * @param productGroupId - product group id
     * @return list of a products
     */
    @Override
    public ResponseEntity<?> getProductGroupProductsByCustomer(Long productGroupId) {
        var productGroup = productGroupRepository.findById(productGroupId)
                .orElseThrow(() -> new BadRequestException(GROUP_NOT_FOUND));
        var products = productRepository.findAllByProductGroup(productGroup);
        var responses = products
                .stream()
                .map(CreatedProductResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    /**
     * Customer gets a product
     *
     * @param id - product id
     * @return product dto
     */
    @Override
    public ResponseEntity<?> getProductByCustomer(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(PRODUCT_NOT_FOUND));
        var response = new CreatedProductResponse(product);

        return ResponseEntity.ok(response);
    }

    /**
     * Checking a product group availability.
     *
     * @param product - product entity
     */
    private void checkProductGroup(Product product) {
        if (productGroupRepository.findById(product.getProductGroup().getId()).isEmpty()) {
            throw new BadRequestException(GROUP_NOT_FOUND);
        }
    }

    /**
     * Checking a modifier availability.
     *
     * @param product - product entity
     */
    private void checkModifiers(Product product) {
        for (var modifier : product.getModifiers()) {
            if (modifierRepository.findById(modifier.getId()).isEmpty()) {
                throw new BadRequestException(MODIFIER_NOT_FOUND);
            }
        }
    }
}
