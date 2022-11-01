package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.InsurerExpert;
import pt.ipleiria.estg.dei.ei.dae.project.entities.RepairShop;
import pt.ipleiria.estg.dei.ei.dae.project.entities.RepairShopExpert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RepairShopExpertBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(int id, String name, String email, String password, int repairShop_id) {
        RepairShopExpert repairShopExpert = findRepairShopExpert(id);
        if (repairShopExpert != null) {
            throw new IllegalArgumentException("RepairShopExpert already exists");
        }
        RepairShop repairShop= entityManager.find(RepairShop.class, repairShop_id);
        if (repairShop == null) {
            throw new IllegalArgumentException("RepairShop does not exist");
        }
        repairShopExpert = new RepairShopExpert(id, name, email, password, repairShop);
        entityManager.persist(repairShopExpert);
    }
    public RepairShopExpert findRepairShopExpert(int id) {
        return entityManager.find(RepairShopExpert.class, id);
    }

    public List<RepairShopExpert> getAllRepairShopsExperts() {
        return (List<RepairShopExpert>) entityManager.createNamedQuery("getAllRepairShopsExperts").getResultList();
    }
}
