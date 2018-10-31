package sender;

import java.util.Random;

class Initable1 {
    static final int STATIC_FINAL = 47;
    static final int STATIC_FINAL2 = new Random(47).nextInt(100);

    static {
        System.out.println("sender.Initable1 init");
    }
}

class Initable2 {
    static int STATIC_FINAL = 147;

    static {
        System.out.println("sender.Initable2 init");
    }
}

class Initable3 {
    static int STATIC_FINAL = 74;

    static {
        System.out.println("sender.Initable3 init");
    }
}

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Sender sender = new RealSender();
        SenderProxy senderProxy = new SenderProxy(sender);
        Sender senderP = senderProxy.create();
        senderP.send("fruit");


//        Class initable1 = sender.Initable1.class;
//        System.out.println("after init initable1");
//        System.out.println(sender.Initable1.STATIC_FINAL);
//        System.out.println("-------------------------");
//        System.out.println(sender.Initable1.STATIC_FINAL2);
//        System.out.println("-------------------------");
//        System.out.println(sender.Initable2.STATIC_FINAL);
//        System.out.println("-------------------------");
//        Class initable2 = Class.forName("sender.Initable3");
//        System.out.println("after init initable3");
//        System.out.println(sender.Initable3.STATIC_FINAL);
    }
}
