package domain.command;

public class StartCommand implements Command {
    public static StartCommand START_COMMAND = new StartCommand();
    public static String INPUT_START = "start";

    private StartCommand() {
    }
}
