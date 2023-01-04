package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.Supervisor;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class RepairShopBean {

    @Inject
    Supervisor supervisor;

    public List<RepairShop> getAllRepairShops() {
        return supervisor.getRepairShops();
    }

    public RepairShop find(int id) {
        for (RepairShop repairShop : supervisor.getRepairShops()) {
            if (repairShop.getId() == id) {
                return repairShop;
            }
        }
        return null;
    }

    public RepairShop find(String email) {
        for (RepairShop repairShop : supervisor.getRepairShops()) {
            if (repairShop.getEmail().equals(email)) {
                return repairShop;
            }
        }
        return null;
    }
}

