package com.gra.worstmovies.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProvider {
  private static EntityManagerFactory entityManagerFactory;
  private static EntityManager entityManager;

  public static EntityManager getEntityManagerFactory(){
    if(entityManager == null){
      entityManagerFactory = Persistence.createEntityManagerFactory("Default");
      entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
    }
    return entityManager;
  }

}
