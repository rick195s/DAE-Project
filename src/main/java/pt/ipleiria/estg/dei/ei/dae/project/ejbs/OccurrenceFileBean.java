package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.OccurrenceFile;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OccurrenceFileBean {
    @PersistenceContext
    EntityManager entityManager;

    @EJB
    OccurrenceBean occurrenceBean;

    public OccurrenceFile create(int occurrenceId, String filename, String filepath){
        var occurrence = occurrenceBean.find(occurrenceId);

        var occurrenceFile = new OccurrenceFile(filename, filepath, occurrence);

        entityManager.persist(occurrenceFile);
        occurrence.addOccurenceFile(occurrenceFile);

        return occurrenceFile;
    }

    public OccurrenceFile find(int id) {
        return entityManager.find(OccurrenceFile.class, id);
    }
}
