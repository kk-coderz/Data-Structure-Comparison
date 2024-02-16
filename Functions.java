import java.util.*;

class Functions {
    static Random randomGenerator = new Random();
    static LinkedHashSet<Integer> randomValuesForSet = new LinkedHashSet<>();
    
    public static void loadSet(Collection<Integer> set, int loadSize) {
        if (randomValuesForSet.size() < loadSize) {
            while (randomValuesForSet.size() < loadSize) {
                randomValuesForSet.add(randomGenerator.nextInt(loadSize));
            }
        }
        set.addAll(randomValuesForSet);
    }

    public static void load(Collection<Integer> collection, int[] randomValues) {
        for (int i = 0; i < randomValues.length; i++) {
            collection.add(randomValues[i]);
        }
    }
    
    public static void loadMap(Map<Integer,Integer> collection,int[] randomValues) {
        for (int i = 0; i < randomValues.length; i++) {
            collection.put(i, randomGenerator.nextInt(100000));
        }
    }

    public static void checkAndRecordTime(HashMap<String,Double> runtime, Collection<Integer> collection,String funcName, int value) {
        long startTime = 0;
        long endTime = 0;
        String collectionName = collection.getClass().getSimpleName();
        switch (funcName) {
            case "add":
                startTime = System.nanoTime();
                collection.add(value);
                endTime = System.nanoTime();
                break;
            case "contains":
                startTime = System.nanoTime();
                collection.contains(value);
                endTime = System.nanoTime();
                break;
            case "remove":
                startTime = System.nanoTime();
                collection.remove(value);
                endTime = System.nanoTime();
                break;
            case "clear":
                startTime = System.nanoTime();
                collection.clear();
                endTime = System.nanoTime();
                break;
        }
        recordTime(runtime, collectionName, endTime - startTime);
    }

    public static void checkAndRecordTime(HashMap<String,Double> runtime, Map<Integer,Integer> map,String funcName, int value) {
        long startTime = 0;
        long endTime = 0;
        String mapName = map.getClass().getSimpleName();
        switch (funcName) {
            case "add":
                startTime = System.nanoTime();
                map.put(value,randomGenerator.nextInt(100000));
                endTime = System.nanoTime();
                break;
            case "contains":
                startTime = System.nanoTime();
                map.containsKey(value);
                endTime = System.nanoTime();
                break;
            case "remove":
                startTime = System.nanoTime();
                map.remove(value);
                endTime = System.nanoTime();
                break;
            case "clear":
                startTime = System.nanoTime();
                map.clear();
                endTime = System.nanoTime();
                break;
        }
        recordTime(runtime, mapName, endTime - startTime);
    }

    public static void recordTime(HashMap<String,Double> map, String name, long time) {
        if (map.containsKey(name)) {
            double oldTime = map.get(name);
            map.put(name,oldTime + (double)time); 
        } else {
            map.put(name,(double)time);
        }
    }
}
