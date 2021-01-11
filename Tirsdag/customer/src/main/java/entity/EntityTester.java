/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author artem
 */
public class EntityTester {
   
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        
        try{
            Customer c1 = new Customer("Jens", "Hansen", new Date());
            Customer c2 = new Customer("Einar", "Pedersen", new Date());
            Customer c3 = new Customer("Mikkel", "Espersen", new Date());

                
            entityManager.getTransaction().begin();
           
            entityManager.persist(c3);
            entityManager.persist(c2);
            entityManager.persist(c1);

                       
            entityManager.getTransaction().commit();
        } finally{
            entityManager.close();
        }
        
    }
}
