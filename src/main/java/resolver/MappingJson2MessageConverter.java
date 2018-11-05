package resolver;

import exception.DispatcherMessageNotReadableException;

import java.io.IOException;

/**
 * @author Administrator
 * @date 2018/11/1 0001
 */
public class MappingJson2MessageConverter extends AbstractGenericDispatcherMessageConverter<Object> {
    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public boolean canRead(Class<?> clazz) {
        return true;
    }

    @Override
    public Object read(Class<?> clazz, DispatchMapMessage inputMessage) throws IOException, DispatcherMessageNotReadableException {
        return null;
    }
}
