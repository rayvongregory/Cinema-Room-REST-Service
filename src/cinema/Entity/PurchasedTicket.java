package cinema.Entity;

import javax.persistence.*;

@Entity
@Table(name = "purchased_tickets")
public class PurchasedTicket {
    @Id
    @Column(name = "seat_id")
    private int seatId;

    @Column(name = "token")
    private String token;

    public PurchasedTicket() {}

    public PurchasedTicket(int seatId, String token) {
        this.seatId = seatId;
        this.token = token;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "PurchasedTicket{" +
                "seatId=" + seatId +
                ", token='" + token + '\'' +
                '}';
    }
}

