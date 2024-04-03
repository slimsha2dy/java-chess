package domain.command;

public class EndCommand implements Command {
    public static EndCommand END_COMMAND = new EndCommand();
    public static String INPUT_END = "end";

    private EndCommand() {
    }
}
