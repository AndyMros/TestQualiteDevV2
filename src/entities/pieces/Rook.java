package entities.pieces;

import entities.Color;
import entities.IPiece;
import entities.Square;
import java.util.ArrayList;
import java.util.List;

public class Rook implements IPiece {
    private final Color color;
    private Square position;

    public Rook(Color color, Square position) {
        this.color = color;
        this.position = position;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Square getPosition() {
        return position;
    }

    @Override
    public void setPosition(Square target) {
        this.position = target;
    }

    @Override
    public boolean canMoveTo(Square target) {
        // Une tour bouge sur la même ligne ou la même colonne
        boolean sameRow = target.getRow() == position.getRow();
        boolean sameCol = target.getCol() == position.getCol();
        boolean isSameSquare = sameRow && sameCol;

        // Doit être sur la même ligne ou colonne, mais pas la même case
        return (sameRow || sameCol) && !isSameSquare;
    }

    @Override
    public char getSymbol() {
        return color == Color.WHITE ? 'R' : 'r';
    }

    /**
     * Retourne la liste de tous les déplacements théoriques possibles
     * pour une tour sur un échiquier vide (nécessaire pour RookTest).
     *
     * @return Une liste de 14 cases (7 sur la ligne, 7 sur la colonne)
     */
    public List<Square> possibleMoveFrom() {
        List<Square> moves = new ArrayList<>();
        int currentRow = position.getRow();
        int currentCol = position.getCol();

        for (int i = 0; i < 8; i++) {
            // Mouvements horizontaux (on parcourt les colonnes)
            if (i != currentCol) {
                moves.add(new Square(currentRow, i));
            }
            // Mouvements verticaux (on parcourt les lignes)
            if (i != currentRow) {
                moves.add(new Square(i, currentCol));
            }
        }
        return moves;
    }
}