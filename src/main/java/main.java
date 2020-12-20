import AI.Solver;
import data.StateData;
import engine.LogicEngine;

import java.util.*;

public class main {


    private static Map<Character, List<Integer>> findAllCharacterIndex(String string) {
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

        return result;
    }

    public static int placementHistoryHash(String string) {
        StringBuilder placementHistory = new StringBuilder();
        for (Character character : string.toCharArray()) {
            placementHistory.append(character);
        }
        int hash = 1;
        Map<Character, List<Integer>> indexMap = findAllCharacterIndex(placementHistory.toString());
        for (List<Integer> integers : indexMap.values()) {
            for (Integer index : integers) {
                hash += index;
            }
        }
        return hash;
    }

    public static void main(String[] args) {
//        List<String> tmp = Arrays.asList("asdsad", "dassac", "qwqrqw");
//        IOEngine.write(tmp, "C:\\iotest\\test.txt");
//        List<String> test = (List<String>) IOEngine.read("C:\\iotest\\test.txt");
//        System.out.println(test);


//        System.out.println(placementHistoryHash("PaB"));
//        System.out.println(placementHistoryHash("PbS"));
//        System.out.println(placementHistoryHash("Sp"));

//        System.out.println(findAllCharacterIndex("PaB").values());
//        System.out.println(findAllCharacterIndex("PbS").values());
//        System.out.println(findAllCharacterIndex("Pa").values().iterator().next().hashCode());
//        System.out.println(findAllCharacterIndex("pb").values().iterator().next().hashCode());
        StateData root = new StateData();
//        LogicEngine.move(root.getWhitePlayer(), root.getBoard(), root.getWhitePlayer().getHand().get(0), 0, 0);
        List<StateData> tp = Solver.getNextStates(root);
        StateData grandChild = new StateData(tp.get(0));
        List<StateData> gtp = Solver.getNextStates(grandChild);

        for (StateData stateData : gtp) {
            stateData.print();
        }


    }


}
