package engine;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class IOEngine {
    public static void write(Collection<String> input, String filePath) {
        BufferedWriter bufferedWriter = null;

        try {
            File file = new File(filePath);

            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File not found (" + filePath + ")");
                //return;
            }

            FileWriter fWriter = new FileWriter(file, false);
            bufferedWriter = new BufferedWriter(fWriter);

            Iterator<String> iterator = input.iterator();

            while (iterator.hasNext()) {
                bufferedWriter.write(iterator.next() + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Collection<String> read(String filePath) {
        BufferedReader bufferedReader = null;
        Collection<String> output = null;

        try {
            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("File not found (" + filePath + ")");
                return null;
            }

            FileReader fReader = new FileReader(file);
            bufferedReader = new BufferedReader(fReader);

            output = new ArrayList<>();

            while(bufferedReader.ready()) {
                output.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    public static void appendStringToFile(String string, String filePath){
        try{
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(string);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendToFile(Collection<String> list, String filePath) {
        BufferedWriter bufferedWriter = null;

        try {
            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("File not found (" + filePath + ")");
                return;
            }

            FileWriter fWriter = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(fWriter);

            for (String s : list) {
                bufferedWriter.write(s + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
