import static domain.GameStatus.END;
import static domain.GameStatus.PROGRESS;
import static domain.GameStatus.RETRY;
import static domain.Team.BLACK;
import static domain.Team.WHITE;
import static domain.command.ContinueCommand.CONTINUE_COMMAND;
import static domain.command.EndCommand.END_COMMAND;
import static domain.command.StartCommand.START_COMMAND;
import static domain.command.StatusCommand.STATUS_COMMAND;

import dao.DBConnector;
import dao.MoveDao;
import dao.MoveDaoImpl;
import domain.ChessGame;
import domain.GameStatus;
import domain.Position;
import domain.command.Command;
import domain.command.MoveCommand;
import domain.piece.Piece;
import domain.piece.PieceWrapper;
import java.util.List;
import java.util.stream.Collectors;
import view.InputView;
import view.OutputView;

public class Application {
    private static final MoveDao MOVE_DAO = new MoveDaoImpl();
    private static final DBConnector DB_CONNECTOR = new DBConnector();

    public static void main(String[] args) {
        OutputView.printGuide();
        Command firstCommand = InputView.readStartPhase();
        if (isEndCommand(firstCommand)) {
            return;
        }
        ChessGame chessGame = initChessGame(firstCommand);
        List<PieceWrapper> piecesOnBoard = wrapPieces(chessGame.getPiecesOnBoard());
        OutputView.printChessBoard(piecesOnBoard);

        while (repeatUntilEnd(chessGame)) {
        }
    }

    private static boolean isEndCommand(Command command) {
        return command.equals(END_COMMAND);
    }

    private static ChessGame initChessGame(Command command) {
        if (isStartCommand(command)) {
            MOVE_DAO.deleteAll(DB_CONNECTOR.getConnection());
        }
        ChessGame chessGame = new ChessGame();
        if (isContinueCommand(command)) {
            List<MoveCommand> moveCommands = MOVE_DAO.findAllMoves(DB_CONNECTOR.getConnection());
            chessGame.moveNotations(moveCommands);
        }
        return chessGame;
    }

    private static boolean isStartCommand(Command command) {
        return command.equals(START_COMMAND);
    }

    private static boolean isContinueCommand(Command command) {
        return command.equals(CONTINUE_COMMAND);
    }

    private static boolean repeatUntilEnd(ChessGame chessGame) {
        Command command = InputView.readWhilePlaying();
        if (command.equals(STATUS_COMMAND)) {
            OutputView.printScore(
                    chessGame.getTeamScore(WHITE),
                    chessGame.getTeamScore(BLACK),
                    chessGame.getHigher()
            );
            return true;
        }
        if (command.equals(END_COMMAND)) {
            return false;
        }
        GameStatus gameStatus = playGame(command, chessGame);
        if (gameStatus.equals(PROGRESS)) {
            MOVE_DAO.add(DB_CONNECTOR.getConnection(), (MoveCommand) command);
        }
        if (gameStatus.equals(END)) {
            OutputView.printEndGame(chessGame.getWinner());
            MOVE_DAO.deleteAll(DB_CONNECTOR.getConnection());
            return false;
        }
        if (gameStatus.equals(RETRY)) {
            OutputView.printReInputGuide();
        }
        return true;
    }

    private static List<PieceWrapper> wrapPieces(List<Piece> piecesOnBoard) {
        return piecesOnBoard.stream()
                .map(PieceWrapper::new)
                .collect(Collectors.toList());
    }

    private static GameStatus playGame(Command command, ChessGame chessGame) {
        MoveCommand moveCommand = (MoveCommand) command;
        Position from = moveCommand.getFrom();
        Position to = moveCommand.getTo();
        GameStatus gameStatus = chessGame.move(from, to);
        List<PieceWrapper> piecesOnBoard = wrapPieces(chessGame.getPiecesOnBoard());
        OutputView.printChessBoard(piecesOnBoard);
        return gameStatus;
    }
}
