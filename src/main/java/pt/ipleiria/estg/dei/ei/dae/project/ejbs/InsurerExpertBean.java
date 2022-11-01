package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.entities.InsurerExpert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class InsurerExpertBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(int id, String name, String email, String password, int insurerId) {
        InsurerExpert insurer_expert = findInsurerExpert(id);
        if (insurer_expert != null) {
            throw new IllegalArgumentException("Insurer_Expert already exists");
        }

        Insurer insurer = entityManager.find(Insurer.class, insurerId);
        if (insurer == null) {
            throw new IllegalArgumentException("Insurer does not exist");
        }

        insurer_expert = new InsurerExpert(id, name, email, password, insurer);
        entityManager.persist(insurer_expert);
    }

    public List<InsurerExpert> getAllInsurerExpert() {
        return (List<InsurerExpert>) entityManager.createNamedQuery("getAllInsurerExperts").getResultList();
    }

    public InsurerExpert findInsurerExpert(int id) {
        return entityManager.find(InsurerExpert.class, id);
    }

    public void delete(int id) {
        InsurerExpert insurer_expert = findInsurerExpert(id);
        if (insurer_expert == null) {
            throw new IllegalArgumentException("Insurer_Expert does not exist");
        }
        entityManager.remove(insurer_expert);
    }

    public void update(int id, String name, String email, String password, int insurerId) {
        InsurerExpert insurer_expert = findInsurerExpert(id);
        if (insurer_expert == null) {
            throw new IllegalArgumentException("Insurer_Expert does not exist");
        }

        Insurer insurer = entityManager.find(Insurer.class, insurerId);
        if (insurer == null) {
            throw new IllegalArgumentException("Insurer does not exist");
        }

        insurer_expert.setName(name);
        insurer_expert.setEmail(email);
        insurer_expert.setPassword(password);
        insurer_expert.setInsurer(insurer);
    }
}
