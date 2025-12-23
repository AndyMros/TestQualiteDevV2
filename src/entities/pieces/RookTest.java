package entities.pieces;

import entities.Color;
import entities.Square;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RookTest {

    @Test
    public void testRookCreation() {
        Rook r = new Rook(Color.BLACK, new Square(0, 0));
        assertEquals(Color.BLACK, r.getColor());
        assertEquals(new Square(0, 0), r.getPosition());
    }

    @Test
    public void testCanMoveTo() {
        Rook r = new Rook(Color.BLACK, new Square(0, 0));

        // Ne peut pas bouger sur place
        assertFalse(r.canMoveTo(new Square(0, 0)));

        // Peut bouger sur la même ligne ou colonne
        assertTrue(r.canMoveTo(new Square(0, 5)));
        assertTrue(r.canMoveTo(new Square(5, 0)));

        // Ne peut pas bouger en diagonale
        assertFalse(r.canMoveTo(new Square(2, 2)));
    }

    @Test
    public void testSetPosition() {
        Rook r = new Rook(Color.BLACK, new Square(0, 0));
        r.setPosition(new Square(5, 5));
        assertEquals(new Square(5, 5), r.getPosition());
    }

    @Test
    public void testPossibleMoveFromCorner() {
        Rook r = new Rook(Color.BLACK, new Square(0, 0));
        List<Square> l = r.possibleMoveFrom();

        // Une tour a toujours 14 coups possibles sur un plateau vide (7 horizontaux + 7 verticaux)
        assertEquals(14, l.size());

        assertFalse(l.contains(new Square(0, 0))); // Pas sur place
        assertFalse(l.contains(new Square(5, 1))); // Pas une ligne/colonne valide

        assertTrue(l.contains(new Square(0, 6))); // Même ligne
        assertTrue(l.contains(new Square(6, 0))); // Même colonne
    }

    @Test
    public void testPossibleMoveMiddle() {
        Rook r = new Rook(Color.BLACK, new Square(4, 4));
        List<Square> l = r.possibleMoveFrom();

        assertEquals(14, l.size());
        assertFalse(l.contains(new Square(4, 4)));

        assertTrue(l.contains(new Square(4, 6)));
        assertTrue(l.contains(new Square(6, 4)));
        assertFalse(l.contains(new Square(5, 1)));
    }

    @Test
    public void testPossibleMoveBorder() {
        Rook r = new Rook(Color.BLACK, new Square(0, 4));
        List<Square> l = r.possibleMoveFrom();

        assertEquals(14, l.size());
        assertFalse(l.contains(new Square(0, 4)));

        assertTrue(l.contains(new Square(0, 6)));
        assertTrue(l.contains(new Square(6, 4)));
    }
}