import AI.Solver;
import data.PieceList;
import data.StateData;
import engine.DataProcessor;
import engine.IOEngine;
import engine.LogicEngine;
import org.codehaus.plexus.util.StringUtils;
import org.javatuples.Pair;
import pojo.Piece;
import pojo.Player;

import javax.xml.crypto.Data;
import javax.xml.stream.events.Characters;
import java.lang.reflect.Parameter;
import java.util.*;

public class main {
    public static List<StateData> load(List<String> data){
        List<StateData> stateDataList = new ArrayList<>();
        for (String stateString : data) {
            if (!StringUtils.isBlank(stateString)){
                stateDataList.add(DataProcessor.parseNotation(stateString));
            }

        }
        return stateDataList;
    }

    public static void addChildren(){
        List<String> data = (List<String>) IOEngine.read("C:\\QuantikStateTable\\Table.txt");

        List<StateData> stateDataList = new ArrayList<>();
        if (data != null){
            stateDataList = load(data);
        }
        for (StateData stateData : stateDataList) {
            if (stateData != null){
                List<StateData> stateChildrenList = Solver.getNextStates(stateData, new HashMap<>());
                stateData.setChildrenHash(DataProcessor.hashChildren(stateChildrenList));
                stateData.print();
            }
        }

        Set<String> processedData = new HashSet<>();

        for (StateData stateData : stateDataList) {
            processedData.add(stateData.toString());
        }

        IOEngine.appendToFile(processedData, "C:\\QuantikStateTable\\NewTable.txt");
    }


    public static Map<Integer, StateData> initTable(List<StateData> list){
        Map<Integer, StateData> table = new HashMap<>();
        for (StateData stateData : list) {
            table.put(stateData.getHash(), stateData);
        }
        return table;
    }


    public static StateData getBestMove(StateData node, Map<Integer, StateData> table){
        StateData tableNode = table.get(node.getHash());

        List<Pair<StateData, Double>> stateValuePair = new ArrayList<>();
        for (Integer childHash : tableNode.getChildrenHash()){
            StateData child = table.get(childHash);
            stateValuePair.add(new Pair<>(child, child.getStateValue()));
        }

        double max = stateValuePair.get(0).getValue1();

        Pair<StateData, Double> toReturn = new Pair<>(stateValuePair.get(0).getValue0(), max);

        for (int i = 1; i < stateValuePair.size(); i++){
            if (stateValuePair.get(i).getValue1() > max){
                max = stateValuePair.get(i).getValue1();
                toReturn = new Pair<>(stateValuePair.get(i).getValue0(), max);
            }
        }
        return toReturn.getValue0();
    }

    //Assume user is blackPlayer
    public static StateData play(StateData stateData, int i, int j, char piece){
        //temp init
        Piece pieceToPlay = PieceList.blackCylinder;
        if (Character.toLowerCase(piece) == 'p'){
            pieceToPlay = PieceList.blackPyramid;
        }else if (Character.toLowerCase(piece) == 's'){
            pieceToPlay = PieceList.blackSphere;
        }else if (Character.toLowerCase(piece) == 'b'){
            pieceToPlay = PieceList.blackSquare;
        }else if (Character.toLowerCase(piece) == 'c'){
            pieceToPlay = PieceList.blackCylinder;
        }

        List<StateData> children = Solver.getNextStates(stateData, new HashMap<>());

        Player player = stateData.getBlackPlayer();
        LogicEngine.move(player, stateData.getBoard(), pieceToPlay, i , j);

        for (StateData child : children){
            if (child.getBoard().hashCode() == stateData.getBoard().hashCode()){
                System.out.println("start");
                child.getBoard().print();
                System.out.println(child.getBoard().hashCode());
                System.out.println();
                stateData.getBoard().print();
                System.out.println(stateData.getBoard().hashCode());
                System.out.println("end");

                return child;
            }
        }
        System.out.println("wtf");
        return stateData;
    }

    public static void main(String[] args) {
//        List<String> data = (List<String>) IOEngine.read("C:\\QuantikStateTable\\Table.txt");
//        Set<String> save = new HashSet<>();
//        for (String datum : data) {
//            String tmp = datum + "\n";
//            save.add(tmp);
//        }
//        IOEngine.appendToFile(save, "C:\\QuantikStateTable\\NewTable.txt");


//        List<String> raw = (List<String>) IOEngine.read("C:\\QuantikStateTable\\NewTable.txt");
//        List<StateData> data = new ArrayList<>();
//        if (raw != null){
//            data = load(raw);
//        }
//        Map<Integer, StateData> table = initTable(data);
//        StateData root = new StateData();
//        List<StateData> firstChildren = Solver.getNextStates(root, new HashMap<>());
//        for (StateData child : firstChildren) {
//            root.getChildrenHash().add(child.getHash());
//        }
//        table.put(root.getHash(), root);
//
//        System.out.println(Solver.backpropagate(table.get(954273), table, 0));

        List<String> tableBase = (List<String>) IOEngine.read(("C:\\QuantikStateTable\\FinalTable.txt"));
        List<StateData> data = new ArrayList<>();
        if (tableBase != null){
            data = load(tableBase);
        }
        Map<Integer, StateData> table = initTable(data);
        StateData root = new StateData();

//        List<StateData> children = Solver.getNextStates(root, new HashMap<>());
//        for (StateData child : children){
//            child.print();
//        }



        StateData best = getBestMove(root, table);
        best.print();
        System.out.println("------------");
        best = getBestMove(best, table);
        best.print();
        System.out.println("------------");
        best = getBestMove(best, table);
        best.print();
//        play(best,1,2, 's').print();

        System.out.println("------------");



//        best = getBestMove(best, table);
//        best.print();
//        System.out.println("------------");
//        Collection<StateData> dataCollection = table.values();


//        for (StateData stateData : dataCollection) {
//            System.out.println(stateData.getHash());
//            Solver.backpropagate(stateData, table, 0);
//        }

//        Set<String> save = new HashSet<>();
//        for (StateData stateData : dataCollection) {
//            save.add(stateData.toString());
//        }


    }
}

