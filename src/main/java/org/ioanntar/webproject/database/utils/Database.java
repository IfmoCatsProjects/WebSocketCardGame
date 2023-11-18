package org.ioanntar.webproject.database.utils;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    public void clear(Class<?> entityClass) {
        Query query = session.createQuery(String.format("delete from %s", entityClass.getName()));
        query.executeUpdate();
    }

    public void commit() {
        transaction.commit();
        session.close();
    }
    public <T> void delete(T entity) {
        session.remove(entity);
    }
}