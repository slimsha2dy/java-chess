package controller;

import static domain.GameStatus.END;
import static domain.GameStatus.RETRY;
import static domain.Team.BLACK;
import static domain.Team.WHITE;
import static domain.command.ContinueCommand.CONTINUE_COMMAND;
import static domain.command.EndCommand.END_COMMAND;
import static domain.command.StartCommand.START_COMMAND;
import static domain.command.StatusCommand.STATUS_COMMAND;

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

public class ChessController {
    private static final MoveDao MOVE_DAO = new MoveDaoImpl();

    public void run() {
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

    private boolean isEndCommand(Command command) {
        return command.equals(END_COMMAND);
    }

    private ChessGame initChessGame(Command command) {
        if (isStartCommand(command)) {
            MOVE_DAO.deleteAll();
        }
        ChessGame chessGame = new ChessGame();
        if (isContinueCommand(command)) {
            chessGame.loadMoves();
        }
        return chessGame;
    }

    private boolean isStartCommand(Command command) {
        return command.equals(START_COMMAND);
    }

    private boolean isContinueCommand(Command command) {
        return command.equals(CONTINUE_COMMAND);
    }

    private boolean repeatUntilEnd(ChessGame chessGame) {
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
        if (gameStatus.equals(END)) {
            OutputView.printEndGame(chessGame.getWinner());
            return false;
        }
        if (gameStatus.equals(RETRY)) {
            OutputView.printReInputGuide();
        }
        return true;
    }

    private List<PieceWrapper> wrapPieces(List<Piece> piecesOnBoard) {
        return piecesOnBoard.stream()
                .map(PieceWrapper::new)
                .collect(Collectors.toList());
    }

    private GameStatus playGame(Command command, ChessGame chessGame) {
        MoveCommand moveCommand = (MoveCommand) command;
        Position from = moveCommand.getFrom();
        Position to = moveCommand.getTo();
        GameStatus gameStatus = chessGame.move(from, to);
        List<PieceWrapper> piecesOnBoard = wrapPieces(chessGame.getPiecesOnBoard());
        OutputView.printChessBoard(piecesOnBoard);
        return gameStatus;
    }
}
