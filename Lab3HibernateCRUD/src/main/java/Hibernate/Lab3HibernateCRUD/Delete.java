package Hibernate.Lab3HibernateCRUD;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Delete {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            int deletedCount = session.createQuery("DELETE FROM ProductEntity WHERE price < 10")
                    .executeUpdate();

            System.out.println("Number of records deleted: " + deletedCount);
            displayProduct(session.createQuery("FROM ProductEnity", ProductEntity.class).getResultList());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    private static void displayProduct(List<ProductEntity> product) {
        System.out.printf("%-5s | %-20s | %-10s | %-8s%n", "ID", "Name", "Price", "Quantity");
        System.out.println("--------------------------------------------------");
        for (ProductEntity productentity : product) {
            System.out.printf("%-5s | %-20s | %-10.2f | %-8d%n", productentity.getId(), productentity.getName(),
                    productentity.getPrice(), productentity.getQuantity());
        }
    }
}

