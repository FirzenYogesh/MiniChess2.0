package firzen.yogesh.minichess.pieces;

import firzen.yogesh.minichess.Util;
import firzen.yogesh.minichess.coordinate.Coordinate;

public class PieceBishop extends ChessPiece {

    public PieceBishop(String name, String player, Coordinate initialCoordinate) {
        super(name, player, initialCoordinate, 2);
    }

    @Override
    public void move() {
        oldCoordinate = currentCoordinate;
        currentCoordinate = newCoordinate;
    }

    @Override
    public boolean isValidMoveCommand(String move) {
        switch (move.toLowerCase()) {
            case "fl":
            case "fr":
            case "bl":
            case "br":
                return true;
        }
        Util.print("Invalid Move", "Move Command for Bishop should only be one of (FL,FR,BL,BR)");
        return false;
    }

    @Override
    protected void predictMove(String move) {
        newCoordinate = new Coordinate(currentCoordinate);
        switch (move) {
            case "fl":
                moveFrontLeft(newCoordinate);
                break;
            case "fr":
                moveFrontRight(newCoordinate);
                break;
            case "bl":
                moveBackLeft(newCoordinate);
                break;
            case "br":
                moveBackRight(newCoordinate);
                break;
        }
    }
}
