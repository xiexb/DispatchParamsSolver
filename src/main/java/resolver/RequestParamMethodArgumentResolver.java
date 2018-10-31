package resolver;

import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ValueConstants;
import sender.RequestParam;

import java.util.Map;

/**
 * @author Administrator
 * @date 2018/10/26 0026
 */
public class RequestParamMethodArgumentResolver extends AbstractNameValueMethodArgumentResolver {

    private static final TypeDescriptor STRING_TYPE_DESCRIPTOR = TypeDescriptor.valueOf(String.class);

    private final boolean useDefaultResolution;

    public RequestParamMethodArgumentResolver(boolean useDefaultResolution) {
        this.useDefaultResolution = useDefaultResolution;
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, DispatchRequest request) throws Exception {
        return request.getParameterValue(name);
    }

    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        RequestParam ann = parameter.getParameterAnnotation(RequestParam.class);
        return (ann != null ? new RequestParamNamedValueInfo(ann) : new RequestParamNamedValueInfo());
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(RequestParam.class)) {
            if (Map.class.isAssignableFrom(parameter.nestedIfOptional().getNestedParameterType())) {
                String paramName = parameter.getParameterAnnotation(RequestParam.class).value();
                return StringUtils.hasText(paramName);
            } else {
                return true;
            }
        } else {
            parameter = parameter.nestedIfOptional();
            if (this.useDefaultResolution) {
                return BeanUtils.isSimpleProperty(parameter.getNestedParameterType());
            } else {
                return false;
            }
        }
    }

    private static class RequestParamNamedValueInfo extends NamedValueInfo {

        public RequestParamNamedValueInfo() {
            super("", false, ValueConstants.DEFAULT_NONE);
        }

        public RequestParamNamedValueInfo(RequestParam annotation) {
            super(annotation.value(), annotation.required(), annotation.defaultValue());
        }
    }
}
