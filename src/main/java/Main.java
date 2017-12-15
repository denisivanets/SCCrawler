import com.google.common.reflect.ClassPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private Map<String, List<String>> counter = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.inspectCode();
        main.printResult();
    }

    private void inspectCode() throws Exception {
        ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .forEach(this::processClass);
    }

    private void processClass(final ClassPath.ClassInfo clazz) {
        String parent = clazz.load().getGenericSuperclass().getTypeName();
        if (!counter.containsKey(parent)) {
            List<String> childClasses = new ArrayList<>();
            childClasses.add(clazz.getName());
            counter.put(parent, childClasses);
        } else {
            counter.get(parent).add(clazz.getName());
        }
    }

    private void printResult() {
        for (Map.Entry<String, List<String>> entry : counter.entrySet()) {
            System.out.println(entry.getKey() + ": " + printChild(entry.getValue()));
        }
    }

    private String printChild(final List<String> classes) {
        return String.join(", ", classes);
    }

}

