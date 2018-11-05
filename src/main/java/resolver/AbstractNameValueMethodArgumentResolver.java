package resolver;

import org.springframework.core.MethodParameter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 * @date 2018/10/10 0010
 */
public abstract class AbstractNameValueMethodArgumentResolver implements DispatcherMethodArgumentResolver {

    private final Map<MethodParameter, NamedValueInfo> namedValueInfoCache =
            new ConcurrentHashMap<>(256);

    @Override
    public final Object resolveArgument(MethodParameter parameter, DispatchRequest request) throws Exception {
        NamedValueInfo namedValueInfo = getNamedValueInfo(parameter);
        MethodParameter nestedParameter = parameter.nestedIfOptional();
        Object arg = resolveName(namedValueInfo.name, nestedParameter, request);
        if (arg == null) {
            if (namedValueInfo.defaultValue != null) {
                arg = namedValueInfo.defaultValue;
            } else if (namedValueInfo.required && !nestedParameter.isOptional()) {
                handleMissingValue(namedValueInfo.name, nestedParameter, request);
            }
            arg = handleNullValue(namedValueInfo.name, arg, nestedParameter.getNestedParameterType());
        } else if ("".equals(arg) && namedValueInfo.defaultValue != null) {
            arg = namedValueInfo.defaultValue;
        }
        handleResolvedValue(arg, namedValueInfo.name, parameter, request);
        return arg;
    }

    protected abstract Object resolveName(String name, MethodParameter parameter, DispatchRequest request)
            throws Exception;

    protected void handleMissingValue(String name, MethodParameter parameter, DispatchRequest request) {

    }

    private Object handleNullValue(String name, Object value, Class<?> paramType) {
        if (value == null) {
            if (Boolean.TYPE.equals(paramType)) {
                return Boolean.FALSE;
            } else if (paramType.isPrimitive()) {
                throw new IllegalStateException("Optional " + paramType.getSimpleName() + " parameter '" + name +
                        "' is present but cannot be translated into a null value due to being declared as a " +
                        "primitive type. Consider declaring it as object wrapper for the corresponding primitive type.");
            }
        }
        return value;
    }

    protected void handleResolvedValue(Object arg, String name, MethodParameter parameter, DispatchRequest webRequest) {
        //do some other handle
    }

    protected abstract NamedValueInfo createNamedValueInfo(MethodParameter parameter);

    private NamedValueInfo updateNamedValueInfo(MethodParameter parameter, NamedValueInfo info) {
        String name = info.name;
        if (info.name.isEmpty()) {
            name = parameter.getParameterName();
            if (name == null) {
                throw new IllegalArgumentException(
                        "Name for argument type [" + parameter.getNestedParameterType().getName() +
                                "] not available, and parameter name information not found in class file either.");
            }
        }
        String defaultValue = (ValueConstants.DEFAULT_NONE.equals(info.defaultValue) ? null : info.defaultValue);
        return new NamedValueInfo(name, info.required, defaultValue);
    }

    private NamedValueInfo getNamedValueInfo(MethodParameter parameter) {
        NamedValueInfo namedValueInfo = this.namedValueInfoCache.get(parameter);
        if (namedValueInfo == null) {
            namedValueInfo = createNamedValueInfo(parameter);
            namedValueInfo = updateNamedValueInfo(parameter, namedValueInfo);
            this.namedValueInfoCache.put(parameter, namedValueInfo);
        }
        return namedValueInfo;
    }

    protected static class NamedValueInfo {

        private final String name;

        private final boolean required;

        private final String defaultValue;

        public NamedValueInfo(String name, boolean required, String defaultValue) {
            this.name = name;
            this.required = required;
            this.defaultValue = defaultValue;
        }
    }
}
