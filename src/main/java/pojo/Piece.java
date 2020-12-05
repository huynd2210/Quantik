package pojo;

import enumeration.Shape;

public class Piece {
    public boolean isWhite;
    public Shape shape;

    public Piece(Shape shape, boolean isWhite){
        this.shape = shape;
        this.isWhite = isWhite;
    }

    public Piece(Piece piece){
        this.isWhite = piece.isWhite;
        this.shape = piece.shape;
    }

    public boolean isEqualShape(Piece other){
        return this.shape == other.shape;
    }

    public boolean isEqualColor(Piece piece){
        return this.isWhite == piece.isWhite;
    }
}
