package AI;

import data.StateData;
import engine.LogicEngine;
import pojo.Piece;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Solver {
    public static List<StateData> getNextStates(StateData root) {
        List<StateData> nextStates = new ArrayList<>();
        if (root.isWhiteTurn()) {
            for (int i = 0; i < root.getBoard().size; i++) {
                for (int j = 0; j < root.getBoard().size; j++) {
                    StateData tmp = new StateData(root);
                    Iterator<Piece> iter = tmp.getWhitePlayer().getHand().iterator();
                    while(iter.hasNext()){
                        Piece p = iter.next();
                        if (LogicEngine.isLegalMove(root.getBoard(), p, i, j)) {
                            StateData copy = new StateData(root);
                            copy.getParentHash().add(root.getHash());
                            root.getChildrenHash().add(copy.getHash());
                            LogicEngine.move(root.getWhitePlayer(), copy.getBoard(), p, i, j);
                            copy.setWhiteTurn(false);
                            nextStates.add(copy);
                            iter.remove();
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < root.getBoard().size; i++) {
                for (int j = 0; j < root.getBoard().size; j++) {
                    StateData tmp = new StateData(root);
                    Iterator<Piece> iter = tmp.getWhitePlayer().getHand().iterator();
                    while(iter.hasNext()){
                        Piece p = iter.next();
                        if (LogicEngine.isLegalMove(root.getBoard(), p, i, j)) {
                            StateData copy = new StateData(root);
                            copy.getParentHash().add(root.getHash());
                            root.getChildrenHash().add(copy.getHash());
                            copy.setEnd(LogicEngine.isEnd(root.getBoard(), false));
                            LogicEngine.move(root.getWhitePlayer(), root.getBoard(), p, i, j);
                            copy.setWhiteTurn(true);
                            nextStates.add(copy);
                            iter.remove();
                        }
                    }
                }
            }
        }
        return nextStates;
    }
}
