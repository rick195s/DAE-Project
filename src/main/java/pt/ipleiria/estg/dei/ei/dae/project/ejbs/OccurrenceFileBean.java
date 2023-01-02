package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Historical;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.project.entities.OccurrenceFile;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.ApprovalType;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.OccurrenceSmallDescriptionException;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.MultivaluedMap;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Stateless
public class OccurrenceFileBean {
    @PersistenceContext
    EntityManager entityManager;

    @EJB
    OccurrenceBean occurrenceBean;

    public OccurrenceFile create(int occurrenceId, String filename, String filepath){
        var occurrence = occurrenceBean.findOccurrence(occurrenceId);

        var occurrenceFile = new OccurrenceFile(1, filename, filepath, occurrence);

        entityManager.persist(occurrenceFile);
        occurrence.addOccurenceFile(occurrenceFile);

        return occurrenceFile;
    }
}
