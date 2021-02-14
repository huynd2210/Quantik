package AI;

import data.StateData;
import engine.IOEngine;
import engine.LogicEngine;
import org.javatuples.Pair;
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
                            if (copy.isEnd()) {
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
            if (!isLegalMoveAvailable) {
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
                            if (copy.isEnd()) {
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
            if (!isLegalMoveAvailable) {
                root.setEnd(true);
                root.setWinner("white");
            }
        }
        return nextStates;
    }

    public static void solve(StateData root) {
        LinkedList<StateData> evaluateQueue = new LinkedList<>();
        LinkedList<StateData> store = new LinkedList<>();
        Set<String> write = new HashSet<>();
        evaluateQueue.add(root);
        Map<Integer, StateData> transpositionTable = new HashMap<>();
        do {
            while (!evaluateQueue.isEmpty()) {
                StateData state = evaluateQueue.pop();
                if (!state.isEnd()) {
                    List<StateData> children = getNextStates(state, transpositionTable);
                    store.addAll(children);
                }
                write.add(state.toString());
            }

            evaluateQueue = copyList(store);
//            List<String> write = new ArrayList<>();
//            for (StateData stateData : store) {
//                write.add(stateData.toString());
//            }
            IOEngine.appendToFile(write, "C:\\QuantikStateTable\\Table.txt");
            store = new LinkedList<>();
            write = new HashSet<>();
        } while (!evaluateQueue.isEmpty());
    }


    private static LinkedList<StateData> copyList(List<StateData> first) {
        LinkedList<StateData> copy = new LinkedList<>();
        for (StateData stateData : first) {
            copy.add(new StateData(stateData));
        }
        return copy;
    }

    private static String determineWinner(boolean isWhiteTurn, List<String> children) {
        if (isWhiteTurn) {
            for (String child : children) {
                if (child.equalsIgnoreCase("white")) {
                    return "white";
                }
            }
            return "black";
        } else {
            for (String child : children) {
                if (child.equalsIgnoreCase("black")) {
                    return "black";
                }
            }
            return "white";
        }
    }

    private static double determineValue(boolean isWhiteTurr, List<Double> values){
        if (isWhiteTurr){
            return Collections.max(values);
        }else{
            return Collections.min(values);
        }
    }

    public static Pair<String, Double> backpropagate(StateData root, Map<Integer, StateData> table, int depthDiscount) {
        if (root.isEnd()) {
            if (root.getWinner().equalsIgnoreCase("white")){
                root.setStateValue(1000 - (double) depthDiscount);
            }else{
                root.setStateValue(-1000 + (double) depthDiscount);
            }
            IOEngine.appendStringToFile(root.toString(), "C:\\QuantikStateTable\\FinalTable.txt");
            return new Pair<>(root.getWinner(), root.getStateValue());
        }else{
            List<Pair<String, Double>> childrenResult = new ArrayList<>();
            for (Integer childHash : root.getChildrenHash()) {
                childrenResult.add(backpropagate(table.get(childHash), table, depthDiscount + 1));
            }
            List<String> winners = new ArrayList<>();
            List<Double> childrenValues = new ArrayList<>();
            for (Pair<String, Double> pair : childrenResult) {
                winners.add(pair.getValue0());
                childrenValues.add(pair.getValue1());
            }
            root.setWinner(determineWinner(root.isWhiteTurn(), winners));
            root.setStateValue(determineValue(root.isWhiteTurn(), childrenValues));
        }
        IOEngine.appendStringToFile(root.toString(), "C:\\QuantikStateTable\\FinalTable.txt");
        return new Pair<>(root.getWinner(), root.getStateValue());
    }
}
