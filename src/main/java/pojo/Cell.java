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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;

        Cell cell = (Cell) o;

        return piece != null ? piece.equals(cell.piece) : cell.piece == null;
    }

    @Override
    public int hashCode() {
        return piece != null ? piece.hashCode() : 0;
    }
}
