package resolver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.ClassUtils;
import sender.RealSender;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Administrator
 * @date 2018/10/10 0010
 */
@Setter
@Getter
public class DispatcherMethod {
    private final Object bean;

    private final Class<?> beanType;

    private final Method method;

    private final DispatcherMethodParameter[] parameters;

    public DispatcherMethod(Object bean, Method method) {
        this.bean = bean;
        this.beanType = ClassUtils.getUserClass(bean);
        this.method = method;
        this.parameters = initMethodParameters();
    }

//    private static void resolveParameterType(DispatcherMethodParameter methodParameter) {
//        methodParameter.setParameterType(
//                ResolvableType.forType(m).resolve());
//    }

    //    Response invoke(Request request) {
//        //获取 分发方法的所有参数
//        Object[] args = null;
//        Object ret = method.invoke(bean, args);
//        //ret 解析成 response
//        Response response = adapter(Object);
//    }
    public static void main(String[] args) throws Exception {
        RealSender realSender = new RealSender();

        ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        Method[] methods = RealSender.class.getMethods();
        for(Method method : methods) {
            System.out.println(method.getName() + " " + Arrays.toString(method.getParameterTypes()));
            String[] names = discoverer.getParameterNames(method);
            for(String name : names) {
                System.out.print(name + ',');
            }
            System.out.println();
            System.out.println("--------------------------");
        }

//
//    try {
//        DispatcherMethod dispatcherMethod = new DispatcherMethod(realSender, RealSender.class.getMethod("send", String.class));
//        System.out.println(dispatcherMethod.getBeanType());
//    } catch (NoSuchMethodException e) {
//        e.printStackTrace();
//    }
    }

    private DispatcherMethodParameter[] initMethodParameters() {
        int count = method.getParameterCount();
        DispatcherMethodParameter[] result = new DispatcherMethodParameter[count];
        for (int i = 0; i < count; i++) {
            MethodParameter methodParameter = new MethodParameter(method, i);
            System.out.println(methodParameter.toString());
//            DispatcherMethodParameter parameter = new DispatcherMethodParameter(method, i);
//            resolveParameterType(parameter);
//            result[i] = parameter;
        }
        return result;
    }
}
