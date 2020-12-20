package AI;

import data.StateData;
import engine.LogicEngine;
import pojo.Piece;

import java.util.*;

public class Solver {
    public static List<StateData> getNextStates(StateData root) {
        List<StateData> nextStates = new ArrayList<>();
        if (root.isWhiteTurn()) {
            for (int i = 0; i < root.getBoard().size; i++) {
                for (int j = 0; j < root.getBoard().size; j++) {
                    StateData tmp = new StateData(root);
                    Iterator<Piece> iter = tmp.getWhitePlayer().getHand().iterator();
                    while (iter.hasNext()) {
                        Piece p = iter.next();
                        if (LogicEngine.isLegalMove(root.getBoard(), p, i, j)) {
                            StateData copy = new StateData(root);
                            copy.getParentHash().add(root.getHash());
                            copy.setEnd(LogicEngine.isEnd(root.getBoard(), true));
                            LogicEngine.move(copy.getWhitePlayer(), copy.getBoard(), p, i, j);
                            copy.setWhiteTurn(false);
                            copy.setHash(copy.hashCode());
                            root.getChildrenHash().add(copy.getHash());
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
                    Iterator<Piece> iter = tmp.getBlackPlayer().getHand().iterator();
                    while (iter.hasNext()) {
                        Piece p = iter.next();
                        if (LogicEngine.isLegalMove(root.getBoard(), p, i, j)) {
                            StateData copy = new StateData(root);
                            copy.getParentHash().add(root.getHash());
                            copy.setEnd(LogicEngine.isEnd(root.getBoard(), false));
                            LogicEngine.move(copy.getBlackPlayer(), copy.getBoard(), p, i, j);
                            copy.setWhiteTurn(true);
                            copy.setHash(copy.hashCode());
                            root.getChildrenHash().add(copy.getHash());
                            nextStates.add(copy);
                            iter.remove();
                        }
                    }
                }
            }
        }
        return nextStates;
    }

    public void solve(StateData root) {
        LinkedList<StateData> evaluateQueue = new LinkedList<>();
        LinkedList<StateData> store = new LinkedList<>();
        evaluateQueue.add(root);
        Map<Integer, StateData> transpositionTable = new HashMap<>();
        do {
            while (!evaluateQueue.isEmpty()) {
                StateData state = evaluateQueue.pop();
                if (!state.isEnd() && !transpositionTable.containsKey(state.getHash())) {
                    List<StateData> children = getNextStates(state);
                    store.addAll(children);
                    transpositionTable.put(state.getHash(), state);
                }
            }

            evaluateQueue = copyList(store);
            store = new LinkedList<>();

        } while (!evaluateQueue.isEmpty());
    }

    private LinkedList<StateData> copyList(List<StateData> first) {
        LinkedList<StateData> copy = new LinkedList<>();
        for (StateData stateData : first) {
            copy.add(new StateData(stateData));
        }
        return copy;
    }
}
