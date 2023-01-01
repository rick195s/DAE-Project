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

    public static void create(String name, String email, long phone) {
        RepairShopGateway repairShopGateway = new RepairShopGateway();

        //verify if some repair shop has already the same email or phone
        List<RepairShop> repairShops = repairShopGateway.getFromMockAPI();
        for (RepairShop repairShop : repairShops) {
            if (repairShop.getEmail().equals(email)) {
                throw new IllegalArgumentException("Repair shop with email " + email + " already exists");
            }
            if (repairShop.getPhone() == phone) {
                throw new IllegalArgumentException("Repair shop with phone " + phone + " already exists");
            }
        }

        RepairShop repairShop = new RepairShop(name, email, phone);
        repairShopGateway.postToMockAPI(repairShop);
    }

    public List<RepairShop> getAllRepairShops() {
        return configBean.getRepairShops();
    }

    public RepairShop findRepairShop(int id) {
        return configBean.getRepairShopById(id);
    }

    public RepairShop findRepairShop(String email) {
        return configBean.getRepairShopByEmail(email);
    }
}

