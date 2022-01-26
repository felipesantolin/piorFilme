package com.gra.worstmovies.util;

import com.google.common.base.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class GenericRepository<E extends AbstractEntity> {

  private EntityManager em = EntityManagerFactoryProvider.getEntityManagerFactory();
  private Class<E> entityClass;

  public GenericRepository() {
    Object type = getClass();
    type = ((Class) type).getGenericSuperclass();
    ParameterizedType parameterizedType = (ParameterizedType) type;
    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
    entityClass = (Class<E>) actualTypeArguments[0];
  }

  public List<E> getAll() {
    StringBuilder sb = new StringBuilder();
    sb.append("select e from ").append(entityClass.getSimpleName()).append(" e ");
    TypedQuery<E> typedQuery = em.createQuery(sb.toString(), entityClass);
    return typedQuery.getResultList();
  }

  public void persist(E entity) {
    System.out.println(entity.toString());
    em.persist(entity);
  }

  public E merge(E entity) {
    findAndValidate(entity.getId());
    E merged = (E) em.merge(entity);
    return merged;
  }

  public TypedQuery<E> createTypedQuery(String sql) {
    return em.createQuery(sql, entityClass);
  }

  public Optional<E> findAndValidate(Long id) {
    E entity = find(id);
    if(entity == null){
      throw new RuntimeException("Entity not found");
    }
    return Optional.of(entity);
  }

  public E find(Long id) {
    E entity = em.find(entityClass, id);
    return entity;
  }

  public Query createNativeQuery(String sql){
    return em.createNativeQuery(sql);
  }
}
