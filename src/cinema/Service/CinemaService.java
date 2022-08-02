package cinema.Service;

import cinema.Request.PurchaseSeatRequest;
import cinema.Request.ReturnTicketRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface CinemaService {

//    public List<Seat> getSeats();

    List<HashMap<String, Integer>> getSeats();

    void addSeats();

    HashMap<String, Object> purchaseTicket(PurchaseSeatRequest purchaseSeatRequest);

    HashMap<String, Object> returnTicket(ReturnTicketRequest returnTicketRequest);

    HashMap<String, Object> getStats(Optional<String> password);

}
