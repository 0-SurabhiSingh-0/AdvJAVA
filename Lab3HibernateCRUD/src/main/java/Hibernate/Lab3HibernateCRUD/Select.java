package Hibernate.Lab3HibernateCRUD;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Select {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            List<ProductEntity> products = session.createQuery("FROM ProductEntity", ProductEntity.class).getResultList();

            System.out.printf("%-5s | %-20s | %-10s | %-8s%n", "ID", "Name", "Price", "Quantity");
            System.out.println("--------------------------------------------------");
            for (ProductEntity product : products) {
                System.out.printf("%-5s | %-20s | %-10.2f | %-8d%n", product.getId(), product.getName(),
                        product.getPrice(), product.getQuantity());
            }

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
}

