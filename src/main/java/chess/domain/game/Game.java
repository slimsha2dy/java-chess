package chess.domain.game;

import chess.domain.Player;
import chess.domain.Turn;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Pieces;
import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;

import java.util.*;

public class Game {
    private final Pieces pieces;
    private final Turn turn;

    public Game() {
        pieces = new Pieces();
        turn = new Turn();
        pieces.init();
    }

    public void move(final Position from, final Position to) {
        final Player player = turn.player();
        player.move(from, to, pieces);
        turn.next();
    }

    public boolean isNotEnd() {
        return pieces.toList()
                     .stream()
                     .filter(Piece::isKing)
                     .count() == 2;
    }

    public Player currentPlayer() {
        return turn.player();
    }

    public Pieces getPieces() {
        return pieces;
    }
}