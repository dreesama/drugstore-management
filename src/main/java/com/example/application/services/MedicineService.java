package com.example.application.services;

import com.example.application.data.Medicine;
import com.example.application.data.MedicineRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class MedicineService {

    private final MedicineRepository repository;

    public MedicineService(MedicineRepository repository) {
        this.repository = repository;
    }

    public Optional<Medicine> get(Long id) {
        return repository.findById(id);
    }

    public Medicine update(Medicine entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Medicine> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Medicine> list(Pageable pageable, Specification<Medicine> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
