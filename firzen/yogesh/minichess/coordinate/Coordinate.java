package firzen.yogesh.minichess.coordinate;

public class Coordinate {
    private int row;
    private int column;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Coordinate(Coordinate coordinate) {
        this.row = coordinate.row;
        this.column = coordinate.column;
    }

    public void updateCoordinate(int rowSteps, int columnSteps) {
        this.row += rowSteps;
        this.column += columnSteps;
    }

    public void updateRowCoordinate(int steps) {
        this.row += steps;
    }

    public void updateColumnCoordinate(int steps) {
        this.column += steps;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return String.format("Row: %d\tColumn: %d", row, column);
    }
}
