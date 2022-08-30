package com.spark.supermart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class App {
	public static <T> void main(String[] args) {
		Customer customer1 = new Customer();
		customer1.setCustomer_name("John");

		Product product1 = new Product();
		product1.setProduct_name("Mobile");
		product1.setCustomer(customer1);

		Product product2 = new Product();
		product2.setProduct_name("Charger");
		product2.setCustomer(customer1);

		Product product3 = new Product();
		product3.setProduct_name("EarPhone");
		product3.setCustomer(customer1);

		List<Product> plist1 = new ArrayList<>();
		plist1.add(product1);
		plist1.add(product2);
		plist1.add(product3);

		customer1.setList(plist1);

		Configuration configuration = new Configuration().configure().addAnnotatedClass(Customer.class)
				.addAnnotatedClass(Product.class);

		SessionFactory sessionfactory = configuration.buildSessionFactory();
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(customer1);
		session.save(product1);
		session.save(product2);

		Query query_all = session.createQuery("from Product");
		List<Product> productList = query_all.list();
		productList.stream().forEach(e -> System.out.println(e.getProduct_name() + " " + e.getProduct_id()));

		Query<?> createQuery = session
				.createQuery("select product.product_id,product.product_name from Product product");
		List<?> list_pr = createQuery.list();
		list_pr.stream().forEach(e -> System.out.println(e.getClass().getSimpleName()));

		for (Object row : list_pr) {
			Object[] x = (Object[]) row;
			for (Object data : x) {
				System.out.println(data);
			}
		}

		Query<?> createQuerys = session
				.createQuery("select customer.customer_id,customer.customer_name from Customer customer");
		List<?> list = createQuerys.list();
		list.stream().forEach(e -> System.out.println(e.getClass().getSimpleName()));

		for (Object row : list) {
			Object[] x = (Object[]) row;
			for (Object data : x) {
				System.out.println(data);
			}
		}

		transaction.commit();

//		System.out.println(session.get(Customer.class, 1));
//		
//		Customer customer = session.get(Customer.class, 1);
//		customer.getList().stream().forEach(customers->System.out.println(customers.getProduct_name()));
//		

	}
}
