package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import javax.validation.constraints.NotBlank;

public class ConfirmPassword {
    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmNewPassword;

    public ConfirmPassword() {}

    public ConfirmPassword(String oldPassword, String newPassword, String confirmNewPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
