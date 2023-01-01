package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.gateways.RepairShopGateway;
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

    public void create(String name, String email, long phone) {
        //verify if some repair shop has already the same email or phone
        for (RepairShop repairShop : configBean.getRepairShops()) {
            if (repairShop.getEmail().equals(email)) {
                throw new IllegalArgumentException("Repair shop with email " + email + " already exists");
            }
            if (repairShop.getPhone() == phone) {
                throw new IllegalArgumentException("Repair shop with phone " + phone + " already exists");
            }
        }

        RepairShop repairShop = new RepairShop(name, email, phone);
        configBean.addRepairShop(repairShop);
    }

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

    public RepairShop findRepairShop(String email) {
        for (RepairShop repairShop : configBean.getRepairShops()) {
            if (repairShop.getEmail().equals(email)) {
                return repairShop;
            }
        }
        return null;
    }
}

