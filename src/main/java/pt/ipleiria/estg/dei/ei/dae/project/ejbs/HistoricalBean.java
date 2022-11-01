package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Historical;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
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

    public void create(int id, String description, int occurrenceId , Calendar date) {
        Historical historical = findHistorical(id);
        if (historical != null) {
            throw new IllegalArgumentException("Historical already exists");
        }

        Occurrence occurrence = entityManager.find(Occurrence.class, occurrenceId);
        if (occurrence == null) {
            throw new IllegalArgumentException("Occurrence does not exist");
        }

        historical = new Historical(id, HistoricalEnum.A_AGUARDAR_APROVACAO_PELA_SEGURADORA, description, occurrence, date);
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
        historical.setDate(date);
        entityManager.merge(historical);
    }

    public void delete(Historical historical) {
        entityManager.remove(entityManager.contains(historical) ? historical : entityManager.merge(historical));
    }
}
