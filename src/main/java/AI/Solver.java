package AI;

import data.StateData;
import engine.LogicEngine;
import pojo.Piece;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    public static List<StateData> getNextStates(StateData root){
        List<StateData> nextStates = new ArrayList<>();
        if (root.isWhiteTurn()){
            for (int i = 0; i < root.getBoard().size; i++){
                for (int j = 0; j < root.getBoard().size; j++){
                    for (Piece p : root.getWhitePlayer().getHand()){
                        if (LogicEngine.isLegalMove(root.getBoard(), p, i, j)){
                            StateData copy = new StateData(root);
                            copy.getParentHash().add(root.getHash());
                            root.getChildrenHash().add(copy.getHash());
                            LogicEngine.move(root.getWhitePlayer(), root.getBoard(), p, i, j);
                            copy.setWhiteTurn(false);
                        }
                    }
                }
            }
        }else{
            for (int i = 0; i < root.getBoard().size; i++){
                for (int j = 0; j < root.getBoard().size; j++){
                    for (Piece p : root.getBlackPlayer().getHand()){
                        if (LogicEngine.isLegalMove(root.getBoard(), p, i, j)){
                            StateData copy = new StateData(root);
                            copy.getParentHash().add(root.getHash());
                            root.getChildrenHash().add(copy.getHash());
                            copy.setEnd(LogicEngine.isEnd(root.getBoard(), false));
                            LogicEngine.move(root.getWhitePlayer(), root.getBoard(), p, i, j);
                            copy.setWhiteTurn(true);
                        }
                    }
                }
            }
        }
        return nextStates;
    }



}
