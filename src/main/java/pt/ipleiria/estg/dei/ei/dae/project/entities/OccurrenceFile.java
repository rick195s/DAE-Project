package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllOccurrencesFiles",
                query = "SELECT o FROM OccurrenceFile o ORDER BY o.id" // JPQL
        )
})
@Table(
        name = "occurrences_files"
)
public class OccurrenceFile implements Serializable {
    @Id
    private int id;
    private String name;
    private String path;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "occurrence_id")
    private Occurrence occurrence;

    public OccurrenceFile() {
    }
    public OccurrenceFile(int id, String name, String path, Occurrence occurrence) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.occurrence = occurrence;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Occurrence getOccurence() {
        return this.occurrence;
    }
    public void setOccurence(Occurrence occurrence) {
        this.occurrence = occurrence;
    }
}
