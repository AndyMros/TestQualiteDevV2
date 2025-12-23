package entities;

import java.util.*;

public class Board {
    private final Map<Square, IPiece> grid = new HashMap<>();
    private final IPieceFactory factory = new PieceFactory();

    public void addPiece(IPiece piece) {
        grid.put(piece.getPosition(), piece);
    }

    public IPiece getPieceAt(Square s) {
        return grid.get(s);
    }

    public Collection<IPiece> getAllPieces() {
        return grid.values();
    }

    public void movePiece(Move move) {
        IPiece piece = grid.remove(move.getStart());
        if (piece != null) {
            grid.remove(move.getEnd()); // Capture éventuelle
            piece.setPosition(move.getEnd());
            grid.put(move.getEnd(), piece);
        }
    }

    public boolean pathIsBlocked(Square from, Square to) {
        int rowStep = Integer.compare(to.getRow(), from.getRow());
        int colStep = Integer.compare(to.getCol(), from.getCol());
        int currRow = from.getRow() + rowStep;
        int currCol = from.getCol() + colStep;

        while (currRow != to.getRow() || currCol != to.getCol()) {
            if (grid.containsKey(new Square(currRow, currCol))) return true;
            currRow += rowStep;
            currCol += colStep;
        }
        return false;
    }

    public void FENtoMAP(String fen) {
        grid.clear();
        String placement = fen.split(" ")[0];
        String[] ranks = placement.split("/");
        for (int i = 0; i < 8; i++) {
            int row = 7 - i;
            int col = 0;
            for (char c : ranks[i].toCharArray()) {
                if (Character.isDigit(c)) {
                    col += (c - '0');
                } else {
                    Square s = new Square(row, col);
                    addPiece(factory.createPiece(c, s));
                    col++;
                }
            }
        }
    }
}