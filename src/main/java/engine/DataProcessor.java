package engine;

import pojo.Board;

public class DataProcessor {
    public static String boardToNotation(Board board){
        StringBuilder notation = new StringBuilder();
        for (int i = 0; i < board.cell.length; i++) {
            int emptySpot = 0;
            for (int j = 0; j < board.cell[i].length; j++){
                if (board.cell[i][j].piece != null){
                    if (emptySpot != 0){
                        notation.append(emptySpot);
                    }
                    emptySpot = 0;
                    notation.append(board.cell[i][j].piece.symbol);
                }else{
                    emptySpot += 1;
                }
            }
            if (emptySpot != 0){
                notation.append(emptySpot);
            }
            notation.append("/");
        }
        notation.deleteCharAt(notation.length() - 1);
        return notation.toString();
    }
}
