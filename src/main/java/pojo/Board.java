package pojo;

import org.javatuples.Pair;

import java.util.*;

public class Board {
    public Cell[][] cell;
    public final int size = 4;
    public List<Region> regions;
    public List<Piece> piecePlacementHistory;

    public Board() {
        initBoard();
    }

    public Board(Board copy) {
        this.cell = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.cell[i][j] = new Cell(copy.cell[i][j]);
            }
        }
        this.regions = new ArrayList<>();
        for (Region r : copy.regions) {
            this.regions.add(new Region(r));
        }
        this.piecePlacementHistory = new ArrayList<>();
        for (Piece piece : copy.piecePlacementHistory) {
            this.piecePlacementHistory.add(new Piece(piece));
        }
    }

    public void initBoard() {
        this.piecePlacementHistory = new ArrayList<>();
        this.cell = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cell[i][j] = new Cell();
            }
        }
        this.regions = new ArrayList<>();
        this.regions.add(new Region(new ArrayList<>(Arrays.asList(cell[0][0], cell[0][1], cell[1][0], cell[1][1]))));
        this.regions.add(new Region(new ArrayList<>(Arrays.asList(cell[0][2], cell[0][3], cell[1][2], cell[1][3]))));
        this.regions.add(new Region(new ArrayList<>(Arrays.asList(cell[2][0], cell[2][1], cell[3][0], cell[3][1]))));
        this.regions.add(new Region(new ArrayList<>(Arrays.asList(cell[2][2], cell[2][3], cell[3][2], cell[3][3]))));
//        00 01 02 03
//        10 11 12 13
//        20 21 22 23
//        30 31 32 33
    }

    public void print() {
        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell[i].length; j++) {
                System.out.print(cell[i][j]);
            }
            System.out.println();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;

        Board board = (Board) o;

        return board.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int result = 0;
        Pair<Integer, List<Pair<Integer, Integer>>> firstRotation = boardHash(this.cell);
        result = 31 * result + firstRotation.getValue0();
        List<Pair<Integer, Integer>> indexList = new ArrayList<>(firstRotation.getValue1());
        for (int i = 1; i < 4; i++) {
            Cell[][] tmp = rotateMatrix(this.cell, i);
            Pair<Integer, List<Pair<Integer, Integer>>> otherRotation = boardHash(tmp);
            result = 31 * result + otherRotation.getValue0();
            indexList.addAll(otherRotation.getValue1());
        }

        Collections.sort(indexList);
        result += indexList.hashCode();

        result = 31 * result + placementHistoryHash();
        return result;
    }

    private int placementHistoryHash() {
        StringBuilder placementHistory = new StringBuilder();
        for (Piece piece : this.piecePlacementHistory) {
            placementHistory.append(piece.symbol);
        }
        int hash = 1;
        Map<Character, List<Integer>> indexMap = findAllCharacterIndex(placementHistory.toString());
        for (List<Integer> integers : indexMap.values()) {
            Collections.sort(integers);
            hash += integers.hashCode();
        }
        return hash;
    }

    private Map<Character, List<Integer>> findAllCharacterIndex(String string) {
        Set<Character> characterSet = new HashSet<>();
        for (Character c : string.toCharArray()) {
            characterSet.add(c);
        }
        Map<Character, List<Integer>> result = new HashMap<>();
        for (Character character : characterSet) {
            List<Integer> indexList = new ArrayList<>();
            int index = string.indexOf(character);
            while (index >= 0) {
                indexList.add(index);
                index = string.indexOf(character, index + 1);
            }
            result.put(character, indexList);
        }
//
        return result;
    }

    private Pair<Integer, List<Pair<Integer, Integer>>> boardHash(Cell[][] board) {
        int hash = 0;
        List<Pair<Integer, Integer>> indexList = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].piece != null) {
                    hash += board[i][j].hashCode();
                    indexList.add(new Pair<>(i + 1, j + 1));
                }
            }
        }
        return new Pair<>(hash, indexList);
    }

    private Cell[][] rotateMatrix(Cell[][] matrix, int numberOfRotation) {
        Cell[][] copy = new Cell[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                copy[i][j] = new Cell(matrix[i][j]);
            }
        }

        for (int k = 0; k < numberOfRotation; k++) {
            int row = copy.length;
            //first find the transpose of the matrix.
            for (int i = 0; i < row; i++) {
                for (int j = i; j < row; j++) {
                    Cell temp = new Cell(copy[i][j]);
                    copy[i][j] = new Cell(copy[j][i]);
                    copy[j][i] = new Cell(temp);
                }
            }
            //reverse each row
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < row / 2; j++) {
                    Cell temp = new Cell(copy[i][j]);
                    copy[i][j] = new Cell(copy[i][row - 1 - j]);
                    copy[i][row - 1 - j] = new Cell(temp);
                }
            }
        }
        return copy;
    }

    private int hashPair(Pair<Integer, Integer> pair) {
        int result = pair.getValue0();
        result = 31 * result + pair.getValue1();
        return result;
    }
}
