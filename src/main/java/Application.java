import static domain.GameStatus.END;
import static domain.GameStatus.RETRY;
import static domain.command.EndCommand.END_COMMAND;

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
    public static void main(String[] args) {
        OutputView.printGuide();
        Command startOrEnd = InputView.readStartOrEnd();
        if (isEndCommand(startOrEnd)) {
            return;
        }
        ChessGame chessGame = new ChessGame();
        List<PieceWrapper> piecesOnBoard = wrapPieces(chessGame.getPiecesOnBoard());
        OutputView.printChessBoard(piecesOnBoard);

        Command endOrMove = InputView.readEndOrMove();
        while (!isEndCommand(endOrMove) && !isEndGame(endOrMove, chessGame)) {
            endOrMove = InputView.readEndOrMove();
        }
    }

    private static boolean isEndGame(Command endOrMove, ChessGame chessGame) {
        if (playGame(endOrMove, chessGame)) {
            OutputView.printEndGame(chessGame.getWinner());
            return true;
        }
        return false;
    }

    private static boolean isEndCommand(Command startOrEnd) {
        return startOrEnd.equals(END_COMMAND);
    }

    private static List<PieceWrapper> wrapPieces(List<Piece> piecesOnBoard) {
        return piecesOnBoard.stream()
                .map(PieceWrapper::new)
                .collect(Collectors.toList());
    }

    private static boolean playGame(Command command, ChessGame chessGame) {
        MoveCommand moveCommand = (MoveCommand) command;
        Position from = moveCommand.getFrom();
        Position to = moveCommand.getTo();
        GameStatus moveResult = chessGame.move(from, to);
        List<PieceWrapper> piecesOnBoard = wrapPieces(chessGame.getPiecesOnBoard());
        OutputView.printChessBoard(piecesOnBoard);
        if (moveResult.equals(END)) {
            return true;
        }
        if (moveResult.equals(RETRY)) {
            OutputView.printReInputGuide();
        }
        return false;
    }
}
