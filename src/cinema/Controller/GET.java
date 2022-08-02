package cinema.Controller;


import cinema.Service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class GET {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping("/seats")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> getSeatInformation() {
        return ResponseEntity.ok().body(new HashMap<>(){
            {
                put("total_rows", 9);
                put("total_columns", 9);
                put("available_seats", cinemaService.getSeats());
            }
        });
    }
}
