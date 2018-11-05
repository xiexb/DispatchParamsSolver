package resolver;

import com.alibaba.fastjson.JSONObject;
import exception.DispatcherMessageNotReadableException;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Administrator
 * @date 2018/10/31 0031
 */
public abstract class AbstractMessageConverterMethodArgumentResolver implements DispatcherMethodArgumentResolver {
    private static final Object NO_VALUE = new Object();
    protected final List<DispatcherMessageConverter<?>> messageConverters;

    public AbstractMessageConverterMethodArgumentResolver(List<DispatcherMessageConverter<?>> converters) {
        this.messageConverters = converters;
    }

    protected Object adaptArgumentIfNecessary(Object arg, MethodParameter parameter) {
        return (parameter.isOptional() ? OptionalResolver.resolveValue(arg) : arg);
    }

    protected <T> Object readWithMessageConverters(DispatchMessage inputMessage, MethodParameter parameter, Type targetType) {
        Class<T> targetClass = (targetType instanceof Class ? (Class<T>) targetType : null);
        if (targetClass == null) {
            ResolvableType resolvableType = (parameter != null ?
                    ResolvableType.forMethodParameter(parameter) : ResolvableType.forType(targetType));
            targetClass = (Class<T>) resolvableType.resolve();
        }

        Object body = NO_VALUE;
        try {
            for (DispatcherMessageConverter<?> converter : this.messageConverters) {
                if (targetClass != null) {
                    if (converter.canRead(targetClass)) {
                        JSONObject messageBody = inputMessage.getBody();
                        if (messageBody != null) {
                            body = converter.read(targetClass, messageBody);
                        } else {
                            System.out.println("message body is empty");
                        }
                    }
                }
            }
        } catch (IOException ex) {
            throw new DispatcherMessageNotReadableException("Could not read document: " + ex.getMessage());
        }
        return body;
    }

    private static class OptionalResolver {
        public static Object resolveValue(Object value) {
            if (value == null || (value instanceof Collection && ((Collection) value).isEmpty()) ||
                    (value instanceof Object[] && ((Object[]) value).length == 0)) {
                return Optional.empty();
            }
            return Optional.of(value);
        }
    }
}
