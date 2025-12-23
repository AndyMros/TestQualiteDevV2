package entities.pieces;

import entities.Color;
import entities.Square;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class KingTest {

    @Test
    public void testKingCreation() {
        King k = new King(Color.WHITE, new Square(4, 4));
        assertEquals(Color.WHITE, k.getColor());
        assertEquals(new Square(4, 4), k.getPosition());
    }

    @Test
    public void testCanMoveTo() {
        King k = new King(Color.WHITE, new Square(4, 4));

        // Diagonale
        assertTrue(k.canMoveTo(new Square(5, 5)));
        // Sur place (interdit)
        assertFalse(k.canMoveTo(new Square(4, 4)));
        // Trop loin (2 cases)
        assertFalse(k.canMoveTo(new Square(6, 4)));
    }

    @Test
    public void testSetPosition() {
        King k = new King(Color.WHITE, new Square(4, 4));
        k.setPosition(new Square(2, 2));
        assertEquals(new Square(2, 2), k.getPosition());
    }

    @Test
    public void testPossibleMoveCorner() {
        // Coin inférieur gauche (0,0)
        King k = new King(Color.WHITE, new Square(0, 0));
        List<Square> moves = k.possibleMoveFrom();

        // Dans un coin, le roi a 3 mouvements possibles
        assertEquals(3, moves.size());
        assertFalse(moves.contains(new Square(0, 0)));
        assertTrue(moves.contains(new Square(0, 1)));
        assertTrue(moves.contains(new Square(1, 0)));
        assertTrue(moves.contains(new Square(1, 1)));
    }

    @Test
    public void testPossibleMoveMiddle() {
        King k = new King(Color.WHITE, new Square(4, 4));
        List<Square> moves = k.possibleMoveFrom();

        // Au milieu, le roi a 8 mouvements possibles
        assertEquals(8, moves.size());
        assertFalse(moves.contains(new Square(4, 4)));

        assertTrue(moves.contains(new Square(3, 4)));
        assertTrue(moves.contains(new Square(5, 4)));
        assertTrue(moves.contains(new Square(4, 3)));
        assertTrue(moves.contains(new Square(4, 5)));
        assertTrue(moves.contains(new Square(3, 3)));
        assertTrue(moves.contains(new Square(3, 5)));
        assertTrue(moves.contains(new Square(5, 3)));
        assertTrue(moves.contains(new Square(5, 5)));
    }

    @Test
    public void testPossibleMoveEdge() {
        // Sur un bord (ligne 0, colonne 4)
        King k = new King(Color.WHITE, new Square(0, 4));
        List<Square> moves = k.possibleMoveFrom();

        // Sur un bord, le roi a 5 mouvements possibles
        assertEquals(5, moves.size());
        assertFalse(moves.contains(new Square(0, 4)));

        assertTrue(moves.contains(new Square(0, 5)));
        assertTrue(moves.contains(new Square(0, 3)));
        assertTrue(moves.contains(new Square(1, 4)));
        assertTrue(moves.contains(new Square(1, 3)));
        assertTrue(moves.contains(new Square(1, 5)));
    }
}