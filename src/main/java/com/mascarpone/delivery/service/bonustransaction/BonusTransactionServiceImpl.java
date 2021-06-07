package com.mascarpone.delivery.service.bonustransaction;

import com.mascarpone.delivery.entity.bonustransaction.BonusTransaction;
import com.mascarpone.delivery.entity.bonustransaction.BonusTransactionFilter;
import com.mascarpone.delivery.repository.bonustransaction.BonusTransactionRepository;
import com.mascarpone.delivery.repository.bonustransaction.specification.BonusTransactionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BonusTransactionServiceImpl implements BonusTransactionService {
    private final BonusTransactionRepository repository;

    @Override
    public List<BonusTransaction> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<BonusTransaction> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(BonusTransaction object) {
        repository.save(object);
    }

    @Override
    public Page<BonusTransaction> search(BonusTransactionFilter filter, int page, int size) {
        Specification<BonusTransaction> specification = Specification.where(new BonusTransactionSpecification(filter));

        return repository.findAll(specification, PageRequest.of(page, size, Sort.Direction.DESC, "dateCreate"));
    }
}
