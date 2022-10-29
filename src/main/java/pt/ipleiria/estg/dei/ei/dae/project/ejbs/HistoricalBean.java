package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Historical;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.HistoricalEnum;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.List;

@Stateless
public class HistoricalBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(int id, String name, String description, Calendar date) {
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

    public void update(int id, HistoricalEnum historicalEnum, String description, Calendar date) {
        Historical historical = findHistorical(id);
        if (historical == null) {
            throw new IllegalArgumentException("Historical does not exist");
        }
        historical.setState(historicalEnum);
        historical.setDescription(description);
        historical.setCalendar(date);
        entityManager.merge(historical);
    }

    public void delete(Historical historical) {
        entityManager.remove(entityManager.contains(historical) ? historical : entityManager.merge(historical));
    }
}
