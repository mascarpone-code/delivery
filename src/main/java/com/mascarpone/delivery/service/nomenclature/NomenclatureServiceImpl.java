package com.mascarpone.delivery.service.nomenclature;

import com.mascarpone.delivery.entity.nomenclature.Nomenclature;
import com.mascarpone.delivery.entity.shopbranch.ShopBranch;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.repository.nomenclature.NomenclatureRepository;
import com.mascarpone.delivery.repository.nomenclature.specification.NomenclatureSpecification;
import com.mascarpone.delivery.service.mail.MailSendService;
import com.mascarpone.delivery.service.shopbranch.ShopBranchService;
import com.mascarpone.delivery.service.supplier.SupplierService;
import com.mascarpone.delivery.writeandread.WritePdfNomenclature;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mascarpone.delivery.exception.ExceptionConstants.NOMENCLATURE_FOR_ORDER_NOT_FOUND;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NomenclatureServiceImpl implements NomenclatureService {
    private final ShopBranchService shopBranchService;
    private final NomenclatureRepository nomenclatureRepository;
    private final SupplierService supplierService;
    private final MailSendService mailSendService;
    private final WritePdfNomenclature writePdfNomenclature;

    @Override
    public List<Nomenclature> getAll() {
        return nomenclatureRepository.findAll();
    }

    @Override
    public void save(Nomenclature object) {
        nomenclatureRepository.save(object);
    }

    @Override
    public Page<Nomenclature> findAllWithFilterPageable(Nomenclature filter, int page, int size) {
        var specification = Specification.where(new NomenclatureSpecification(filter));
        return nomenclatureRepository.findAll(specification, PageRequest.of(page, size, Sort.Direction.ASC, "name"));
    }

    @Override
    public Optional<Nomenclature> findById(Long id) {
        return nomenclatureRepository.findById(id);
    }

    @Override
    public Optional<Nomenclature> getByName(String s) {
        return nomenclatureRepository.findByName(s);
    }

    //автоматическая отправка заказа по email
    @Override
    public void getAndSendNomenclatureForOrder() {
        var nomenclatureForOrder = nomenclatureRepository.findNomenclatureForOrder();

        if (nomenclatureForOrder != null && !nomenclatureForOrder.isEmpty()) {
            List<List<Nomenclature>> nomenclatureForOrderByShopBranchId =
                    new ArrayList<>(nomenclatureForOrder.stream()
                            .collect(Collectors.groupingBy(Nomenclature::getShopBranch)).values());//листы заказов по каждой точке

            for (var list : nomenclatureForOrderByShopBranchId) {
                List<List<Nomenclature>> nomenclatureForOrderByShopBranchIdOrderBySupplier =
                        new ArrayList<>(list.stream().collect(Collectors.groupingBy(Nomenclature::getSupplier)).values());
                orderBySupplierAndWriteToPdfAndSend(nomenclatureForOrderByShopBranchIdOrderBySupplier);
            }
        } else {
            throw new BadRequestException(NOMENCLATURE_FOR_ORDER_NOT_FOUND);
        }
    }

    //ручная отправка заказа по текущей точке
    @Override
    public void getAndSendNomenclatureByShopBranchId(ShopBranch shopBranch) {
        var nomenclatureForOrderByShopBranchId = nomenclatureRepository.findNomenclatureForOrderForShopBranchId(shopBranch);

        if (nomenclatureForOrderByShopBranchId != null && !nomenclatureForOrderByShopBranchId.isEmpty()) {
            List<List<Nomenclature>> nomenclatureForOrderByShopBranchIdOrderBySupplier =
                    new ArrayList<>(nomenclatureForOrderByShopBranchId.stream().collect(Collectors.groupingBy(Nomenclature::getSupplier)).values());
            orderBySupplierAndWriteToPdfAndSend(nomenclatureForOrderByShopBranchIdOrderBySupplier);
        } else {
            throw new BadRequestException(NOMENCLATURE_FOR_ORDER_NOT_FOUND);
        }
    }

    private void orderBySupplierAndWriteToPdfAndSend(List<List<Nomenclature>> lists) {
        for (var nomenclatureList : lists) {//листы заказа для каждой точки по поставщику
            var shopBranch = shopBranchService.getOne(nomenclatureList.get(0).getShopBranch().getId());
            var pdf = writePdfNomenclature.nomenclatureReport(shopBranch, nomenclatureList); //запись в pdf

            var supplierEmail = supplierService.getOne(nomenclatureList.get(0).getSupplier().getId()).getEmail();

            try {
                mailSendService.sendNomenclatureForOrder(supplierEmail, pdf); //отправка письма
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
