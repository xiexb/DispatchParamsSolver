package resolver;

import exception.DispatcherMessageNotReadableException;

import java.io.IOException;

/**
 * @author Administrator
 */
public interface DispatcherMessageConverter<T> {

    boolean canRead(Class<?> clazz);

    T read(Class<?> clazz, DispatchMapMessage inputMessage)
            throws IOException, DispatcherMessageNotReadableException;
}
