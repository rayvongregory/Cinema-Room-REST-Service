package cinema.Request;

import java.util.UUID;

public class ReturnTicketRequest {

    private String token;

    public ReturnTicketRequest () {}

    public ReturnTicketRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
