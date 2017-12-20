package firzen.yogesh.minichess.pieces;

import firzen.yogesh.minichess.Util;
import firzen.yogesh.minichess.coordinate.Coordinate;

public abstract class ChessPiece {
    protected int steps;
    protected String name;
    protected String player;
    protected Coordinate currentCoordinate;
    protected Coordinate oldCoordinate;
    protected Coordinate newCoordinate;
    protected boolean isAlive = true;
    protected boolean isDead = false;

    ChessPiece(String name, String player, Coordinate initialCoordinate, int steps) {
        this.name = name;
        this.player = player;
        this.currentCoordinate = initialCoordinate;
        this.oldCoordinate = new Coordinate(initialCoordinate);
        this.steps = steps;
    }

    public void prepareMove(String move) {
        predictMove(move);
    }

    public abstract void move();

    public abstract boolean isValidMoveCommand(String move);

    protected abstract void predictMove(String move);

    public Coordinate getNewCoordinate() {
        return newCoordinate;
    }

    public Coordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public Coordinate getOldCoordinate() {
        return oldCoordinate;
    }

    public String getName() {
        return name;
    }

    public String getPlayer() {
        return player;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void kill() {
        isDead = true;
        isAlive = false;
    }

    public void killPiece(ChessPiece piece) {
        if (isEnemy(piece)) {
            piece.kill();
        }
    }

    public boolean isFriend(ChessPiece piece) {
        return piece.player.equals(this.player);
    }

    public boolean isEnemy(ChessPiece piece) {
        return !piece.player.equals(this.player);
    }

    public boolean equals(ChessPiece obj) {
        return obj.player.equals(this.player) && obj.name.equals(this.player);
    }

    public boolean belongsToPlayer1() {
        return this.player.equals(Util.P1);
    }

    public boolean belongsToPlayer2() {
        return this.player.equals(Util.P2);
    }

    void moveLeft(Coordinate coordinate) {
        int moveColumnFactor = belongsToPlayer1() ? -1 : 1;
        coordinate.updateColumnCoordinate(getStepsToMove(moveColumnFactor));
    }

    void moveRight(Coordinate coordinate) {
        int moveColumnFactor = belongsToPlayer1() ? 1 : -1;
        coordinate.updateColumnCoordinate(getStepsToMove(moveColumnFactor));
    }

    void moveFront(Coordinate coordinate) {
        int moveRowFactor = belongsToPlayer1() ? -1 : 1;
        coordinate.updateRowCoordinate(getStepsToMove(moveRowFactor));
    }

    void moveBack(Coordinate coordinate) {
        int moveRowFactor = belongsToPlayer1() ? 1 : -1;
        coordinate.updateRowCoordinate(getStepsToMove(moveRowFactor));
    }

    void moveFrontLeft(Coordinate coordinate) {
        int moveRowFactor = belongsToPlayer1() ? -1 : 1;
        int moveColumnFactor = belongsToPlayer1() ? -1 : 1;
        coordinate.updateCoordinate(getStepsToMove(moveRowFactor), getStepsToMove(moveColumnFactor));
    }

    void moveFrontRight(Coordinate coordinate) {
        int moveRowFactor = belongsToPlayer1() ? -1 : 1;
        int moveColumnFactor = belongsToPlayer1() ? 1 : -1;
        coordinate.updateCoordinate(getStepsToMove(moveRowFactor), getStepsToMove(moveColumnFactor));
    }

    void moveBackLeft(Coordinate coordinate) {
        int moveRowFactor = belongsToPlayer1() ? 1 : -1;
        int moveColumnFactor = belongsToPlayer1() ? -1 : 1;
        coordinate.updateCoordinate(getStepsToMove(moveRowFactor), getStepsToMove(moveColumnFactor));
    }

    void moveBackRight(Coordinate coordinate) {
        int moveRowFactor = belongsToPlayer1() ? 1 : -1;
        int moveColumnFactor = belongsToPlayer1() ? 1 : -1;
        coordinate.updateCoordinate(getStepsToMove(moveRowFactor), getStepsToMove(moveColumnFactor));
    }

    private int getStepsToMove(int factor) {
        return factor * steps;
    }
}
