package ir.maktab.hsps.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class BaseService<T, ID> {

    private JpaRepository<T, ID> jpaRepository;

    public void setJpaRepository(JpaRepository<T, ID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public T saveOrUpdate(T entity) {
        return jpaRepository.save(entity);
    }

    public T loadById(ID id) {
        return jpaRepository.getById(id);
    }

    public List<T> loadAll() {
        return jpaRepository.findAll();
    }

    public void deleteById(ID id) {
        jpaRepository.deleteById(id);
    }
}
