package resolver;

import org.springframework.core.MethodParameter;
import sender.RequestAttribute;

/**
 * @author Administrator
 * @date 2018/10/31 0031
 */
public class RequestAttributeMethodArgumentResolver extends AbstractNameValueMethodArgumentResolver {
    @Override
    protected Object resolveName(String name, MethodParameter parameter, DispatchRequest request) throws Exception {
        return request.getAttributeValue(name);
    }

    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        RequestAttribute ann = parameter.getParameterAnnotation(RequestAttribute.class);
        return new NamedValueInfo(ann.value(), ann.required(), ValueConstants.DEFAULT_NONE);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestAttribute.class);
    }
}
