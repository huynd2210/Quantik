package pojo;

public class Cell {
    public Piece piece;

    public Cell(){
        this.piece = null;
    }

    public Cell(Cell other){
        this.piece = new Piece(other.piece);
    }
}
