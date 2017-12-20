package firzen.yogesh.minichess.playingfield;

public class CommandParser {

    public static String getPieceName(String command) {
        return command.substring(0, command.indexOf(':'));
    }

    public static String getMoveCommand(String command) {
        return command.substring(command.indexOf(":") + 1);
    }
}
