package domain;

import domain.piece.PieceType;
import domain.piece.PieceWrapper;
import java.util.ArrayList;
import java.util.List;

public class ScoreCalculator {
    private final List<PieceWrapper> pieceWrappers;

    public ScoreCalculator(List<PieceWrapper> pieceWrappers) {
        this.pieceWrappers = new ArrayList<>(pieceWrappers);
    }

    public double getScore(Team team) {
        List<PieceWrapper> piecesOfTeam = pieceWrappers.stream()
                .filter(pieceWrapper -> pieceWrapper.getTeam().equals(team))
                .toList();

        return simpleTotal(piecesOfTeam) - deductionBySameRowPawn(piecesOfTeam);
    }

    private double simpleTotal(List<PieceWrapper> piecesOfTeam) {
        return piecesOfTeam.stream()
                .map(PieceWrapper::getPieceType)
                .map(PieceType::getScore)
                .reduce(Double::sum)
                .orElseThrow(() -> new IllegalArgumentException("점수를 계산할 수 있는 기물이 존재하지 않습니다."));
    }

    private double deductionBySameRowPawn(List<PieceWrapper> piecesOfTeam) {
        List<Integer> countPawnOfRow = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0));
        piecesOfTeam.stream()
                .filter(pieceWrapper -> pieceWrapper.getPieceType().equals(PieceType.PAWN))
                .map(PieceWrapper::getColumn)
                .forEach(rowIndex -> countPawnOfRow.set(rowIndex, countPawnOfRow.get(rowIndex) + 1));
        return countPawnOfRow.stream()
                .filter(number -> number > 1)
                .reduce(0, Integer::sum) * 0.5;
    }
}
