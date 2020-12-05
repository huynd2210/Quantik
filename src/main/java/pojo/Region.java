package pojo;

import java.util.ArrayList;
import java.util.List;


public class Region {
    private List<Cell> cellList;

    public Region(List<Cell> cellList){
        this.cellList = cellList;
    }

    public Region(){
        this.cellList = new ArrayList<>();
    }

    public Region(Region copy){
        this.cellList = new ArrayList<>();
        for (Cell c : copy.getCellList()){
            this.cellList.add(new Cell(c));
        }
    }

    public void addCell(Cell cell){
        this.cellList.add(cell);
    }

    public List<Cell> getCellList(){
        return this.cellList;
    }
}
