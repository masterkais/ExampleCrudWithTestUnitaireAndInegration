package fr.byteCode.erp.service.services.ImplService;


import fr.byteCode.erp.persistance.dao.BaseRepository;
import fr.byteCode.erp.service.services.InterfaceService.IGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Slf4j
public class GenericService<T, D extends Serializable> implements IGenericService<T, D> {

    private static final int INDEX_BEGIN = 31;
    public static final int SQL_SERVER_IN_CLAUSE_PARTITION_SIZE = 2000;
    private static final String IS_DELETED_FIELD = "isDeleted";

    @Autowired
    private BaseRepository<T, D> baseRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public GenericService() {
        super();
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return baseRepository.findAll(sort);
    }

    @Override
    public List<T> findAll(Iterable<D> ids) {
        return baseRepository.findAllById(ids);
    }

    @Override
    public <S extends T> List<S> save(Iterable<S> entities) {
        return baseRepository.saveAll(entities);
    }

    @Override
    public void flush() {
        baseRepository.flush();

    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return baseRepository.saveAndFlush(entity);
    }

    @Override
    public void deleteInBatchSoft(Iterable<T> entities) {
    }

    @Override
    public T findOne(D id) {
        return baseRepository.findOne(id);
    }

    @Override
    public void delete(D id) {
        UUID uuid = UUID.randomUUID();
        baseRepository.delete(id, uuid);
    }

    @Override
    public void delete(T entity) {
        UUID uuid = UUID.randomUUID();
        baseRepository.delete(entity, uuid);
    }

    @Override
    public Page<T> findAllByPaginationAndIsDeletedFalse(Pageable pageable) {
        return baseRepository.findAllByIsDeletedFalse(pageable);
    }

    @Override
    public boolean isDynamicSoftDelete(D id, String className, String fieldToShow, String messageToShow) {
        return true;
    }


    @SuppressWarnings({"squid:S2384", "squid:S2658"})
    @Override
    public boolean isRelated(D id, String className) {
        return true;
    }

    public List<T> findFields(Class<T> entity, String fieldName, Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entity);

        Root<T> object = criteriaQuery.from(entity);
        Predicate authorNamePredicate = criteriaBuilder.equal(object.get(fieldName), id);
        Predicate isDeletedPredicat = criteriaBuilder.equal(object.get(IS_DELETED_FIELD), false);
        criteriaQuery.where(authorNamePredicate, isDeletedPredicat);

        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public void deleteList(Iterable<T> entities) {
        baseRepository.deleteAll(entities);
    }

}
