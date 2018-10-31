package sender;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Administrator
 * @date 2018/9/7 0007
 */
public class SenderInvokeHandler implements InvocationHandler {

    private Sender realSender;

    public SenderInvokeHandler (Sender realSender) {
        this.realSender = realSender;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Talk about the price....");
        method.invoke(realSender, args);
        System.out.println("tire!!!");
        return null;
    }
}
