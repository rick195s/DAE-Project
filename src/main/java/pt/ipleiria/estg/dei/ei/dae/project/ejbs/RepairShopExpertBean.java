package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.RepairShopExpert;
import pt.ipleiria.estg.dei.ei.dae.project.security.Hasher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RepairShopExpertBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Hasher hasher;

    public void create(String name, String email, String password, int repairShopId) {
        RepairShopExpert repairShopExpert = new RepairShopExpert(name, email, hasher.hash(password), repairShopId);
        entityManager.persist(repairShopExpert);
    }

    public RepairShopExpert find(int id) {
        return entityManager.find(RepairShopExpert.class, id);
    }
}
