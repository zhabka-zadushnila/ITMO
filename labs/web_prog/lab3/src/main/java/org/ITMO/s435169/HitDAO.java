package org.ITMO.s435169;

import org.ITMO.s435169.beans.Hit;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HitDAO {

    public void saveResult(Hit hit) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(hit);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public List<Hit> getAllResults() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Hit> hits = session.createQuery("from Hit").list();
        session.close();
        return hits;
    }
}