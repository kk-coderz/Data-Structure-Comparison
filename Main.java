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

        String[] funcNames = {"add","put","contains","containsKey","remove","clear"};
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

            int checkForContains = randomValues[rand.nextInt(100000)];
            int checkForRemove = randomValues[rand.nextInt(100000)];

            for (String func : funcNames) {
                switch (func) {
                    case "add":
                        for (Collection<Integer> c : collections) {
                            long startTime = System.nanoTime();
                            c.add(randomValues[rand.nextInt(100000)]);
                            long endTime = System.nanoTime();

                            String collectionName = c.getClass().getSimpleName();
                            Functions.recordTime(addRuntimes, collectionName, endTime - startTime);
                        }
                        for (Collection<Integer> set : sets) {
                            long startTime = System.nanoTime();
                            set.add(randomValues[rand.nextInt(100000)]);
                            long endTime = System.nanoTime();

                            String collectionName = set.getClass().getSimpleName();
                            Functions.recordTime(addRuntimes, collectionName, endTime - startTime);
                        }
                        break;
                    case "put":
                        for (Map<Integer,Integer> map : maps) {
                            long startTime = System.nanoTime();
                            map.put(rand.nextInt(100000),(rand.nextInt(100000)));
                            long endTime = System.nanoTime();

                            String mapName = map.getClass().getSimpleName();
                            Functions.recordTime(addRuntimes, mapName, (endTime - startTime));
                        }
                        break;
                    case "containsKey":
                        for (Map<Integer,Integer> map : maps) {
                            long startTime = System.nanoTime();
                            map.containsKey(checkForContains);
                            long endTime = System.nanoTime();

                            String mapName = map.getClass().getSimpleName();
                            Functions.recordTime(containsRuntimes, mapName, endTime - startTime);
                        }
                        break;
                    case "contains":
                        for (Collection<Integer> c : collections) {
                            long startTime = System.nanoTime();
                            c.contains(checkForContains);
                            long endTime = System.nanoTime();

                            String collectionName = c.getClass().getSimpleName();
                            Functions.recordTime(containsRuntimes, collectionName, endTime - startTime);
                        }
                        for (Collection<Integer> set : sets) {
                            long startTime = System.nanoTime();
                            set.contains(checkForContains);
                            long endTime = System.nanoTime();

                            String setName = set.getClass().getSimpleName();
                            Functions.recordTime(containsRuntimes, setName, endTime - startTime);
                        }
                        break;
                    case "remove":
                        for (Collection<Integer> c : collections) {
                            long startTime = System.nanoTime();
                            c.remove(checkForRemove);
                            long endTime = System.nanoTime();

                            String collectionName = c.getClass().getSimpleName();
                            Functions.recordTime(removeRuntimes, collectionName, endTime - startTime);
                        }
                        for (Collection<Integer> set : sets) {
                            long startTime = System.nanoTime();
                            set.remove(checkForRemove);
                            long endTime = System.nanoTime();

                            String setName = set.getClass().getSimpleName();
                            Functions.recordTime(removeRuntimes, setName, endTime - startTime);
                        }
                        for (Map<Integer,Integer> map : maps) {
                            long startTime = System.nanoTime();
                            map.remove(checkForRemove);
                            long endTime = System.nanoTime();

                            String mapName = map.getClass().getSimpleName();
                            Functions.recordTime(removeRuntimes, mapName, endTime - startTime);
                        }
                        break;
                    case "clear":
                        for (Collection<Integer> c : collections) {
                            long startTime = System.nanoTime();
                            c.clear();
                            long endTime = System.nanoTime();

                            String collectionName = c.getClass().getSimpleName();
                            Functions.recordTime(clearRuntimes, collectionName, endTime - startTime);
                        }
                        for (Collection<Integer> set : sets) {
                            long startTime = System.nanoTime();
                            set.clear();
                            long endTime = System.nanoTime();

                            String setName = set.getClass().getSimpleName();
                            Functions.recordTime(clearRuntimes, setName, endTime - startTime);
                        }
                        for (Map<Integer,Integer> map : maps) {
                            long startTime = System.nanoTime();
                            map.clear();
                            long endTime = System.nanoTime();

                            String mapName = map.getClass().getSimpleName();
                            Functions.recordTime(clearRuntimes, mapName, endTime - startTime);
                        }
                        break;
                }
            }
            System.out.printf("Iteration %d done.\n", s + 1);
        }

        // calculate average and output to csv file
        for (String name : collectionNames) {
            addRuntimes.put(name,addRuntimes.get(name) / sampleSize);
            containsRuntimes.put(name,containsRuntimes.get(name) / sampleSize);
            removeRuntimes.put(name, containsRuntimes.get(name) / sampleSize);
            clearRuntimes.put(name,clearRuntimes.get(name) / sampleSize);
        }

        String[] timedFunctions = {"add","contains","remove","clear"};
        File outputFile = new File("output.csv");
        try {
            FileWriter writer = new FileWriter(outputFile);
            String columnNames = "function," + String.join(",", collectionNames) + "\n";
            writer.write(columnNames);

            for (String funcName : timedFunctions) {
                String row = funcName + ",";
                for (String colName : collectionNames) {
                    switch (funcName) {
                        case "add":
                            row += addRuntimes.get(colName);
                            if (colName != "TreeMap") {
                                row += ",";
                            } else {
                                row += "\n";
                            }
                            break;
                        case "contains":
                            row += containsRuntimes.get(colName);
                            if (colName != "TreeMap") {
                                row += ",";
                            } else {
                                row += "\n";
                            }
                            break;
                        case "remove":
                            row += removeRuntimes.get(colName);
                            if (colName != "TreeMap") {
                                row += ",";
                            } else {
                                row += "\n";
                            }
                            break;
                        case "clear":
                            row += clearRuntimes.get(colName);
                            if (colName != "TreeMap") {
                                row += ",";
                            } else {
                                row += "\n";
                            }
                            break;
                    }
                }
                writer.write(row);
            }
            writer.close();

        } catch(IOException error) {
            System.out.println(error);
        }
    }
}