package org.ioanntar.webproject.database.utils;

import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.JpaCriteriaQuery;

import java.util.List;

public class Database {

    private final Session session;
    private final Transaction transaction;

    public Database() {
        session = HibernateUtils.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }

    public <T> T get(Class<?> entityClass, long id) {
        return (T) session.get(entityClass, id);
    }

    public <T> T merge(T entity) {
        return session.merge(entity);
    }

    public <T> List<T> getAll(Class<T> entityClass) {
        var criteriaQuery = session.getCriteriaBuilder().createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        return session.createQuery(criteriaQuery.select(root)).getResultList();
    }

    public void commit() {
        transaction.commit();
        session.close();
    }
    public <T> void delete(T entity) {
        session.remove(entity);
    }
}