package pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    public Cell[][] cell;
    public final int size = 4;
    public List<Region> regions;

    public Board(){
        initBoard();
    }

    public Board(Board copy){
        this.cell = new Cell[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                this.cell[i][j] = new Cell(copy.cell[i][j]);
            }
        }
        this.regions = new ArrayList<>();
        for (Region r : copy.regions){
            this.regions.add(new Region(r));
        }
    }

    public void initBoard(){
        this.cell = new Cell[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                cell[i][j] = new Cell();
            }
        }
        this.regions = new ArrayList<>();
        this.regions.add(new Region(new ArrayList<>(Arrays.asList(cell[0][0],cell[0][1],cell[1][0],cell[1][1]))));
        this.regions.add(new Region(new ArrayList<>(Arrays.asList(cell[0][2],cell[0][3],cell[1][2],cell[1][3]))));
        this.regions.add(new Region(new ArrayList<>(Arrays.asList(cell[2][0],cell[2][1],cell[3][0],cell[3][1]))));
        this.regions.add(new Region(new ArrayList<>(Arrays.asList(cell[2][2],cell[2][3],cell[3][2],cell[3][3]))));
//        00 01 02 03
//        10 11 12 13
//        20 21 22 23
//        30 31 32 33
    }

    public void print(){
        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell[i].length; j++) {
                System.out.print(cell[i][j]);
            }
            System.out.println();
        }
    }
}
