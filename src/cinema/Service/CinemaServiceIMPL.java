package cinema.Service;

import cinema.DAO.PurchasedTicketDAO;
import cinema.DAO.SeatDAO;
import cinema.Entity.PurchasedTicket;
import cinema.Entity.Seat;
import cinema.Request.PurchaseSeatRequest;
import cinema.Request.ReturnTicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CinemaServiceIMPL implements CinemaService{

    @Autowired
    private SeatDAO seatDAO;

    @Autowired
    private PurchasedTicketDAO purchasedTicketDAO;

    @Override
    public List<HashMap<String, Integer>> getSeats() {
        Seat availableSeat = new Seat();
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("seatId").withIgnorePaths("seatRow").withIgnorePaths("seatColumn").withIgnorePaths("available").withIgnorePaths("seatPrice");
        Example<Seat> seatExample = Example.of(availableSeat, matcher);
        List<Seat> availableSeats = seatDAO.findAll(seatExample);
        List<HashMap<String, Integer>> availableSeats_FORMATTED = new ArrayList<>();
        for(Seat s: availableSeats) {
            HashMap<String, Integer> seatInfo = new HashMap<>();
            seatInfo.put("row", s.getSeatRow());
            seatInfo.put("column", s.getSeatColumn());
            seatInfo.put("price", s.getSeatPrice());
            availableSeats_FORMATTED.add(seatInfo);
        }
        return availableSeats_FORMATTED;
    }

    @Override
    public void addSeats() {
        int row = 1;
        int col = 1;
        while(row < 10) {
            Seat seat = new Seat();
            seat.setSeatRow(row);
            seat.setSeatColumn(col);
            if(row <= 4) seat.setSeatPrice(10);
            else seat.setSeatPrice(8);
            seatDAO.save(seat);
            if(col == 9) {
                col = 1;
                row++;
            }
            else col++;
        }
    }

    @Override
    public HashMap<String, Object> purchaseTicket(PurchaseSeatRequest purchaseSeatRequest) {
        int row = purchaseSeatRequest.getRow();
        int column = purchaseSeatRequest.getColumn();

        if(row < 1  || row >= 9 || column < 1 || column >=9) return new HashMap<>(){
            {
                put("error", "The number of a row or a column is out of bounds!");
            }
        };

        Seat desiredSeat = new Seat();
        desiredSeat.setSeatRow(row);
        desiredSeat.setSeatColumn(column);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnorePaths("seatId").withIgnorePaths("available").withIgnorePaths("seatPrice");
        Example<Seat> seatExample = Example.of(desiredSeat, matcher);
        Seat selectedSeat = seatDAO.findOne(seatExample).get();
        if(selectedSeat.isAvailable()) {
            selectedSeat.setAvailable(false);
            String token = UUID.randomUUID().toString();
            PurchasedTicket purchasedTicket = new PurchasedTicket(selectedSeat.getSeatId(), token);
            purchasedTicketDAO.save(purchasedTicket);
            seatDAO.save(selectedSeat);
            HashMap<String, Object> res = new HashMap<>();
            res.put("token", token);
            res.put("ticket", new HashMap<>(){
                {
                    put("row", row);
                    put("column", column);
                    put("price", selectedSeat.getSeatPrice());
                }
            });
            return res;
        } else return new HashMap<>() {
            {
                put("error", "The ticket has been already purchased!");
            }
        };
    }

    @Override
    public HashMap<String, Object> returnTicket(ReturnTicketRequest returnTicketRequest) {
        String token = returnTicketRequest.getToken();
        PurchasedTicket purchasedTicketToFind = new PurchasedTicket();
        purchasedTicketToFind.setToken(token);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnorePaths("seatId");
        Example<PurchasedTicket> purchasedTicketExample = Example.of(purchasedTicketToFind, matcher);
        try {
            PurchasedTicket purchasedTicket = purchasedTicketDAO.findOne(purchasedTicketExample).get();
            int seatId = purchasedTicket.getSeatId();
            Seat bookedSeat = seatDAO.getById(seatId);
            bookedSeat.setAvailable(true);
            seatDAO.save(bookedSeat);
            purchasedTicketDAO.delete(purchasedTicket);
            return new HashMap<>() {
                {
                    put("returned_ticket", new HashMap<>() {
                        {
                            put("row", bookedSeat.getSeatRow());
                            put("column", bookedSeat.getSeatColumn());
                            put("price", bookedSeat.getSeatPrice());
                        }
                    });
                }
            };
        } catch (NoSuchElementException e) {
            return new HashMap<>() {
                {
                    put("error", "Wrong token!");
                }
            };
        }
    }

    @Override
    public HashMap<String, Object> getStats(Optional<String> password) {
        String secret;
        try {
            secret = password.get().split("=")[1];
            if(!secret.equals("super_secret")) throw new NoSuchElementException();
        } catch (NoSuchElementException e) {
            return new HashMap<>() {
                {
                    put("error", "The password is wrong!");
                }
            };
        }

        int currentIncome = 0;
        int numAvailableSeats = 0;
        int numPurchasedSeats = 0;
        for (Seat s : seatDAO.findAll()) {
            if (s.isAvailable()) numAvailableSeats++;
            else {
                numPurchasedSeats++;
                currentIncome += s.getSeatPrice();
            }
        }
        int finalCurrentIncome = currentIncome;
        int finalNumAvailableSeats = numAvailableSeats;
        int finalNumPurchasedSeats = numPurchasedSeats;
        return new HashMap<>() {
            {
                put("current_income", finalCurrentIncome);
                put("number_of_available_seats", finalNumAvailableSeats);
                put("number_of_purchased_tickets", finalNumPurchasedSeats);
            }
        };
    }
}
