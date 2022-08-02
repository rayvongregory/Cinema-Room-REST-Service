package cinema.Entity;

import javax.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {
    @SequenceGenerator(name = "seat_id_generator")
    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int seatId;

    @Column(name = "seat_row")
    private int seatRow;

    @Column(name = "seat_column")
    private int seatColumn;

    @Column(name = "seat_price")
    private int seatPrice;

    @Column(name = "available")
    private boolean available = true;

    public Seat() {}

    public Seat(int seatId, int seatRow, int seatColumn, int seatPrice) {
        this.seatId = seatId;
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
        this.seatPrice = seatPrice;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatColumn() {
        return seatColumn;
    }

    public void setSeatColumn(int seatColumn) {
        this.seatColumn = seatColumn;
    }

    public int getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(int seatPrice) {
        this.seatPrice = seatPrice;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatId=" + seatId +
                ", seatRow=" + seatRow +
                ", seatColumn=" + seatColumn +
                ", seatPrice=" + seatPrice +
                ", available=" + available +
                '}';
    }
}

