package engine;

import AI.Solver;
import data.StateData;
import pojo.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogicEngine {
    public static void move(Player player, Board board, Piece piece, int i, int j) {
        if (!isLegalMove(board, piece, i, j)){
            return;
        }
        Piece copy = new Piece(piece);
        if (board.cell[i][j].piece != null && player.getHand().contains(copy)) {
            board.cell[i][j].piece = copy;
            player.getHand().remove(copy);
        }

    }

    public static boolean isLegalMove(Board board, Piece piece, int i, int j) {
        return isMoveColumnLegal(board, piece, i, j) && isMoveRowLegal(board, piece, i, j) && isMoveRegionLegal(board, piece, i, j);
    }

    private static boolean isMoveRowLegal(Board board, Piece piece, int i, int j) {
        if (board.cell[i][j].piece != null) {
            return false;
        }

        for (int rowIndex = 0; rowIndex < board.size; rowIndex++) {
            if (board.cell[i][rowIndex].piece.isEqualShape(piece) && !board.cell[i][rowIndex].piece.isEqualColor(piece)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isMoveColumnLegal(Board board, Piece piece, int i, int j) {
        if (board.cell[i][j].piece != null) {
            return false;
        }

        for (int colIndex = 0; colIndex < board.size; colIndex++) {
            if (board.cell[colIndex][j].piece.isEqualShape(piece) && !board.cell[colIndex][j].piece.isEqualColor(piece)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isMoveRegionLegal(Board board, Piece piece, int i, int j){
        for (int k = 0; k < board.regions.size(); k++){
            for (Cell c : board.regions.get(k).getCellList()){
                if (c.piece.isEqualShape(piece) && !c.piece.isEqualColor(piece))    {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isEnd(Board board, boolean isWhiteTurn) {
        if (Solver.getNextStates(new StateData(board, isWhiteTurn, false)).size() == 0){
            return true;
        }
        for (int i = 0; i < board.size; i++){
            if (isEndByRow(board, i)){
                return true;
            }
        }
        for (int j = 0; j < board.size; j++){
            if (isEndByCol(board, j)){
                return true;
            }
        }
        return isEndByRegion(board);
    }

    private static boolean isEndByRow(Board board, int i) {
        Set<Piece> pieceSet = new HashSet<>();
        for (int j = 0; j < board.size; j++){
            if (board.cell[i][j].piece == null){
                return false;
            }else{
                pieceSet.add(board.cell[i][j].piece);
            }
        }

        return pieceSet.size() == 4;
    }

    private static boolean isEndByCol(Board board, int j){
        Set<Piece> pieceSet = new HashSet<>();
        for (int i = 0; i < board.size; i++){
            if (board.cell[i][j].piece == null){
                return false;
            }else {
                pieceSet.add(board.cell[i][j].piece);

            }
        }
        return pieceSet.size() == 4;
    }

    private static boolean isEndByRegion(Board board){

        for (Region region : board.regions){
            Set<Piece> pieceSet = new HashSet<>();
            for (Cell c : region.getCellList()){
                if (c.piece == null){
                    return false;
                }else{
                    pieceSet.add(c.piece);
                }
            }
            if (pieceSet.size() != 4){
                return false;
            }
        }
        return true;
    }

}