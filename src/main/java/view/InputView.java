package view;

import domain.command.Command;
import domain.command.ContinueCommand;
import domain.command.EndCommand;
import domain.command.MoveCommand;
import domain.command.StartCommand;
import domain.command.StatusCommand;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputView {

    private static final Pattern MOVE_FORM = Pattern.compile("move [a-h][1-8] [a-h][1-8]");
    private static final int OPTION_BEGIN_INDEX = 5;
    private static final String SEPARATOR = " ";

    public static Command readStartPhase() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!isStartCommand(input) && !isEndCommand(input) && !isContinueCommand(input)) {
            System.out.println("다시 입력해 주세요");
            input = scanner.nextLine();
        }
        if (isEndCommand(input)) {
            return EndCommand.END_COMMAND;
        }
        if (isContinueCommand(input)) {
            return ContinueCommand.CONTINUE_COMMAND;
        }
        return StartCommand.START_COMMAND;
    }

    private static boolean isStartCommand(String input) {
        return input.equals("start");
    }

    private static boolean isEndCommand(String input) {
        return input.equals("end");
    }

    private static boolean isContinueCommand(String input) {
        return input.equals("continue");
    }

    public static Command readWhilePlaying() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!MOVE_FORM.matcher(input).matches() && !isEndCommand(input) && !isStatusCommand(input)) {
            System.out.println("다시 입력해 주세요");
            input = scanner.nextLine();
        }
        if (isEndCommand(input)) {
            return EndCommand.END_COMMAND;
        }
        if (isStatusCommand(input)) {
            return StatusCommand.STATUS_COMMAND;
        }
        String options = input.substring(OPTION_BEGIN_INDEX);
        String[] splitOptions = options.split(SEPARATOR);
        return new MoveCommand(splitOptions[0], splitOptions[1]);
    }

    private static boolean isStatusCommand(String input) {
        return input.equals("status");
    }
}
