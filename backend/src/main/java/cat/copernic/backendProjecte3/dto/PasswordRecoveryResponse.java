package cat.copernic.backendProjecte3.dto;

public class PasswordRecoveryResponse {

    private String message;

    public PasswordRecoveryResponse() {}

    public PasswordRecoveryResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}