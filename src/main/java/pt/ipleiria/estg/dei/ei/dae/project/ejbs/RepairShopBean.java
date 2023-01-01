package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RepairShopBean {
    @PersistenceContext
    EntityManager entityManager;

    @EJB
    private ConfigBean configBean;

    public List<RepairShop> getAllRepairShops() {
        return configBean.getRepairShops();
    }

    public RepairShop findRepairShop(int id) {
        for (RepairShop repairShop : configBean.getRepairShops()) {
            if (repairShop.getId() == id) {
                return repairShop;
            }
        }
        return null;
    }
}

