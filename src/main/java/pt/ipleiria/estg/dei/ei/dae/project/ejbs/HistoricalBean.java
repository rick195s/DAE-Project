package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.enteties.Historical;
import pt.ipleiria.estg.dei.ei.dae.project.enteties.enums.Historical_Enum;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Stateless
public class HistoricalBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(int id, String name, String description, Date date) {
        Historical historical = findHistorical(id);
        if (historical != null) {
            throw new IllegalArgumentException("Historical already exists");
        }
        historical = new Historical(id, Historical_Enum.A_AGUARDAR_APROVACAO_PELA_SEGURADORA, description, date);
        entityManager.persist(historical);
    }

    public Historical findHistorical(int id) {
        return entityManager.find(Historical.class, id);
    }
}
