package pt.ipleiria.estg.dei.ei.dae.project.dtos;

public class ErrorDTO {

    private String key;

    private String value;

    private String reason;

    public ErrorDTO(String reason) {
        this.reason = reason;
    }

    public ErrorDTO(String key, String value, String reason) {
        this(reason);
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
