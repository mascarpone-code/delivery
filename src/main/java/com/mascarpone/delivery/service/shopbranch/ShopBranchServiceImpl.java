package com.mascarpone.delivery.service.shopbranch;

import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.entity.shopbranch.ShopBranch;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.payload.shopbranch.ShopBranchListResponse;
import com.mascarpone.delivery.payload.shopbranch.ShopBranchResponse;
import com.mascarpone.delivery.repository.shopbranch.ShopBranchRepository;
import com.mascarpone.delivery.repository.shopbranch.specification.ShopBranchSpecification;
import com.mascarpone.delivery.repository.user.UserRepository;
import com.mascarpone.delivery.service.shop.ShopService;
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

import static com.mascarpone.delivery.exception.ExceptionConstants.BRANCH_NOT_FOUND;
import static com.mascarpone.delivery.utils.Constants.DEFAULT_PAGE;
import static com.mascarpone.delivery.utils.Constants.FETCH_RECORD_COUNT;
import static com.mascarpone.delivery.utils.shop.ShopUtils.getShop;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShopBranchServiceImpl implements ShopBranchService {
    private final ShopBranchRepository shopBranchRepository;

    @Override
    public List<ShopBranch> getAll() {
        return shopBranchRepository.findAll();
    }

    @Override
    public void save(ShopBranch object) {
        shopBranchRepository.save(object);
    }

    @Override
    public Page<ShopBranch> getAllBranches(ShopBranch shopBranch, int page, int size) {
        Specification<ShopBranch> specification = Specification.where(new ShopBranchSpecification(shopBranch));
        return shopBranchRepository.findAll(specification, PageRequest.of(page, size, Sort.Direction.ASC, "id"));
    }

    @Override
    public List<ShopBranch> findAllByShopIdAndActiveTrueOrderByAddressAsc(Long shopId) {
        return shopBranchRepository.findAllByShopIdAndActiveTrueOrderByAddressAsc(shopId);
    }

    @Override
    public Optional<ShopBranch> findByIdAndShopId(Long id, Long shopId) {
        return shopBranchRepository.findByIdAndShopId(id, shopId);
    }

    @Override
    public ShopBranch getOne(Long id) {
        return shopBranchRepository.getOne(id);
    }

    /**
     * Creating and updating a shop branch
     *
     * @param branch      - shop branch entity
     * @param shopAdminId - shop admin id
     * @return shop branch entity
     */
    @Override
    public ResponseEntity<?> createOrUpdateShopBranch(ShopBranch branch, Long shopAdminId) {
        Shop shop = getShop(shopAdminId);
        branch.setShop(shop);
        shopBranchRepository.save(branch);

        return ResponseEntity.ok(branch);
    }

    /**
     * Shop gets a list of its branches. Searchable by name.
     *
     * @param page        - page number
     * @param name        - shop branch name
     * @param shopAdminId - shop admin id
     * @return list of shop branches
     */
    @Override
    public ResponseEntity<?> getShopBranchesByShopAdmin(Optional<Integer> page, Optional<String> name, Long shopAdminId) {
        ShopBranch shopBranch = new ShopBranch();
        Shop shop = getShop(shopAdminId);
        shopBranch.setShop(shop);
        name.ifPresent(shopBranch::setName);

        Page<ShopBranch> branches = getAllBranches(
                shopBranch,
                page.orElse(DEFAULT_PAGE),
                FETCH_RECORD_COUNT);

        List<ShopBranchResponse> shopBranchResponses = branches
                .stream()
                .map(ShopBranchResponse::new)
                .collect(Collectors.toList());

        long totalBranchesCount = branches.getTotalElements();
        ShopBranchListResponse response = new ShopBranchListResponse(shopBranchResponses, totalBranchesCount);

        return ResponseEntity.ok(response);
    }

    /**
     * Shop gets its branch.
     *
     * @param id          - shop branch id
     * @param shopAdminId - shop admin id
     * @return shop branch entity
     */
    @Override
    public ResponseEntity<?> getShopBranchByShopAdmin(Long id, Long shopAdminId) {
        Shop shop = getShop(shopAdminId);
        ShopBranch shopBranch = shopBranchRepository.findByIdAndShop(id, shop)
                .orElseThrow(() -> new BadRequestException(BRANCH_NOT_FOUND));

        return ResponseEntity.ok(shopBranch);
    }

    /**
     * Customer gets a list of shop branches
     *
     * @param shopId - shop id
     * @return list of shop branches
     */
    @Override
    public ResponseEntity<?> getShopBranchesByCustomer(Long shopId) {
        ShopUtils.checkShop(shopId);
        List<ShopBranch> shopBranches = shopBranchRepository.findAllByShopIdAndActiveTrueOrderByAddressAsc(shopId);

        return ResponseEntity.ok(shopBranches);

    }

    /**
     * Customer gets a shop branch
     *
     * @param shopId - shop id
     * @param id     - shop branch id
     * @return shop branch entity
     */
    @Override
    public ResponseEntity<?> getShopBranchByCustomer(Long shopId, Long id) {
        ShopUtils.checkShop(shopId);
        ShopBranch shopBranch = shopBranchRepository.findByIdAndShopId(id, shopId)
                .orElseThrow(() -> new BadRequestException(BRANCH_NOT_FOUND));

        return ResponseEntity.ok(shopBranch);
    }

    @Override
    public Optional<ShopBranch> findById(Long id) {
        return shopBranchRepository.findById(id);
    }
}
