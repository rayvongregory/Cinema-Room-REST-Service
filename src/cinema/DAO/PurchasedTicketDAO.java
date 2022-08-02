package cinema.DAO;

import cinema.Entity.PurchasedTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasedTicketDAO extends JpaRepository<PurchasedTicket, Integer> {
}
