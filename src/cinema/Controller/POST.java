package cinema.Controller;

import cinema.Request.PurchaseSeatRequest;
import cinema.Request.ReturnTicketRequest;
import cinema.Service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Optional;

@RestController

public class POST {
    @Autowired
    private CinemaService cinemaService;

    @PostMapping("/add-seats")
    @ResponseBody
    public ResponseEntity<String> addSeats() {
        cinemaService.addSeats();
        return ResponseEntity.ok().body("Seats added");
    }

    @PostMapping("/purchase")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> purchaseTicket(@RequestBody PurchaseSeatRequest purchaseSeatRequest) {
        HashMap<String, Object> res = cinemaService.purchaseTicket(purchaseSeatRequest);
        if(res.containsKey("error")) return ResponseEntity.badRequest().body(res);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/return")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> returnTicket(@RequestBody ReturnTicketRequest returnTicketRequest) {
        HashMap<String, Object> res = cinemaService.returnTicket(returnTicketRequest);
        if(res.containsKey("error")) return ResponseEntity.badRequest().body(res);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/stats")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> getStatsWithOptionalBody(@RequestBody Optional<String> password) {
        HashMap<String, Object> res = cinemaService.getStats(password);
        if(res.containsKey("error")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        return ResponseEntity.ok().body(res);
    }
}
