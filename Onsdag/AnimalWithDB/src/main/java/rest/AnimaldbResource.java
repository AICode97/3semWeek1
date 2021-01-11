/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import Entity.Animal;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.PathParam;
/**
 * REST Web Service
 *
 * @author artem
 */
@Path("animal")
public class AnimaldbResource {

    @Context
    private UriInfo context;
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    /**
     * Creates a new instance of AnimaldbResource
     */
    public AnimaldbResource() {
    }

    /**
     * Retrieves representation of an instance of rest.AnimaldbResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "{}";
    }
    
@Path("animals")
@GET
@Produces(MediaType.APPLICATION_JSON)
public String getAnimals() {
  EntityManager em = emf.createEntityManager();
  try{
      TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a", Animal.class);
      List<Animal> animals = query.getResultList();
      return new Gson().toJson(animals);
   } finally {
          em.close();
   }
}

    @Path("{id}")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
   public Animal findAnimalById(Integer id){
         EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a WHERE a.id = :id", Animal.class);
            query.setParameter("id", id);
            Animal animal = (Animal)query.getSingleResult();
            return animal;
        }finally {
            em.close();
        }
    }
  
    @Path("type/{name}")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Animal findAnimalByType(String type){
         EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a WHERE a.name = :name", Animal.class);
            query.setParameter("type", type);
            return query.getSingleResult();
        }finally {
            em.close();
        }
    }
    
    
     
    
    
        


    /**
     * PUT method for updating or creating an instance of AnimaldbResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
