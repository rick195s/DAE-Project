package pt.ipleiria.estg.dei.ei.dae.project.exceptions;

public class PasswordInvalidException extends Exception {
    private String description;

    public PasswordInvalidException(String description) {
        super(
                description
        );

        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
