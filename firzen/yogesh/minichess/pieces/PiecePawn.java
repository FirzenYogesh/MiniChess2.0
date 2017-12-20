package firzen.yogesh.minichess.pieces;

import firzen.yogesh.minichess.Util;
import firzen.yogesh.minichess.coordinate.Coordinate;

public class PiecePawn extends ChessPiece {


    public PiecePawn(String name, String player, Coordinate initialCoordinate) {
        super(name, player, initialCoordinate, 1);
    }

    protected PiecePawn(String name, String player, Coordinate initialCoordinate, int steps) {
        super(name, player, initialCoordinate, steps);
    }

    @Override
    public void move() {
        oldCoordinate = currentCoordinate;
        currentCoordinate = newCoordinate;
    }

    @Override
    public boolean isValidMoveCommand(String move) {
        switch (move.toLowerCase()) {
            case "f":
            case "b":
            case "l":
            case "r":
                return true;
        }
        Util.print("Invalid Move", "Move Command for Pawn should only be one of (F,B,L,R)");
        return false;
    }

    @Override
    public void predictMove(String move) {
        newCoordinate = new Coordinate(currentCoordinate);
        switch (move.toLowerCase()) {
            case "f":
                moveFront(newCoordinate);
                break;
            case "b":
                moveBack(newCoordinate);
                break;
            case "l":
                moveLeft(newCoordinate);
                break;
            case "r":
                moveRight(newCoordinate);
                break;
        }
    }
}
