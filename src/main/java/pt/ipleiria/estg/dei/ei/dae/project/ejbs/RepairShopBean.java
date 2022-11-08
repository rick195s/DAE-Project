package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.RepairShop;

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

    public void update(int id, String name, String email, String phone) {
        RepairShop repairShop = findRepairShop(id);
        if (repairShop == null) {
            throw new IllegalArgumentException("RepairShop does not exist");
        }
        repairShop.setName(name);
        repairShop.setEmail(email);
        repairShop.setPhone(phone);
        entityManager.persist(repairShop);
    }

    public void delete(int id) {
        RepairShop repairShop = findRepairShop(id);
        if (repairShop == null) {
            throw new IllegalArgumentException("RepairShop does not exist");
        }
        entityManager.remove(repairShop);
    }
}

