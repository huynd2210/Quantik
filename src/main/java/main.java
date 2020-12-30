import AI.Solver;
import data.StateData;
import engine.LogicEngine;
import org.javatuples.Pair;

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

    public static void main(String[] args) {
//        List<String> tmp = Arrays.asList("asdsad", "dassac", "qwqrqw");
//        IOEngine.write(tmp, "C:\\iotest\\test.txt");
//        List<String> test = (List<String>) IOEngine.read("C:\\iotest\\test.txt");
//        System.out.println(test);

        StateData root = new StateData();
//        LogicEngine.move(root.getWhitePlayer(), root.getBoard(), root.getWhitePlayer().getHand().get(0), 0, 0);

        List<StateData> tp = Solver.getNextStates(root);
        StateData grandChild = new StateData(tp.get(0));
        List<StateData> gtp = Solver.getNextStates(grandChild);

        for (StateData stateData : tp){
            stateData.print();
        }


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

