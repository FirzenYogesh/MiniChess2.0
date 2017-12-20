package firzen.yogesh.minichess.playingfield;

import firzen.yogesh.minichess.Util;
import firzen.yogesh.minichess.coordinate.Coordinate;
import firzen.yogesh.minichess.pieces.ChessPiece;

public class ChessBoard {

    private ChessPiece[][] chessBlocks;
    private ChessPiece[] player1Pieces;
    private ChessPiece[] player2Pieces;
    private boolean isPlayer1Move = true;
    private int boardSize = 5;


    public ChessBoard(ChessPiece[] player1Pieces, ChessPiece[] player2Pieces, int boardSize) {
        if (boardSize < 5)
            boardSize = 5;
        this.boardSize = boardSize;
        this.player1Pieces = player1Pieces;
        this.player2Pieces = player2Pieces;
        chessBlocks = new ChessPiece[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            chessBlocks[boardSize - 1][i] = player1Pieces[i];
            chessBlocks[0][i] = player2Pieces[i];
        }
    }

    public void displayBoard() {
        System.out.println();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (chessBlocks[i][j] == null) {
                    System.out.printf("%2s-%2s", " ", " ");
                } else {
                    System.out.printf("%1s%s-%s%1s", " ", chessBlocks[i][j].getPlayer(), chessBlocks[i][j].getName(), " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean isCommandFormatValid(String command) {
        if (command.contains(":")) {
            String[] tempStrings = command.split(":");
            return tempStrings.length == 2;
        }
        return false;
    }

    private boolean isMoveWithinTheBoard(ChessPiece piece) {
        return piece != null &&
                piece.getNewCoordinate().getRow() >= 0 &&
                piece.getNewCoordinate().getRow() < boardSize &&
                piece.getNewCoordinate().getColumn() >= 0 &&
                piece.getNewCoordinate().getColumn() < boardSize;
    }

    private boolean hasFriendlyPiece(Coordinate coordinate, ChessPiece piece) {
        ChessPiece blockToCheck = getPieceAt(coordinate);
        return blockToCheck != null && (piece.isFriend(blockToCheck));
    }

    private boolean hasEnemyPiece(Coordinate coordinate, ChessPiece piece) {
        ChessPiece blockToCheck = getPieceAt(coordinate);
        return blockToCheck != null && (piece.isEnemy(blockToCheck));
    }

    private ChessPiece getPieceAt(Coordinate coordinate) {
        return chessBlocks[coordinate.getRow()][coordinate.getColumn()];
    }

    public void giveCommand(String command) {
        boolean commandSuccessful = false;
        if (isCommandFormatValid(command)) {
            String name = CommandParser.getPieceName(command);
            String move = CommandParser.getMoveCommand(command);
            ChessPiece pieceToMove =
                    isPlayer1Move ? pickPlayerPiece(name, player1Pieces) : pickPlayerPiece(name, player2Pieces);
            if (pieceToMove == null) {
                Util.print("Invalid Move.", "This Piece does not exist");
            } else {
                commandSuccessful = moveAPiece(pieceToMove, move);
                if (commandSuccessful) {
                    swapPiece(pieceToMove);
                    isPlayer1Move = !isPlayer1Move;
                }
            }
        }
        //return commandSuccessful;
    }

    private boolean moveAPiece(ChessPiece piece, String move) {
        boolean moveSuccessful = false;
        if (piece.isDead()) Util.print("Invalid Move.", "This soldier is dead");
        else {
            if (piece.isValidMoveCommand(move)) {
                piece.prepareMove(move);
                if (isMoveWithinTheBoard(piece)) {
                    if (hasFriendlyPiece(piece.getNewCoordinate(), piece)) {
                        Util.print("Invalid Move.", "A friendly Piece is on the way");
                    } else {
                        if (hasEnemyPiece(piece.getNewCoordinate(), piece))
                            piece.killPiece(getPieceAt(piece.getNewCoordinate()));
                        moveSuccessful = true;
                    }
                } else Util.print("Invalid Move.", "The Move goes out of the board");
            } else Util.print("Invalid Move.", "Wrong Command format");
        }
        return moveSuccessful;
    }

    private ChessPiece pickPlayerPiece(String name, ChessPiece[] playerPool) {
        for (ChessPiece piece : playerPool) {
            if (piece.getName().equals(name)) {
                return piece;
            }
        }
        return null;
    }

    private void swapPiece(ChessPiece piece) {
        Coordinate newPosition = piece.getNewCoordinate();
        Coordinate currentPosition = piece.getCurrentCoordinate();
        chessBlocks[newPosition.getRow()][newPosition.getColumn()] = piece;
        chessBlocks[currentPosition.getRow()][currentPosition.getColumn()] = null;
        piece.move();
    }

    public String getWhoseTurn() {
        return isPlayer1Move ? Util.P1 : Util.P2;
    }

    private static int player1PiecesLeft = 5;
    private static int player2PiecesLeft = 5;

    public static void setTotalPiecesForPlayers(int boardSize) {
        player1PiecesLeft = boardSize;
        player2PiecesLeft = boardSize;
    }

    public static int getPlayer1PiecesLeft() {
        return player1PiecesLeft;
    }

    public static int getPlayer2PiecesLeft() {
        return player2PiecesLeft;
    }

    public static void removedPlayer1Piece() {
        player1PiecesLeft--;
    }

    public static void removedPlayer2Piece() {
        player2PiecesLeft--;
    }
}
