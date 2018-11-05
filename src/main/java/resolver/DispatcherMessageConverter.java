package resolver;

import com.alibaba.fastjson.JSONObject;
import exception.DispatcherMessageNotReadableException;

import java.io.IOException;

/**
 * @author Administrator
 */
public interface DispatcherMessageConverter<T> {

    boolean canRead(Class<?> clazz);

    T read(Class<?> clazz, JSONObject messageBody)
            throws IOException, DispatcherMessageNotReadableException;

}
