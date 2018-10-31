package sender;

/**
 * @author Administrator
 * @date 2018/9/7 0007
 */
public class RealSender implements Sender {
    public void testParamters(String Hello, int world) {

    }

    @Override
    public void send(String name) {
        System.out.println("now real send " + name);
    }
}
