package entities;

public interface IPiece {
    Color getColor();
    Square getPosition();
    void setPosition(Square target);
    boolean canMoveTo(Square target);
    char getSymbol(); // 'K', 'k', 'R', etc.
}