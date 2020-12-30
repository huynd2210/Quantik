package pojo;

import enumeration.Shape;

public class Piece {
    public boolean isWhite;
    public Shape shape;
    public char symbol;

    public Piece(Shape shape, boolean isWhite, char symbol){
        this.shape = shape;
        this.isWhite = isWhite;
        this.symbol = symbol;
    }

    public Piece(Piece piece){
        this.isWhite = piece.isWhite;
        this.shape = piece.shape;
        this.symbol = piece.symbol;
    }

    public boolean isEqualShape(Piece other){
        return this.shape == other.shape;
    }

    public boolean isEqualColor(Piece piece){
        return this.isWhite == piece.isWhite;
    }

    @Override
    public String toString(){
        return Character.toString(this.symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) return false;

        Piece piece = (Piece) o;

        return isWhite == piece.isWhite;
    }

    @Override
    public int hashCode() {
        return (isWhite ? 3 : 0);
    }

    public int hashShape(){
        return shape.hashCode();
    }
}
