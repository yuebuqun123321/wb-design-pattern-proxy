package com.wb.pattern.proxy.dynamic;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: wb_cheng
 * @Date: 2019/9/17 22:07
 * @Description:
 */
public class WBProxy {

    private static final String ln = "\r\n";

    public static Object newProxyInstance(WBClassLoader classLoader, Class<?>[] interfaces, WBInvocationHandler h) {
        try {
            //动态生成源代码
            String src = generateSrc(interfaces);
            //将文件输入磁盘生成java文件
            String path = WBProxy.class.getResource("").getPath();
            File file = new File(path + "$Proxy0.java");
            FileWriter writer = new FileWriter(file);
            writer.write(src);
            writer.flush();
            writer.close();
            //将Java文件编译成字节码文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> iterable = manager.getJavaFileObjects(file);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
            task.call();
            manager.close();
            //将编译好的字节码文件加载到jvm中
            Class<?> clazz = classLoader.findClass("$Proxy0");
            Constructor<?> constructor = clazz.getConstructor(WBInvocationHandler.class);
            file.delete();
            return constructor.newInstance(h);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private static String generateSrc(Class<?>[] interfaces) {
        StringBuffer sb = new StringBuffer();
        sb.append("package com.wb.pattern.proxy.dynamic;" + ln);
        sb.append("import com.wb.pattern.proxy.Person;" + ln);
        sb.append("import java.lang.reflect.*;" + ln);
        sb.append("public class $Proxy0 implements " + interfaces[0].getName() + "{" + ln);
            sb.append("WBInvocationHandler h;" + ln);
            sb.append("public $Proxy0(WBInvocationHandler h) {" + ln);
                sb.append("this.h = h;" + ln);
            sb.append("}" + ln);
            for (Method m : interfaces[0].getDeclaredMethods()) {
                Class<?>[] params = m.getParameterTypes();
                StringBuffer paramNames = new StringBuffer();
                StringBuffer paramValues = new StringBuffer();
                StringBuffer paramClasses = new StringBuffer();
                for (int i = 0; i < params.length; i++) {
                        Class clazz = params[i];
                        String type = clazz.getName();
                        String paramName = toLowerFirstCase(clazz.getSimpleName());
                        paramNames.append(type + " " +  paramName);
                        paramValues.append(paramName);
                        paramClasses.append(clazz.getName() + ".class");
                        if(i > 0 && i < params.length-1){
                            paramNames.append(",");
                            paramClasses.append(",");
                            paramValues.append(",");
                        }
                    }

                    sb.append("public " + m.getReturnType().getName() + " " + m.getName() + "(" + paramNames.toString() + ") {" + ln);
                    sb.append("try{" + ln);
                    sb.append("Method m = " + interfaces[0].getName() + ".class.getMethod(\"" + m.getName() + "\",new Class[]{" + paramClasses.toString() + "});" + ln);
                    sb.append((hasReturnValue(m.getReturnType()) ? "return " : "") + getCaseCode("this.h.invoke(this,m,new Object[]{" + paramValues + "})",m.getReturnType()) + ";" + ln);
                    sb.append("}catch(Error _ex) { }");
                    sb.append("catch(Throwable e){" + ln);
                    sb.append("throw new UndeclaredThrowableException(e);" + ln);
                    sb.append("}");
                    sb.append(getReturnEmptyCode(m.getReturnType()));
                    sb.append("}");
                }
                sb.append("}" + ln);
                return sb.toString();
    }

    private static Map<Class,Class> mappings = new HashMap<Class, Class>();
    static {
        mappings.put(int.class,Integer.class);
    }

    private static String getReturnEmptyCode(Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return "return 0;";
        }else if(returnClass == void.class){
            return "";
        }else {
            return "return null;";
        }
    }

    private static String getCaseCode(String code,Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return "((" + mappings.get(returnClass).getName() +  ")" + code + ")." + returnClass.getSimpleName() + "Value()";
        }
        return code;
    }

    private static boolean hasReturnValue(Class<?> clazz){
        return clazz != void.class;
    }

    private static String toLowerFirstCase(String src){
        char [] chars = src.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

}
