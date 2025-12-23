package entities;

import entities.pieces.King;
import entities.pieces.Rook;
import useCases.MoveValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;
    private MoveValidator validator;

    @BeforeEach
    public void setUp() {
        board = new Board();
        validator = new MoveValidator();
    }

    @Test
    public void testKingValidMove() {
        King king = new King(Color.WHITE, new Square(4, 4));
        board.addPiece(king);

        // Diagonale 1 case
        Move move = new Move(new Square(4, 4), new Square(5, 5));
        assertTrue(validator.isValid(board, move), "Le Roi devrait pouvoir se déplacer d'une case en diagonale");

        // Vertical 1 case
        move = new Move(new Square(4, 4), new Square(4, 5));
        assertTrue(validator.isValid(board, move), "Le Roi devrait pouvoir se déplacer d'une case verticalement");

        // Trop loin (2 cases)
        move = new Move(new Square(4, 4), new Square(6, 6));
        assertFalse(validator.isValid(board, move), "Le Roi ne peut pas se déplacer de deux cases");
    }

    @Test
    public void testRookValidMove() {
        Rook rook = new Rook(Color.WHITE, new Square(4, 4));
        board.addPiece(rook);

        // Interdit : Diagonale
        Move move = new Move(new Square(4, 4), new Square(5, 5));
        assertFalse(validator.isValid(board, move), "La tour ne peut pas se déplacer en diagonale");

        // Vertical
        move = new Move(new Square(4, 4), new Square(4, 5));
        assertTrue(validator.isValid(board, move), "La tour peut se déplacer d'une case verticalement");

        // Longue distance
        move = new Move(new Square(4, 4), new Square(4, 7));
        assertTrue(validator.isValid(board, move), "La tour peut se déplacer de plusieurs cases");
    }

    @Test
    public void testPathBlocked() {
        // FEN : Roi Blanc e8, tour noire c4, tour blanche c2, roi noir h1
        String fen = "4K3/8/8/8/2r5/8/2R5/7k";
        board.FENtoMAP(fen);

        // De c7 (6,2) vers c1 (0,2). La tour noire est en c4 (3,2)
        Square from = new Square(6, 2);
        Square to = new Square(0, 2);

        // Vérification de la logique de collision du plateau
        assertTrue(board.pathIsBlocked(from, to), "Le chemin doit être bloqué par la tour noire en c4");

        // Chemin court sans obstacle
        Square from2 = new Square(6, 2);
        Square to2 = new Square(5, 2);
        assertFalse(board.pathIsBlocked(from2, to2), "Le chemin ne devrait pas être bloqué");
    }

    @Test
    public void testKingProximity() {
        // Deux rois face à face : Blanc en d6 (5,3) et Noir en d4 (3,3)
        // Le noir essaie d'aller en d5 (4,3), ce qui est trop proche du roi blanc
        String fen = "8/8/3K4/8/3k4/8/8/8";
        board.FENtoMAP(fen);

        Square blackKingPos = new Square(3, 3);

        // d5 (case interdite car adjacente au Roi blanc en d6)
        Move moveIllegal = new Move(blackKingPos, new Square(4, 3));
        assertFalse(validator.isValid(board, moveIllegal), "Un roi ne peut pas s'approcher à 1 case d'un autre roi");

        // d3 (case autorisée)
        Move moveLegal = new Move(blackKingPos, new Square(2, 3));
        assertTrue(validator.isValid(board, moveLegal), "Le roi noir peut s'éloigner");
    }

    @Test
    public void testFENTOMAP() {
        // FEN : Roi noir d8, Tour blanche a3, Roi blanc d1
        String fen = "3k4/8/8/8/8/R7/8/3K4";
        board.FENtoMAP(fen);

        // Roi blanc en d1 -> row 0, col 3
        IPiece whiteKing = board.getPieceAt(new Square(0, 3));
        assertNotNull(whiteKing);
        assertTrue(whiteKing instanceof King);
        assertEquals(Color.WHITE, whiteKing.getColor());

        // Roi noir en d8 -> row 7, col 3
        IPiece blackKing = board.getPieceAt(new Square(7, 3));
        assertNotNull(blackKing);
        assertTrue(blackKing instanceof King);
        assertEquals(Color.BLACK, blackKing.getColor());

        // Tour blanche en a3 -> row 2, col 0
        IPiece whiteRook = board.getPieceAt(new Square(2, 0));
        assertNotNull(whiteRook);
        assertTrue(whiteRook instanceof Rook);
        assertEquals(Color.WHITE, whiteRook.getColor());
    }

    @Test
    public void testCaptureEnnemi() {
        // Roi Blanc en d1, Tour Noire en d2
        board.addPiece(new King(Color.WHITE, new Square(0, 3)));
        board.addPiece(new Rook(Color.BLACK, new Square(1, 3)));

        // Le roi blanc mange la tour noire
        Move capture = new Move(new Square(0, 3), new Square(1, 3));
        assertTrue(validator.isValid(board, capture), "Le roi devrait pouvoir capturer une pièce ennemie");
    }

    @Test
    public void testAllieBloque() {
        // Roi Blanc en d1, Tour Blanche en d2
        board.addPiece(new King(Color.WHITE, new Square(0, 3)));
        board.addPiece(new Rook(Color.WHITE, new Square(1, 3)));

        // Le roi blanc veut aller sur la case de sa propre tour
        Move moveSurAllie = new Move(new Square(0, 3), new Square(1, 3));
        assertFalse(validator.isValid(board, moveSurAllie), "On ne peut pas capturer sa propre pièce");
    }
}