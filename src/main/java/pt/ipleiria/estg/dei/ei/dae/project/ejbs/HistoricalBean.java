package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Historical;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.HistoricalEnum;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class HistoricalBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(String description, int occurrenceId , String date) {
        Occurrence occurrence = entityManager.find(Occurrence.class, occurrenceId);
        if (occurrence == null) {
            throw new IllegalArgumentException("Occurrence does not exist");
        }

        Historical historical = new Historical(HistoricalEnum.A_AGUARDAR_APROVACAO_PELA_SEGURADORA, description, occurrence, date);
        entityManager.persist(historical);
    }

    public List<Historical> getAllHistorical() {
        return (List<Historical>) entityManager.createNamedQuery("getAllHistoricals").getResultList();
    }

    public Historical find(int id) {
        return entityManager.find(Historical.class, id);
    }
}
