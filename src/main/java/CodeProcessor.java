import com.google.common.reflect.ClassPath;

import java.util.concurrent.Callable;

public class CodeProcessor implements Callable<String> {
    private ClassPath.ClassInfo clazz;

    public CodeProcessor(final ClassPath.ClassInfo clazz) {
        this.clazz = clazz;
    }

    @Override
    public String call() throws Exception {
        if (isAnotherClass(clazz)) {
            return null;
        }
        return clazz.load().getGenericSuperclass().getTypeName();
    }

    private boolean isAnotherClass(final ClassPath.ClassInfo info) {
        return !info.getPackageName().equals("");
    }

}
