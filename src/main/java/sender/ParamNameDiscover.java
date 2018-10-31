package sender;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @date 2018/10/8 0008
 */
public class ParamNameDiscover {

    public static Map<Integer, Method> mapper = new HashMap<>(10);

    public static void main(String[] args) {

        Map<String, Object> data = new HashMap<>(10);
        data.put("sex", "female");
        data.put("world", "China");
        data.put("yes", new RealSender());
        data.put("hello", "ye");

        Object[] methodArgs = new Object[10];
        Method[] methods = ParamNameDiscover.class.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Command.class)) {
                Command cmd = method.getAnnotation(Command.class);
                int[] type = cmd.value();
                for (int aType : type) {
                    mapper.put(aType, method);
                }

                Parameter[] parameters = method.getParameters();
                for (Parameter parameter : parameters) {
                    if (parameter.isAnnotationPresent(RequestParam.class)) {
                        System.out.print(parameter.toString() + " ");
                        System.out.println(parameter.isNamePresent());
                    }
                }

                System.out.println("---------------------------------");
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                for (Annotation[] parameterAnnotation : parameterAnnotations) {
                    System.out.println(parameterAnnotation.length);
                    if (parameterAnnotation.length == 0) {

                    } else {
                        for (Annotation annotation : parameterAnnotation) {
                            if (RequestParam.class.equals(annotation.annotationType())) {
                                RequestParam parameter = (RequestParam) annotation;
                                String paramValue = parameter.value();
                                if (paramValue != null) {
                                    System.out.println(parameter.value());
                                } else {

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Command(1)
    public static void test1(@RequestAttribute Sender sender, @RequestParam String hello, int world, @RequestParam("yes") String come) {
        System.out.println(hello + " " + world + " " + come);
        sender.send("apple");
    }

    @Command({2, 3})
    public static void test2(String on) {
//        DispatcherMethodParameter
        System.out.println();
    }

    public String[] getParameterName(int type) {
        ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = null;
        try {
            Method tMethod = mapper.get(type);
            parameterNames = parameterNameDiscoverer.getParameterNames(tMethod);
            System.out.println("method name : " + tMethod.getName());
            for (String parameterName : parameterNames) {
                System.out.print(parameterName + ' ');
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return parameterNames;
    }
}
