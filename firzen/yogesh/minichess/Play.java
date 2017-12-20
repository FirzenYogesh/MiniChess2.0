package firzen.yogesh.minichess;

import firzen.yogesh.minichess.coordinate.Coordinate;
import firzen.yogesh.minichess.pieces.ChessPiece;
import firzen.yogesh.minichess.pieces.PieceBishop;
import firzen.yogesh.minichess.pieces.PieceCastle;
import firzen.yogesh.minichess.pieces.PiecePawn;
import firzen.yogesh.minichess.playingfield.ChessBoard;

import java.util.ArrayList;
import java.util.Scanner;

public class Play {

    public static void main(String[] args) {
        initPlaying();
    }

    private static void initPlaying() {
        Scanner scanner = new Scanner(System.in);
        Util.print("Enter the board size (min 5): ");
        int boardSize = scanner.nextInt();
        restrictChessPieces(boardSize);
        ChessBoard.setTotalPiecesForPlayers(boardSize);
        Util.print("Enter Player(P1) Pieces Selection");
        ChessPiece[] player1Pieces = getPlayerPieces(boardSize, Util.P1, scanner);
        resetLeftPieces();
        Util.print("Enter Player(P2) Pieces Selection");
        ChessPiece[] player2Pieces = getPlayerPieces(boardSize, Util.P2, scanner);
        ChessBoard chessBoard = new ChessBoard(player1Pieces, player2Pieces, boardSize);
        startPlaying(chessBoard, scanner);
    }

    private static void startPlaying(ChessBoard chessBoard, Scanner scanner) {
        while (ChessBoard.getPlayer1PiecesLeft() > 0 && ChessBoard.getPlayer2PiecesLeft() > 0) {
            chessBoard.displayBoard();
            Util.print(chessBoard.getWhoseTurn() + " Move:");
            String command = scanner.nextLine();
            chessBoard.giveCommand(command);
            System.out.println("Status : ");
            System.out.println("Player 1 Pieces left: " + ChessBoard.getPlayer1PiecesLeft());
            System.out.println("Player 2 Pieces left: " + ChessBoard.getPlayer2PiecesLeft());
        }
        announceResults();
    }

    private static void announceResults() {
        Util.print();
        if (ChessBoard.getPlayer1PiecesLeft() == 0) {
            Util.print("Player 2 Wins");
        } else {
            Util.print("Player 1 Wins");
        }
    }

    private static ChessPiece[] getPlayerPieces(int boardSize, String player, Scanner scanner) {
        ChessPiece[] pieces = new ChessPiece[boardSize];
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            String pieceType = getPieceType(scanner);
            String name = getPieceName(scanner, temp);
            Coordinate position = getPiecePosition(player, boardSize, i);
            ChessPiece piece = getPiece(pieceType, name, player, position);
            assignPiece(pieces, boardSize, i, player, piece);
        }
        return pieces;
    }

    private static String getPieceType(Scanner scanner) {
        Util.print("Enter the Piece Type:\np for Pawn\nc for Castle\nb for Bishop\n");
        String pieceType = scanner.nextLine().trim();
        while (!pieceType.toLowerCase().equals("p") && !pieceType.toLowerCase().equals("c") && !pieceType.toLowerCase().equals("b")) {
            Util.print("Enter a valid Piece Type");
            pieceType = scanner.nextLine().trim();
        }
        if (validatePieceType(pieceType))
            return pieceType;
        else
            return getPieceType(scanner);
    }

    private static boolean validatePieceType(String pieceType) {
        switch (pieceType.toLowerCase()) {
            case "p":
                if (numberOfPawnsLeft > 0)
                    return true;
                else {
                    Util.print("You have reached the limit of Pawns you can use");
                    return false;
                }
            case "c":
                if (numberOfCastleLeft > 0)
                    return true;
                else {
                    Util.print("You have reached the limit of Castle you can use");
                    return false;
                }
            case "b":
                if (numberOfBishopLeft > 0)
                    return true;
                else {
                    Util.print("You have reached the limit of Bishop you can use");
                    return false;
                }
        }
        return false;
    }

    private static String getPieceName(Scanner scanner, ArrayList<String> tempList) {
        Util.print("Enter the name of the piece");
        String name = scanner.nextLine().trim();
        while (name.equals("") || name.equals("\n")) {
            Util.print("Please enter a valid Character name");
            name = scanner.nextLine().trim();
        }
        while (tempList.contains(name)) {
            Util.print("Please enter a Unique Character name");
            name = scanner.nextLine().trim();
        }
        tempList.add(name);
        return name;
    }

    private static Coordinate getPiecePosition(String player, int boardSize, int currentPosition) {
        Coordinate position;
        if (player.equals(Util.P1))
            position = new Coordinate(boardSize - 1, currentPosition);
        else
            position = new Coordinate(0, (boardSize - 1) - currentPosition);
        return position;
    }

    private static ChessPiece getPiece(String pieceType, String name, String player, Coordinate position) {
        ChessPiece piece;
        switch (pieceType.toLowerCase()) {
            case "p":
                numberOfPawnsLeft--;
                piece = new PiecePawn(name, player, position);
                break;
            case "c":
                numberOfCastleLeft--;
                piece = new PieceCastle(name, player, position);
                break;
            case "b":
                numberOfBishopLeft--;
                piece = new PieceBishop(name, player, position);
                break;
            default:
                piece = null;
        }
        return piece;
    }

    private static void assignPiece(ChessPiece[] pieces, int boardSize, int i, String player, ChessPiece piece) {
        if (player.equals(Util.P1)) {
            pieces[i] = piece;
        } else {
            pieces[(boardSize - 1) - i] = piece;
        }
    }

    private static int numberOfPawns = 3;
    private static int numberOfCastle = 1;
    private static int numberOfBishop = 1;
    private static int numberOfPawnsLeft;
    private static int numberOfCastleLeft;
    private static int numberOfBishopLeft;

    private static void restrictChessPieces(int numberOfPieces) {
        for (int i = 6; i <= numberOfPieces; i++) {
            if (i % 3 == 0)
                numberOfCastle++;
            else if (i % 3 == 1)
                numberOfBishop++;
            else
                numberOfPawns++;
        }
        resetLeftPieces();
    }

    private static void resetLeftPieces() {
        numberOfPawnsLeft = numberOfPawns;
        numberOfBishopLeft = numberOfBishop;
        numberOfCastleLeft = numberOfCastle;
    }

}
