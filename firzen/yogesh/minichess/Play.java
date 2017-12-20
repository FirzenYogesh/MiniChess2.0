package firzen.yogesh.minichess;

import firzen.yogesh.minichess.coordinate.Coordinate;
import firzen.yogesh.minichess.pieces.ChessPiece;
import firzen.yogesh.minichess.pieces.PieceBishop;

public class Play {

    public static void main(String[] args) {
        ChessPiece piece = new PieceBishop("A", Util.P1, new Coordinate(0, 0));
        piece.prepareMove("fl");
        System.out.println(piece.getCurrentCoordinate());
        System.out.println(piece.getNewCoordinate());
    }
}
