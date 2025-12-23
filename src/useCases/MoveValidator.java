package useCases;

import entities.*;
import entities.pieces.King;
import entities.pieces.Rook;

public class MoveValidator {

    public boolean isValid(Board board, Move move) {
        IPiece piece = board.getPieceAt(move.getStart());
        if (piece == null) return false;
        if (!move.getEnd().isValid()) return false;

        // 1. Géométrie de la pièce
        if (!piece.canMoveTo(move.getEnd())) return false;

        // 2. Collision avec ses propres pièces
        IPiece target = board.getPieceAt(move.getEnd());
        if (target != null && target.getColor() == piece.getColor()) return false;

        // 3. Trajet bloqué (uniquement pour la tour)
        if (piece instanceof Rook) {
            if (board.pathIsBlocked(move.getStart(), move.getEnd())) return false;
        }

        // 4. Règle spéciale : Distance entre les deux Rois
        if (piece instanceof King) {
            if (isKingTooClose(board, move.getEnd(), piece.getColor())) return false;
        }

        return true;
    }

    private boolean isKingTooClose(Board board, Square targetSquare, Color movingColor) {
        for (IPiece p : board.getAllPieces()) {
            if (p instanceof King && p.getColor() != movingColor) {
                int rDiff = Math.abs(p.getPosition().getRow() - targetSquare.getRow());
                int cDiff = Math.abs(p.getPosition().getCol() - targetSquare.getCol());
                if (rDiff <= 1 && cDiff <= 1) return true;
            }
        }
        return false;
    }
}