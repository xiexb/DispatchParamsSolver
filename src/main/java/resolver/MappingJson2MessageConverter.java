package resolver;

import com.alibaba.fastjson.JSONObject;
import exception.DispatcherMessageNotReadableException;

/**
 * @author Administrator
 * @date 2018/11/1 0001
 */
public class MappingJson2MessageConverter extends AbstractJsonDispatcherMessageConverter<Object> {
    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public boolean canRead(Class<?> clazz) {
        return true;
    }

    @Override
    public Object read(Class<?> clazz, JSONObject messageBody) throws DispatcherMessageNotReadableException {
        try {
            return JSONObject.toJavaObject(messageBody, clazz);
        } catch (Exception e) {
            throw new DispatcherMessageNotReadableException("read messageBody error");
        }
    }
}
