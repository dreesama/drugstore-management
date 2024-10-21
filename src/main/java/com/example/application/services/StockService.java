package com.example.application.services;

import com.example.application.data.Stock;
import com.example.application.data.StockRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository repository;

    public StockService(StockRepository repository) {
        this.repository = repository;
    }

    public Optional<Stock> get(Long id) {
        return repository.findById(id);
    }

    public Stock update(Stock entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Stock> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Stock> list(Pageable pageable, Specification<Stock> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
