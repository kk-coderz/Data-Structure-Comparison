import java.util.Collection;
import java.util.Map;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String args[]) {
        // Initialize collections for testing
        Collection<Integer>[] collections = new Collection[4];
        Collection<Integer>[] sets = new Collection[3];
        Map<Integer,Integer>[] maps = new Map[3];

        sets[0] = new HashSet<>();
        sets[1] = new TreeSet<>();
        sets[2] = new LinkedHashSet<>();

        collections[0] = new ArrayList<>();
        collections[1] = new LinkedList<>();
        collections[2] = new ArrayDeque<>();
        collections[3] = new PriorityQueue<>();

        maps[0] = new HashMap<Integer,Integer>();
        maps[1] = new TreeMap<Integer,Integer>();
        maps[2] = new LinkedHashMap<Integer,Integer>();


        // Initialize maps to store different runtimes of tests
        HashMap<String,Double> addRuntimes = new HashMap<>();
        HashMap<String,Double> containsRuntimes = new HashMap<>();
        HashMap<String,Double> removeRuntimes = new HashMap<>();
        HashMap<String,Double> clearRuntimes = new HashMap<>();

        HashMap<String, HashMap<String,Double>> runtimes = new HashMap<>();
        runtimes.put("add",addRuntimes);
        runtimes.put("contains",containsRuntimes);
        runtimes.put("remove",removeRuntimes);
        runtimes.put("clear",clearRuntimes);


        String[] funcNames = {"add","contains","remove","clear"};
        String[] collectionNames = {"HashSet","TreeSet","LinkedHashSet","ArrayList","LinkedList","PriorityQueue","ArrayDeque","HashMap","LinkedHashMap","TreeMap"};

        // Run each test for 100 times
        int sampleSize = 100;
        for (int s = 0; s < sampleSize; s++) {
            // create and load the values to collections
            Random rand = new Random();
            int[] randomValues = new int[100000];
            for (int i = 0; i < 100000; i++) {
                randomValues[i] = rand.nextInt(100000);
            }
            for (Collection<Integer> c : collections) {
                Functions.load(c,randomValues);
            }
            for (Collection<Integer> c : sets) {
                Functions.loadSet(c,100000);
            }
            for (Map<Integer,Integer> m : maps) {
                Functions.loadMap(m, randomValues);
            }

            // check runtime for different collections for different test functions and record the times
            for (String func : funcNames) {
                for (Collection<Integer> c : collections) {
                    Functions.checkAndRecordTime(runtimes.get(func), c, func, rand.nextInt(sampleSize));
                }
                for (Collection<Integer> set : sets) {
                    Functions.checkAndRecordTime(runtimes.get(func), set, func, rand.nextInt(sampleSize));
                }
                for (Map<Integer,Integer> map : maps) {
                    Functions.checkAndRecordTime(runtimes.get(func), map, func, rand.nextInt(sampleSize));
                }
            }
            System.out.printf("Iteration %d done.\n", s + 1);
        }

        // calculate average and output to csv file
        for (String name : collectionNames) {
            // addRuntimes.put(name,addRuntimes.get(name) / sampleSize);
            // containsRuntimes.put(name,containsRuntimes.get(name) / sampleSize);
            // removeRuntimes.put(name, removeRuntimes.get(name) / sampleSize);
            // clearRuntimes.put(name,clearRuntimes.get(name) / sampleSize);
            for (String func : funcNames) {
                runtimes.get(func).put(name, runtimes.get(func).get(name) / sampleSize);
            }
        }

        File outputFile = new File("output.csv");
        try {
            FileWriter writer = new FileWriter(outputFile);
            String columnNames = "function," + String.join(",", collectionNames) + "\n";
            writer.write(columnNames);

            for (String funcName : funcNames) {
                String row = funcName + ",";
                for (String colName : collectionNames) {
                    // switch (funcName) {
                    //     case "add":
                    //         row += addRuntimes.get(colName);
                    //         break;
                    //     case "contains":
                    //         row += containsRuntimes.get(colName);
                    //         break;
                    //     case "remove":
                    //         row += removeRuntimes.get(colName);
                    //         break;
                    //     case "clear":
                    //         row += clearRuntimes.get(colName);
                    //         break;
                    // }
                    row += runtimes.get(funcName).get(colName);
                    row += (colName != "TreeMap") ? "," : "\n";
                }
                writer.write(row);
            }
            writer.close();

        } catch(IOException error) {
            System.out.println(error);
        }
    }
}