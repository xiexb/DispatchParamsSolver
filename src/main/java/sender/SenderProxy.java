package sender;

import java.lang.reflect.Proxy;

/**
 * @author Administrator
 * @date 2018/9/7 0007
 */
public class SenderProxy {
    private Sender realSender;

    public SenderProxy(Sender realSender) {
        this.realSender = realSender;
    }

    public Sender create() {
        final Class[] interfaces = new Class[]{Sender.class};
        SenderInvokeHandler senderInvokeHandler = new SenderInvokeHandler(realSender);
        return (Sender) Proxy.newProxyInstance(Sender.class.getClassLoader(), interfaces, senderInvokeHandler);
    }
}
