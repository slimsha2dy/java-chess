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
        while (!CommandMapper.isStartCommand(input) && !CommandMapper.isEndCommand(input)
                && !CommandMapper.isContinueCommand(input)) {
            System.out.println("다시 입력해 주세요");
            input = scanner.nextLine();
        }
        return CommandMapper.getCommandByInput(input);
    }


    public static Command readWhilePlaying() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!MOVE_FORM.matcher(input).matches() && !CommandMapper.isEndCommand(input)
                && !CommandMapper.isStatusCommand(input)) {
            System.out.println("다시 입력해 주세요");
            input = scanner.nextLine();
        }
        if (CommandMapper.isEndCommand(input) || CommandMapper.isStatusCommand(input)) {
            return CommandMapper.getCommandByInput(input);
        }
        String options = input.substring(OPTION_BEGIN_INDEX);
        String[] splitOptions = options.split(SEPARATOR);
        return new MoveCommand(splitOptions[0], splitOptions[1]);
    }

    enum CommandMapper {
        CONTINUE_COMMAND("continue", ContinueCommand.CONTINUE_COMMAND),
        END_COMMAND("end", EndCommand.END_COMMAND),
        START_COMMAND("start", StartCommand.START_COMMAND),
        STATUS_COMMAND("status", StatusCommand.STATUS_COMMAND);

        private final String input;
        private final Command command;

        CommandMapper(String input, Command command) {
            this.input = input;
            this.command = command;
        }

        public static boolean isStartCommand(String input) {
            return input.equals(START_COMMAND.input);
        }

        public static boolean isEndCommand(String input) {
            return input.equals(END_COMMAND.input);
        }

        public static boolean isContinueCommand(String input) {
            return input.equals(CONTINUE_COMMAND.input);
        }


        public static boolean isStatusCommand(String input) {
            return input.equals(STATUS_COMMAND.input);
        }

        public static Command getCommandByInput(String inputCommand) {
            for (CommandMapper commandMapper : values()) {
                if (commandMapper.input.equals(inputCommand)) {
                    return commandMapper.command;
                }
            }
            throw new IllegalArgumentException("해당하는 명령어가 존재하지 않습니다.");
        }
    }
}
