package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.project.Supervisor;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Administrator;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShopExpert;
import pt.ipleiria.estg.dei.ei.dae.project.security.Hasher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class RepairShopExpertBean {
    @Inject
    private Hasher hasher;

    @Inject
    Supervisor supervisor;

    public List<RepairShopExpert> getAllRepairShopExperts() {
        //return supervisor.getRepairShopExperts();
        return null;
    }

    public RepairShopExpert find(int id) {
        /*for (RepairShopExpert repairShopExpert : supervisor.getRepairShopExperts()) {
            if (repairShopExpert.getId() == id) {
                return repairShopExpert;
            }
        }
        return null;*/
        return null;
    }

    public RepairShopExpert find(String email) {
        /*for (RepairShopExpert repairShopExpert : supervisor.getRepairShopExperts()) {
            if (repairShopExpert.getEmail().equals(email)) {
                return repairShopExpert;
            }
        }
        return null;*/
        return null;
    }

    public RepairShopExpert findOrFail(String email) {
        var repairShopExpert = find(email);
        Hibernate.initialize(repairShopExpert);

        return repairShopExpert;
    }
}
