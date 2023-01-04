package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.project.Supervisor;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Administrator;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;
import pt.ipleiria.estg.dei.ei.dae.project.security.Hasher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AdministratorBean {
    @Inject
    private Hasher hasher;

    @Inject
    Supervisor supervisor;

    public List<Administrator> getAllAdministrators() {
        //return supervisor.getAdministrators();
        return null;
    }

    public Administrator find(int id) {
        /*for (Administrator administrator : supervisor.getAdministrators()) {
            if (administrator.getId() == id) {
                return administrator;
            }
        }
        return null;*/
        return null;
    }

    public Administrator find(String email) {
        /*for (Administrator administrator : supervisor.getAdministrators()) {
            if (administrator.getEmail().equals(email)) {
                return administrator;
            }
        }
        return null;*/
        return null;
    }

    public Administrator findOrFail(String email) {
        var admin = find(email);
        Hibernate.initialize(admin);

        return admin;
    }
}