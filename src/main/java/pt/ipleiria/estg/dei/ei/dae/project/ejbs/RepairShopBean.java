package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.Supervisor;
import pt.ipleiria.estg.dei.ei.dae.project.gateways.RepairShopGateway;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.regex.Pattern;

@Stateless
public class RepairShopBean {

    @Inject
    Supervisor supervisor;

    public RepairShop create(String name, String email, long phone) {
        List<RepairShop> repairShops = supervisor.getRepairShops();

        if (!Pattern.compile("^(.+)@(\\S+)$")
                .matcher(email)
                .matches()) {
            throw new IllegalArgumentException("Email " + email + " not valid");
        }

        for (RepairShop repairShop : repairShops) {
            // if there is a repair shop with the email than we dont need to create it
            if (repairShop.getEmail().equals(email)) {
                return repairShop;
            }
            if (repairShop.getPhone() == phone) {
                throw new IllegalArgumentException("Repair shop with phone " + phone + " already exists");
            }
        }

        RepairShop repairShop = new RepairShop(name, email, phone);
        RepairShopGateway gateway = new RepairShopGateway();
        return gateway.postToMockAPI(repairShop);
    }

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

