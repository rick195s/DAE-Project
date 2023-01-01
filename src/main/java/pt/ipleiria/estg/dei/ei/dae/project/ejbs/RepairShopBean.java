package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.Supervisor;
import pt.ipleiria.estg.dei.ei.dae.project.gateways.RepairShopGateway;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RepairShopBean {

    @Inject
    Supervisor supervisor;

    public void create(String name, String email, long phone) {
        //verify if some repair shop has already the same email or phone
        for (RepairShop repairShop : supervisor.getRepairShops()) {
            if (repairShop.getEmail().equals(email)) {
                throw new IllegalArgumentException("Repair shop with email " + email + " already exists");
            }
            if (repairShop.getPhone() == phone) {
                throw new IllegalArgumentException("Repair shop with phone " + phone + " already exists");
            }
        }

        RepairShop repairShop = new RepairShop(name, email, phone);
        supervisor.addRepairShop(repairShop);
    }

    public List<RepairShop> getAllRepairShops() {
        return supervisor.getRepairShops();
    }

    public RepairShop findRepairShop(int id) {
        for (RepairShop repairShop : supervisor.getRepairShops()) {
            if (repairShop.getId() == id) {
                return repairShop;
            }
        }
        return null;
    }

    public RepairShop findRepairShop(String email) {
        for (RepairShop repairShop : supervisor.getRepairShops()) {
            if (repairShop.getEmail().equals(email)) {
                return repairShop;
            }
        }
        return null;
    }
}

