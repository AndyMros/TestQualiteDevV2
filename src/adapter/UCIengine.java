package adapter;

import entities.*;
import useCases.MoveValidator;
import java.util.*;

public class UCIengine {
    private Board board = new Board();
    private MoveValidator validator = new MoveValidator();

    public static void main(String[] args) {
        new UCIengine().run();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.equals("uci")) {
                System.out.println("id name ChessEngineClean");
                System.out.println("id author User");
                System.out.println("uciok");
            } else if (line.equals("isready")) {
                System.out.println("readyok");
            } else if (line.startsWith("position")) {
                handlePosition(line);
            } else if (line.startsWith("go")) {
                handleGo();
            } else if (line.equals("quit")) {
                break;
            }
        }
    }

    private void handlePosition(String cmd) {
        if (cmd.contains("fen")) {
            String fen = cmd.split("fen ")[1].split(" moves")[0];
            board.FENtoMAP(fen);
        }
        if (cmd.contains("moves")) {
            String[] moves = cmd.split("moves ")[1].split(" ");
            for (String m : moves) {
                applyMove(m);
            }
        }
    }

    private void applyMove(String uci) {
        Square from = new Square(uci.charAt(1) - '1', uci.charAt(0) - 'a');
        Square to = new Square(uci.charAt(3) - '1', uci.charAt(2) - 'a');
        board.movePiece(new Move(from, to));
    }

    private void handleGo() {
        // Logique simplifiée : renvoie le premier coup valide trouvé
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                IPiece p = board.getPieceAt(new Square(r, c));
                if (p != null && p.getColor() == Color.WHITE) { // Supposons que l'IA est blanche
                    for (int tr = 0; tr < 8; tr++) {
                        for (int tc = 0; tc < 8; tc++) {
                            Move m = new Move(p.getPosition(), new Square(tr, tc));
                            if (validator.isValid(board, m)) {
                                System.out.println("bestmove " + m.toUCI());
                                return;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("bestmove 0000");
    }
}