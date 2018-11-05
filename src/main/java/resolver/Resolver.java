package resolver;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import sender.ParamNameDiscover;
import sender.Seller;
import sender.Sender;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author Administrator
 * @date 2018/10/10 0010
 */
public class Resolver {

    private static DispatcherMethodArgumentResolver[] dispatcherMethodArgumentResolvers = new DispatcherMethodArgumentResolver[]{
            new RequestResponseBodyMethodProcessor(),
            new RequestParamMethodArgumentResolver(true),
            new RequestAttributeMethodArgumentResolver()
    };

    public static void main(String[] args) throws Exception {
        String nativeMsg = "{\"hello\":\"yeye\",\"world\":114,\"yes\":\"come\",\"come\":\"baby\",\"seller\":{\"name\":\"yoyo\",\"age\":22}}";
        DispatchRequest dispatchRequest = new DispatchRequest(nativeMsg);
        ParamNameDiscover paramNameDiscover = new ParamNameDiscover();
        Method method = ParamNameDiscover.class.getMethod("test1", Seller.class, Sender.class, String.class, int.class, String.class);

        Parameter[] parameters = method.getParameters();
        Object[] methodArgs = new Object[parameters.length];
        ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        for (int i = 0; i < parameters.length; i++) {
            MethodParameter methodParameter = new SynthesizingMethodParameter(method, i);
            methodParameter.initParameterNameDiscovery(parameterNameDiscoverer);
            DispatcherMethodArgumentResolver supportResolver = supportResolver(methodParameter);
            if (supportResolver != null) {
                Object argument = supportResolver.resolveArgument(methodParameter, dispatchRequest);
                System.out.println(argument);
                methodArgs[i] = argument;
            } else {
                System.out.println("no Resolver support the methodParameter !!!");
                return;
            }
        }

        method.invoke(paramNameDiscover, methodArgs);
    }

    private static DispatcherMethodArgumentResolver supportResolver(MethodParameter methodParameter) {
        for (DispatcherMethodArgumentResolver dispatcherMethodArgumentResolver : dispatcherMethodArgumentResolvers) {
            if (dispatcherMethodArgumentResolver.supportsParameter(methodParameter)) {
                return dispatcherMethodArgumentResolver;
            }
        }
        return null;
    }
}
