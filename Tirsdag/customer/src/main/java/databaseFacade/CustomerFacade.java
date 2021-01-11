/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseFacade;

import entity.Customer;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author artem
 */
public class CustomerFacade {
    
    public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");      
    CustomerFacade facade = CustomerFacade.getCustomerFacade(emf);
    Customer customer = facade.addCustomer("Karen", "Jensen", new Date());
    Customer customer1 = facade.addCustomer("Emma", "Jensen", new Date());
    System.out.println("Customer: "+facade.findCustomerById(customer.getId()).getFirstName());
    System.out.println("Customer: "+facade.findCustomerById(customer1.getId()).getFirstName());
    System.out.println("Number of customers: "+facade.getAllCustomers().size());
    }
    
    
    private static EntityManagerFactory entityManagerFactory;
    private static CustomerFacade instance;

    private CustomerFacade() {}

    public static CustomerFacade getCustomerFacade(EntityManagerFactory _entityManagerFactory) {
        if (instance == null) {
            entityManagerFactory = _entityManagerFactory;
            instance = new CustomerFacade();
        }
        return instance;
    }
    
    public Customer addCustomer(String firstName, String lastName, Date created ){
        Customer customer = new Customer(firstName, lastName, created);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(customer);
            entityManager.getTransaction().commit();
            return customer;
        }finally {
            entityManager.close();
        }
    }
    
    public Customer findCustomerById(int id){
         EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            Customer customer = entityManager.find(Customer.class,id);
            return customer;
        }finally {
            entityManager.close();
        }
    }
    public List<Customer> getAllCustomers(){
         EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            TypedQuery<Customer> query = entityManager.createQuery("Select customer from Customer customer",Customer.class);
            return query.getResultList();
        }finally {
            entityManager.close();
        }
    }

}

