package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RepairShopBean {

    @PersistenceContext
    EntityManager entityManager;
    public void create(int id, String name, String email, String phone) {
        RepairShop repairShop = findRepairShop(id);
        if (repairShop != null) {
            throw new IllegalArgumentException("RepairShop already exists");
        }
        repairShop = new RepairShop(id,name,email,phone);
        entityManager.persist(repairShop);
    }

    public List<RepairShop> getAllRepairShops() {
        return (List<RepairShop>) entityManager.createNamedQuery("getAllRepairShops").getResultList();
    }

    public RepairShop findRepairShop(int id) {
        return entityManager.find(RepairShop.class, id);
    }
}

