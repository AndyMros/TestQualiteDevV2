package entities;

import entities.pieces.King;
import entities.pieces.Rook;

public class PieceFactory implements IPieceFactory {
    @Override
    public IPiece createPiece(char type, Square position) {
        return switch (type) {
            case 'K' -> new King(Color.WHITE, position);
            case 'k' -> new King(Color.BLACK, position);
            case 'R' -> new Rook(Color.WHITE, position);
            case 'r' -> new Rook(Color.BLACK, position); // Optionnel si tu n'as qu'une tour blanche
            default -> throw new IllegalArgumentException("Pièce inconnue : " + type);
        };
    }
}