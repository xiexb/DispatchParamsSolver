package resolver;

import exception.DispatcherMessageNotReadableException;
import org.springframework.core.MethodParameter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
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

    protected <T> Object readWithMessageConverters(DispatchMapMessage inputMessage, MethodParameter parameter, Type targetType) {
        Class<?> contextClass = (parameter != null ? parameter.getContainingClass() : null);
        Class<T> targetClass = (targetType instanceof Class ? (Class<T>) targetType : null);
        Object body = NO_VALUE;
        try {
            for (DispatcherMessageConverter<?> converter : this.messageConverters) {
                AbstractGenericDispatcherMessageConverter<?> genericConverter = (AbstractGenericDispatcherMessageConverter<?>) converter;
                 if (genericConverter.canRead(contextClass)) {
                    if (inputMessage.getBody() != null) {
                        body = genericConverter.read(contextClass, inputMessage);
                    } else {
                        System.out.println("message body is empty");
                    }
                    break;
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
