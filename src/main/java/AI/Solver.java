package AI;

import data.StateData;
import engine.IOEngine;
import engine.LogicEngine;
import pojo.Piece;

import java.util.*;

public class Solver {
    public static List<StateData> getNextStates(StateData root, Map<Integer, StateData> transpositionTable) {
        List<StateData> nextStates = new ArrayList<>();

        if (root.isWhiteTurn()) {
            boolean isLegalMoveAvailable = false;
            for (int i = 0; i < root.getBoard().size; i++) {
                for (int j = 0; j < root.getBoard().size; j++) {
                    StateData tmp = new StateData(root);
                    Iterator<Piece> iter = tmp.getWhitePlayer().getHand().iterator();
                    while (iter.hasNext()) {
                        Piece p = iter.next();
                        if (LogicEngine.isLegalMove(root.getBoard(), p, i, j)) {
                            isLegalMoveAvailable = true;
                            StateData copy = new StateData(root);
                            copy.getParentHash().add(root.hashCode());
                            copy.setEnd(LogicEngine.isEnd(root.getBoard(), true));
                            if (copy.isEnd()){
                                copy.setWinner("white");
                            }
                            LogicEngine.move(copy.getWhitePlayer(), copy.getBoard(), p, i, j);
                            copy.setWhiteTurn(false);
                            copy.setHash(copy.hashCode());
                            root.getChildrenHash().add(copy.hashCode());

                            if (!transpositionTable.containsKey(copy.getHash())) {
                                nextStates.add(copy);
                                transpositionTable.put(copy.getHash(), copy);
                            }
                            iter.remove();
                        }

                    }
                }
            }
            if (!isLegalMoveAvailable){
                root.setEnd(true);
                root.setWinner("black");
            }
        } else {
            boolean isLegalMoveAvailable = false;
            for (int i = 0; i < root.getBoard().size; i++) {
                for (int j = 0; j < root.getBoard().size; j++) {
                    StateData tmp = new StateData(root);
                    Iterator<Piece> iter = tmp.getBlackPlayer().getHand().iterator();
                    while (iter.hasNext()) {
                        Piece p = iter.next();
                        if (LogicEngine.isLegalMove(root.getBoard(), p, i, j)) {
                            isLegalMoveAvailable = true;
                            StateData copy = new StateData(root);
                            copy.getParentHash().add(root.hashCode());
                            copy.setEnd(LogicEngine.isEnd(root.getBoard(), false));
                            if (copy.isEnd()){
                                copy.setWinner("black");
                            }
                            LogicEngine.move(copy.getBlackPlayer(), copy.getBoard(), p, i, j);
                            copy.setWhiteTurn(true);
                            copy.setHash(copy.hashCode());
                            root.getChildrenHash().add(copy.hashCode());
                            if (!transpositionTable.containsKey(copy.getHash())) {
                                nextStates.add(copy);
                                transpositionTable.put(copy.getHash(), copy);
                            }
                            iter.remove();
                        }
                    }
                }
            }
            if (!isLegalMoveAvailable){
                root.setEnd(true);
                root.setWinner("white");
            }
        }
        return nextStates;
    }

    public static void solve(StateData root) {
        LinkedList<StateData> evaluateQueue = new LinkedList<>();
        LinkedList<StateData> store = new LinkedList<>();
        evaluateQueue.add(root);
        Map<Integer, StateData> transpositionTable = new HashMap<>();
        do {
            while (!evaluateQueue.isEmpty()) {
                StateData state = evaluateQueue.pop();
                if (!state.isEnd()) {
                    List<StateData> children = getNextStates(state, transpositionTable);
                    store.addAll(children);
                }
            }
//            if (store.size() >= 10) {
//                System.out.println("Test finished");
//                List<String> write = new ArrayList<>();
//                for (StateData stateData : store) {
//                    write.add(stateData.toString());
//                }
//                IOEngine.appendToFile(write, "C:\\QuantikStateTable\\Table.txt");
//                return;
//            }
            evaluateQueue = copyList(store);
            List<String> write = new ArrayList<>();
            for (StateData stateData : store) {
                write.add(stateData.toString());
            }
            IOEngine.appendToFile(write, "C:\\QuantikStateTable\\Table.txt");
            store = new LinkedList<>();
        } while (!evaluateQueue.isEmpty());
    }

    private static LinkedList<StateData> copyList(List<StateData> first) {
        LinkedList<StateData> copy = new LinkedList<>();
        for (StateData stateData : first) {
            copy.add(new StateData(stateData));
        }
        return copy;
    }
}
