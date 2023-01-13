package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.InsurerExpert;
import pt.ipleiria.estg.dei.ei.dae.project.entities.RepairShopExpert;
import pt.ipleiria.estg.dei.ei.dae.project.security.Hasher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class InsurerExpertBean {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Hasher hasher;

    public void create(String name, String email, String password, int repairShopId) {
        InsurerExpert repairShopExpert = new InsurerExpert(name, email, hasher.hash(password), repairShopId);
        entityManager.persist(repairShopExpert);
    }

    public InsurerExpert find(int id) {
        return entityManager.find(InsurerExpert.class, id);
    }
}
