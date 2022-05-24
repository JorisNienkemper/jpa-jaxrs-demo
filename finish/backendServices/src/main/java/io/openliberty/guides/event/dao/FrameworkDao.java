package io.openliberty.guides.event.dao;

import io.openliberty.guides.event.models.Framework;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped

public class FrameworkDao {

    @PersistenceContext(name = "jpa-unit")
    private EntityManager em;

    public void save(Framework framework){
        em.persist(framework);
    }

    public Framework getById(int id){
       return   em.find(Framework.class,id);
    }

    public void update(Framework updatedFramework){
        em.merge(updatedFramework);
    }

    public void delete(Framework framework){
        Framework framework1 = em.merge(framework);
        em.remove(framework1);
    }

    public Framework getFrameworkByName(String name){

        String jpqlString ="SELECT f FROM Framework f WHERE f.name=:name";
        return em.createQuery(jpqlString,Framework.class)
                .setParameter("name",name)
                .getSingleResult();
    }

    public List<Framework> getFrameworks() {
        return em.createQuery("SELECT f FROM Framework f").getResultList();
    }
}
