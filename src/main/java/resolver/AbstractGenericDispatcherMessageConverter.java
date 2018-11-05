package resolver;

/**
 * @author Administrator
 * @date 2018/11/1 0001
 */
public abstract class AbstractGenericDispatcherMessageConverter<T> implements DispatcherMessageConverter<T> {
    protected abstract boolean supports(Class<?> clazz);

    @Override
    public boolean canRead(Class<?> clazz) {
        return supports(clazz);
    }
}
