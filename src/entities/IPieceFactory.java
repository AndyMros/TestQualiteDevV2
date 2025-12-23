package entities;

public interface IPieceFactory {
    /**
     * Crée une pièce d'échecs à partir d'un caractère FEN.
     * <ul>
     *     <li>Les lettres majuscules représentent les pièces blanches</li>
     *     <li>Les lettres minuscules représentent les pièces noires</li>
     * </ul>
     *
     * @param fenChar le caractère FEN représentant la pièce
     * @param position la position initiale de la pièce sur le plateau
     * @return une instance de {@link IPiece} correspondant au caractère FEN
     * @throws IllegalArgumentException si le caractère FEN ne correspond
     *         à aucune pièce connue
     */
    IPiece createPiece(char fenChar, Square position);
}