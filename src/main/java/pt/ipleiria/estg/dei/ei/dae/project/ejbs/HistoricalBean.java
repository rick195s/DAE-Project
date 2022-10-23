package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Historical;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.HistoricalEnum;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless
public class HistoricalBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(int id, String name, String description, Date date) {
        Historical historical = findHistorical(id);
        if (historical != null) {
            throw new IllegalArgumentException("Historical already exists");
        }
        historical = new Historical(id, HistoricalEnum.A_AGUARDAR_APROVACAO_PELA_SEGURADORA, description, date);
        entityManager.persist(historical);
    }

    public List<Historical> getAllHistorical() {
        return (List<Historical>) entityManager.createNamedQuery("getAllHistoricals").getResultList();
    }

    public Historical findHistorical(int id) {
        return entityManager.find(Historical.class, id);
    }
}
