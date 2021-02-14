package engine;

import data.PieceList;
import data.StateData;
import org.codehaus.plexus.util.StringUtils;
import pojo.Board;
import pojo.Piece;
import pojo.Player;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataProcessor {
    public static String boardToNotation(Board board) {
        StringBuilder notation = new StringBuilder();
        for (int i = 0; i < board.cell.length; i++) {
            int emptySpot = 0;
            for (int j = 0; j < board.cell[i].length; j++) {
                if (board.cell[i][j].piece != null) {
                    if (emptySpot != 0) {
                        notation.append(emptySpot);
                    }
                    emptySpot = 0;
                    notation.append(board.cell[i][j].piece.symbol);
                } else {
                    emptySpot += 1;
                }
            }
            if (emptySpot != 0) {
                notation.append(emptySpot);
            }
            notation.append("/");
        }
        notation.deleteCharAt(notation.length() - 1);
        return notation.toString();
    }

    public static StateData parseNotation(String stateNotation) {
        if (StringUtils.isBlank(stateNotation)){
            return null;
        }
        stateNotation = stateNotation.replace(" ", "");
        String[] tokens = stateNotation.split(";");
        StateData stateData = new StateData();
        stateData.setHash(Integer.parseInt(tokens[0]));
        stateData.setBoard(parseBoardFromNotation(tokens[1]));
        stateData.setWhitePlayer(initWhitePlayerFromBoard(stateData.getBoard()));
        stateData.setBlackPlayer(initBlackPlayerFromBoard(stateData.getBoard()));
        stateData.setWhiteTurn(Boolean.parseBoolean(tokens[2]));
        stateData.setEnd(Boolean.parseBoolean(tokens[3]));
        stateData.setWinner(tokens[4]);
        stateData.setStateValue(Double.parseDouble(tokens[5]));
        stateData.setChildrenHash(initHashFromString(tokens[6]));
        stateData.setParentHash(initHashFromString(tokens[7]));
        return stateData;
    }

    private static Board parseBoardFromNotation(String boardNotation) {
        String[] tokens = boardNotation.split("/");
        Board board = new Board();
        for (int i = 0; i < tokens.length; i++) {
            int emptySpot = 0;
            for (int j = 0; j < tokens[i].length(); j++) {
                if (tokens[i].charAt(j) == 'S') {
                    LogicEngine.editSetPiece(board, new Piece(PieceList.whiteSphere), i, j + emptySpot);
                } else if (tokens[i].charAt(j) == 's') {
                    LogicEngine.editSetPiece(board, new Piece(PieceList.blackSphere), i, j + emptySpot);
                } else if (tokens[i].charAt(j) == 'C') {
                    LogicEngine.editSetPiece(board, new Piece(PieceList.whiteCylinder), i, j + emptySpot);
                } else if (tokens[i].charAt(j) == 'c') {
                    LogicEngine.editSetPiece(board, new Piece(PieceList.blackCylinder), i, j + emptySpot);
                } else if (tokens[i].charAt(j) == 'P') {
                    LogicEngine.editSetPiece(board, new Piece(PieceList.whitePyramid), i, j + emptySpot);
                } else if (tokens[i].charAt(j) == 'p') {
                    LogicEngine.editSetPiece(board, new Piece(PieceList.blackPyramid), i, j + emptySpot);
                } else if (tokens[i].charAt(j) == 'B') {
                    LogicEngine.editSetPiece(board, new Piece(PieceList.whiteSquare), i, j + emptySpot);
                } else if (tokens[i].charAt(j) == 'b') {
                    LogicEngine.editSetPiece(board, new Piece(PieceList.blackSquare), i, j + emptySpot);
                } else {
                    emptySpot = Character.getNumericValue(tokens[i].charAt(j)) - 1;
                }
            }
        }
        return board;
    }

    private static Player initWhitePlayerFromBoard(Board board) {
        Player white = new Player(true);
        for (int i = 0; i < board.cell.length; i++) {
            for (int j = 0; j < board.cell[i].length; j++) {
                if (board.cell[i][j].piece != null) {
                    if (board.cell[i][j].piece.symbol == 'S') {
                        removePieceFromHand(white, 'S');
                    } else if (board.cell[i][j].piece.symbol == 'P') {
                        removePieceFromHand(white, 'P');
                    } else if (board.cell[i][j].piece.symbol == 'B') {
                        removePieceFromHand(white, 'B');
                    } else if (board.cell[i][j].piece.symbol == 'C') {
                        removePieceFromHand(white, 'C');
                    }
                }
            }
        }
        return white;
    }

    private static Player initBlackPlayerFromBoard(Board board) {
        Player black = new Player(false);
        for (int i = 0; i < board.cell.length; i++) {
            for (int j = 0; j < board.cell[i].length; j++) {
                if (board.cell[i][j].piece != null) {
                    if (board.cell[i][j].piece.symbol == 's') {
                        removePieceFromHand(black, 's');
                    } else if (board.cell[i][j].piece.symbol == 'p') {
                        removePieceFromHand(black, 'p');
                    } else if (board.cell[i][j].piece.symbol == 'b') {
                        removePieceFromHand(black, 'b');
                    } else if (board.cell[i][j].piece.symbol == 'c') {
                        removePieceFromHand(black, 'c');
                    }
                }
            }
        }
        return black;
    }

    private static Set<Integer> initHashFromString(String data) {
        Set<Integer> hashList = new HashSet<>();
        data = data.replace("[", "");
        data = data.replace("]", "");
        data = data.replace(" ", "");

        if (!data.isEmpty()) {
            String[] tokens = data.split(",");
            for (String s : tokens) {
                hashList.add(Integer.parseInt(s));
            }
        }
        return hashList;
    }

    private static void removePieceFromHand(Player player, char symbol) {
//        List<Integer> toRemove = new ArrayList<>();
//        for (int i = 0; i < player.getHand().size(); i++) {
//            if (player.getHand().get(i).symbol == symbol) {
//                toRemove.add(i);
//            }
//        }
//        for (Integer integer : toRemove) {
//            System.out.println(player.getHand().size());
//
//            player.getHand().remove(integer.intValue());
//        }
        player.getHand().removeIf(p -> p.symbol == symbol);
    }

    public static Set<Integer> hashChildren(List<StateData> children){
        Set<Integer> childrenList = new HashSet<>();
        for (StateData stateData : children) {
            childrenList.add(stateData.getHash());
        }
        return childrenList;
    }
}
