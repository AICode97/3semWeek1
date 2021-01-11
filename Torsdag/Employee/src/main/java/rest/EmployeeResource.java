package rest;

import dto.EmployeeDTO;
import dto.EmployeeHighestSalaryDTO;
import entities.Employee;
import facades.EmployeeFacade;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author artem
 */
@Path("employees")
public class EmployeeResource {
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu"); 
    EmployeeFacade facade =  EmployeeFacade.getEmployeeFacade(emf);

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public ArrayList<EmployeeDTO> index() {
        ArrayList<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();
        
        for (Employee employee: facade.getAllEmployees()) {
            employeeDTOs.add(new EmployeeDTO(employee));
        }
        
        return employeeDTOs;
    }
    
    @Path("all")
    @GET
    public ArrayList<EmployeeDTO> getEmployees() {
        return this.index();
    }
    
    @Path("highestpaid")
    @GET
    public EmployeeHighestSalaryDTO getHighestPaidEmployee(){
        return new EmployeeHighestSalaryDTO(facade.getAllEmployeeWithHighestSalary());
    }
    
    @Path("{id}")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public EmployeeDTO getEmployeeById(@PathParam("id") int id) {
      return new EmployeeDTO(facade.findEmployeeById(id));
    }
  
    @Path("name/{name}")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public EmployeeDTO getEmployeeByName(@PathParam("name") String name) {
      return new EmployeeDTO(facade.findEmployeeByName(name));
    }
    
}
