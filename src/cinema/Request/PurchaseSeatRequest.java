package cinema.Request;

public class PurchaseSeatRequest {

    private int row;
    private int column;

    public PurchaseSeatRequest() {}

    public PurchaseSeatRequest(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() { return row; }

    public int getColumn() {
        return column;
    }
}
