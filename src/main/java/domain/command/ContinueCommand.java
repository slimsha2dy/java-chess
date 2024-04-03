package domain.command;

public class ContinueCommand implements Command {
    public static ContinueCommand CONTINUE_COMMAND = new ContinueCommand();
    public static String INPUT_CONTINUE = "continue";

    private ContinueCommand() {
    }
}
