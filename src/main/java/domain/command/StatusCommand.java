package domain.command;

public class StatusCommand implements Command {
    public static StatusCommand STATUS_COMMAND = new StatusCommand();

    private StatusCommand() {
    }
}
