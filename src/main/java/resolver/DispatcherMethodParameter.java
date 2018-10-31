package resolver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author Administrator
 * @date 2018/10/10 0010
 */
@Setter
@Getter
public class DispatcherMethodParameter {

    private final Method method;

    private final int parameterIndex;

    private volatile Class<?> parameterType;

    private volatile Type genericParameterType;

    private volatile Annotation[] parameterAnnotations;

    private volatile ParameterNameDiscoverer parameterNameDiscoverer;

    private volatile String parameterName;

    public DispatcherMethodParameter(Method method, int parameterIndex) {
        this.method = method;
        this.parameterIndex = parameterIndex;
    }
}
