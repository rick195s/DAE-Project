package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.Supervisor;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
public class InsurerBean {
    @Inject
    Supervisor supervisor;

    public List<Insurer> getAllInsurers() {
        return supervisor.getInsurers();
    }

    public Insurer find(int id) {
        for (Insurer insurer : supervisor.getInsurers()) {
            if (insurer.getId() == id) {
                return insurer;
            }
        }
        return null;
    }

    public List<RepairShop> getRepairShops(int id) {
        return supervisor.getRepairShops(id);
    }
}
