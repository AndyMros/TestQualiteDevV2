package entities.pieces;

import entities.Color;
import entities.IPiece;
import entities.Square;
import java.util.ArrayList;
import java.util.List;

public class King implements IPiece {
    private final Color color;
    private Square position;

    public King(Color color, Square position) {
        this.color = color;
        this.position = position;
    }

    @Override public Color getColor() { return color; }
    @Override public Square getPosition() { return position; }
    @Override public void setPosition(Square target) { this.position = target; }

    @Override
    public boolean canMoveTo(Square target) {
        int rowDiff = Math.abs(target.getRow() - position.getRow());
        int colDiff = Math.abs(target.getCol() - position.getCol());
        // 1 case max dans n'importe quelle direction, mais pas sur place
        return (rowDiff <= 1 && colDiff <= 1) && !(rowDiff == 0 && colDiff == 0);
    }

    @Override
    public char getSymbol() {
        return color == Color.WHITE ? 'K' : 'k';
    }

    /**
     * CETTE FONCTION EST ICI :
     * Elle génère la liste de toutes les cases adjacentes valides.
     */
    public List<Square> possibleMoveFrom() {
        List<Square> moves = new ArrayList<>();
        int currentRow = position.getRow();
        int currentCol = position.getCol();

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue; // Ignorer la case actuelle

                Square s = new Square(currentRow + dr, currentCol + dc);
                if (s.isValid()) { // Vérifie si la case est bien dans le plateau (0-7)
                    moves.add(s);
                }
            }
        }
        return moves;
    }
}