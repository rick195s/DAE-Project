package pt.ipleiria.estg.dei.ei.dae.project.exceptions;

public class OccurrenceSmallDescriptionException extends Exception {
    private String description;

    public OccurrenceSmallDescriptionException(String description) {
        super(
                "Description '"+ description +"' is too small"
        );

        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
