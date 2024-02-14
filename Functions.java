import java.util.*;

class Functions {
    static LinkedHashSet<Integer> randomValuesForSet = new LinkedHashSet<>();
    public static void loadSet(Collection<Integer> set, int loadSize) {
        if (randomValuesForSet.size() < loadSize) {
            Random randomGenerator = new Random();
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
        Random randomGenerator = new Random();
        for (int i = 0; i < randomValues.length; i++) {
            collection.put(randomValues[i], randomGenerator.nextInt(100000));
        }
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
