import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DIvanets
 * @version 1.0 30.12.2017.
 */
public class Collector {
    public Map<String, List<String>> counter = new HashMap<>();
    private static Collector ourInstance = new Collector();

    public static Collector getInstance() {
        return ourInstance;
    }

    public synchronized void add(final String parent, final String child) {
        if (counter.containsKey(parent)) {
            counter.get(parent).add(child);
        } else {
            addExisting(parent, child);
        }
    }

    public synchronized Map<String, List<String>> getCounter() {
        return counter;
    }

    private synchronized void addExisting(final String parent, final String child) {
        List<String> list = new ArrayList<>();
        list.add(child);
        counter.put(parent, list);
    }

    private Collector() {
    }
}
