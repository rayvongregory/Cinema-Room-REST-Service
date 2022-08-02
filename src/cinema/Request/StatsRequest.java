package cinema.Request;

public class StatsRequest {

    private String secretPassword;

    public StatsRequest() {}

    public StatsRequest(String secretPassword) {
        this.secretPassword = secretPassword;
    }

    public String getSecretPassword() {
        return secretPassword;
    }
}
