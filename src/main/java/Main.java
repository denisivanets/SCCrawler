import com.google.common.reflect.ClassPath;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Set<ClassPath.ClassInfo> classes =  ClassPath.from(ClassLoader.getSystemClassLoader()).getAllClasses();
        for (ClassPath.ClassInfo clazz : classes) {
            Future<String> result = executorService.submit(new CodeProcessor(clazz));
            if (result.get() != null) {
                Collector.getInstance().add(result.get(), clazz.getName());
            }
        }
        executorService.shutdown();
        printResult();
    }

    private static void printResult() {
        for (Map.Entry<String, List<String>> entry : Collector.getInstance().getCounter().entrySet()) {
            if (!isRightObject(entry.getKey())) {
                System.out.println(entry.getKey() + ": " + printChild(entry.getValue()));
            }
        }
    }

    private static boolean isRightObject(final String clazz) {
        return clazz.endsWith("Object");
    }

    private static String printChild(final List<String> classes) {
        return String.join(", ", classes);
    }

}

