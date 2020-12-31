import AI.Solver;
import data.StateData;

import java.util.*;

public class main {
    public static void rotateMatrix(char[][] matrix, int rotations) {
        for (int k = 0; k < rotations; k++) {
            int row = matrix.length;
            //first find the transpose of the matrix.
            for (int i = 0; i < row; i++) {
                for (int j = i; j < row; j++) {
                    char temp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = temp;
                }
            }
            //reverse each row
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < row / 2; j++) {
                    char temp = matrix[i][j];
                    matrix[i][j] = matrix[i][row - 1 - j];
                    matrix[i][row - 1 - j] = temp;
                }
            }
        }
    }

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
//
        return result;
    }

    private static int placementHistoryHash(String string) {
        StringBuilder placementHistory = new StringBuilder();
        for (Character c : string.toCharArray()) {
            placementHistory.append(c);
        }
        int hash = 1;
        Map<Character, List<Integer>> indexMap = findAllCharacterIndex(placementHistory.toString());
        System.out.println(indexMap);
        for (List<Integer> integers : indexMap.values()) {
            Collections.sort(integers);
            hash += integers.hashCode();
        }
        return hash;
    }

    public static void main(String[] args) {
//        List<String> tmp = Arrays.asList("asdsad", "dassac", "qwqrqw");
//        IOEngine.write(tmp, "C:\\iotest\\test.txt");
//        List<String> test = (List<String>) IOEngine.read("C:\\iotest\\test.txt");
//        System.out.println(test);

//        StateData root = new StateData();
////        LogicEngine.move(root.getWhitePlayer(), root.getBoard(), root.getWhitePlayer().getHand().get(0), 0, 0);
//
//        List<StateData> tp = Solver.getNextStates(root);
//        StateData grandChild = new StateData(tp.get(0));
//        List<StateData> gtp = Solver.getNextStates(grandChild);
//        StateData grandgrandChild = new StateData(gtp.get(0));
//        List<StateData> ggtp = Solver.getNextStates(grandgrandChild);

//        for (StateData stateData : ggtp){
//            stateData.print();
//        }

        StateData root = new StateData();
        Solver.solve(root);

//        String first = "SbS";
//        String firstEqual = "CbC";
//        String second = "SbB";
//        String secondEqual = "CaA";
//
//        System.out.println(placementHistoryHash(first));
//        System.out.println("-----------");
//        System.out.println(placementHistoryHash(firstEqual));
//        System.out.println("-----------");
//        System.out.println(placementHistoryHash(second));
//        System.out.println("-----------");
//        System.out.println(placementHistoryHash(secondEqual));


//        char[][] matrix = {
//                {'S', '.', '.'},
//                {'.', '.', '.'},
//                {'.', '.', '.'}
//        };
//        rotateMatrix(matrix,3);
//        for (int i = 0; i < matrix.length; i++) {
//            for (int i1 = 0; i1 < matrix[i].length; i1++) {
//                System.out.print(matrix[i][i1]);
//            }
//            System.out.println();
//        }


    }
}

