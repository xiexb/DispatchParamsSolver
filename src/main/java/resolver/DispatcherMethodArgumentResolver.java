package resolver;

import org.springframework.core.MethodParameter;

/**
 * @author Administrator
 * @date 2018/10/10 0010
 */
public interface DispatcherMethodArgumentResolver {

    /**
     * 是否支持此参数
     *
     * @param parameter
     * @return
     */
    boolean supportsParameter(MethodParameter parameter);


    /**
     * 解析参数
     *
     * @param parameter
     * @param request
     * @return
     * @throws Exception
     */
    Object resolveArgument(MethodParameter parameter, DispatchRequest request) throws Exception;
}
