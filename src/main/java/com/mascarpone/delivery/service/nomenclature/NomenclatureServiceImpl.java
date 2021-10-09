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
    public Optional<Nomenclature> findById(Long id) {
        return nomenclatureRepository.findById(id);
    }

    @Override
    public Optional<Nomenclature> getByName(String s) {
        return nomenclatureRepository.findByName(s);
    }

    @Override
    public void getAndSendNomenclatureForOrder() {
        var nomenclatureList = nomenclatureRepository.findNomenclatureForOrder();

        if (nomenclatureList == null || nomenclatureList.isEmpty()) {
            throw new BadRequestException(NOMENCLATURE_FOR_ORDER_NOT_FOUND);
        }

        List<List<Nomenclature>> nomenclatureForOrderByShopBranchId =
                new ArrayList<>(nomenclatureList.stream()
                        .collect(Collectors.groupingBy(Nomenclature::getShopBranch)).values());

        for (var list : nomenclatureForOrderByShopBranchId) {
            List<List<Nomenclature>> nomenclatureForOrderByShopBranchIdOrderBySupplier =
                    new ArrayList<>(list.stream().collect(Collectors.groupingBy(Nomenclature::getSupplier)).values());
            orderBySupplierAndWriteToPdfAndSend(nomenclatureForOrderByShopBranchIdOrderBySupplier);
        }
    }

    /**
     * Sending the order to the supplier by e-mail
     *
     * @param lists lists of lists of nomenclatures
     */
    private void orderBySupplierAndWriteToPdfAndSend(List<List<Nomenclature>> lists) {
        for (var nomenclatureList : lists) {
            var shopBranch = shopBranchService.getOne(nomenclatureList.get(0).getShopBranch().getId());
            var pdfBytes = writePdfNomenclature.nomenclatureReport(shopBranch, nomenclatureList);
            var supplierEmail = supplierService.getOne(nomenclatureList.get(0).getSupplier().getId()).getEmail();

            try {
                mailSendService.sendNomenclatureForOrder(supplierEmail, pdfBytes);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
