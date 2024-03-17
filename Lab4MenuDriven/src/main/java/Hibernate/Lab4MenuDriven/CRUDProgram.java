package Hibernate.Lab4MenuDriven;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class CRUDProgram {
    private static SessionFactory sessionFactory;
    private static Session session;

    public static void main(String[] args) {
        try {
            // Initialize Hibernate
            sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();

            // Main menu
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
                System.out.println("\n=== CRUD Operations Menu ===");
                System.out.println("1. Add Product");
                System.out.println("2. View All Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addProduct(scanner);
                        break;
                    case 2:
                        viewAllProducts();
                        break;
                    case 3:
                        updateProduct(scanner);
                        break;
                    case 4:
                        deleteProduct(scanner);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice! Please enter a number between 1 and 5.");
                }
            }
        } finally {
            // Close Hibernate session factory
            if (session != null) {
                session.close();
            }
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }

    private static void addProduct(Scanner scanner) {
        Transaction transaction = session.beginTransaction();
        try {
            System.out.println("Enter product name:");
            String name = scanner.next();

            System.out.println("Enter product price:");
            double price = scanner.nextDouble();

            System.out.println("Enter product quantity:");
            int quantity = scanner.nextInt();

            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);

            session.save(product);
            transaction.commit();
            System.out.println("Product added successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void viewAllProducts() {
        List<Product> products = session.createQuery("FROM Product", Product.class).getResultList();
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            System.out.println("\nAll Products:");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    private static void updateProduct(Scanner scanner) {
        Transaction transaction = session.beginTransaction();
        try {
            System.out.println("Enter product ID to update:");
            long id = scanner.nextLong();

            Product product = session.get(Product.class, id);
            if (product == null) {
                System.out.println("Product not found with ID: " + id);
                return;
            }

            System.out.println("Enter new product name:");
            String name = scanner.next();

            System.out.println("Enter new product price:");
            double price = scanner.nextDouble();

            System.out.println("Enter new product quantity:");
            int quantity = scanner.nextInt();

            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);

            session.update(product);
            transaction.commit();
            System.out.println("Product updated successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void deleteProduct(Scanner scanner) {
        Transaction transaction = session.beginTransaction();
        try {
            System.out.println("Enter product ID to delete:");
            long id = scanner.nextLong();

            Product product = session.get(Product.class, id);
            if (product == null) {
                System.out.println("Product not found with ID: " + id);
                return;
            }

            session.delete(product);
            transaction.commit();
            System.out.println("Product deleted successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
