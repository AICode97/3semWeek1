package facades;

import entities.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author artem
 */
public class EmployeeFacade {

    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;

    private EmployeeFacade() {}

    public static EmployeeFacade getEmployeeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }
    
    public Employee createEmployee(String name, String address, double salary){
        Employee employee = new Employee(name, address, salary);
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
            return employee;
        }finally {
            em.close();
        }
    }
    
    public Employee findEmployeeById(int id){
         EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Employee> query = em.createQuery("SELECT employee FROM Employee employee WHERE employee.id = :id", Employee.class);
            query.setParameter("id", id);
            Employee employee = (Employee)query.getSingleResult();
            return employee;
        }finally {
            em.close();
        }
    }
    
    
        public Employee findEmployeeByName(String name){
         EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Employee> query = em.createQuery("SELECT employee FROM Employee employee WHERE employee.name = :name", Employee.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        }finally {
            em.close();
        }
    }

     
    public List<Employee> getAllEmployees(){
        EntityManager em = emf.createEntityManager();
        
        try {
            TypedQuery<Employee> query = em.createQuery("SELECT employee FROM Employee employee",Employee.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Employee getAllEmployeeWithHighestSalary(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> query = em.createQuery("SELECT employee FROM Employee employee WHERE employee.salary = (SELECT MAX(subEmployee.salary) FROM Employee subEmployee)", Employee.class);
            return query.getSingleResult();
        } finally{
            em.close();
        }
    }
    

}
