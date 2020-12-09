package pojo;

public class Cell {
    public Piece piece;

    public Cell(){
        this.piece = null;
    }

    public Cell(Cell other){
        if (other.piece == null) {
            this.piece = null;
        }else{
            this.piece = new Piece(other.piece);
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("[");
        if (this.piece == null){
            sb.append(" ");
        }else{
            sb.append(this.piece);
        }
        sb.append("]");
        return sb.toString();
    }
}
