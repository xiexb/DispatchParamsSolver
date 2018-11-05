package resolver;

import exception.DispatcherMessageNotReadableException;
import org.springframework.core.MethodParameter;
import sender.DispatcherConverter;
import sender.RequestBody;

import java.util.Arrays;

/**
 * @author Administrator
 * @date 2018/10/31 0031
 */
public class RequestResponseBodyMethodProcessor extends AbstractMessageConverterMethodArgumentResolver {

    public RequestResponseBodyMethodProcessor() {
        super(Arrays.asList(new MappingJson2MessageConverter()));
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(RequestBody.class)) {
            Class<?> nestedParameterType = parameter.nestedIfOptional().getNestedParameterType();
            return nestedParameterType.getClass().isAnnotationPresent(DispatcherConverter.class);
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, DispatchRequest request) {
        parameter = parameter.nestedIfOptional();
        Object arg = readWithMessageConverters(request, parameter);
        return adaptArgumentIfNecessary(arg, parameter);
    }


    protected <T> Object readWithMessageConverters(DispatchRequest request, MethodParameter parameter) throws DispatcherMessageNotReadableException {
        DispatchMapMessage inputMessage = new JsonObjectDispatcherMapMessage(request);
        Object arg = readWithMessageConverters(inputMessage, parameter, parameter.getParameterType());
        if (arg == null) {
            if (checkRequired(parameter)) {
                throw new DispatcherMessageNotReadableException("Required request body is missing: " + parameter.getMethod().toGenericString());
            }
        }
        return arg;
    }

    protected boolean checkRequired(MethodParameter parameter) {
        return (parameter.getParameterAnnotation(org.springframework.web.bind.annotation.RequestBody.class).required() && !parameter.isOptional());
    }
}
