package resolver;

import java.io.IOException;
import java.util.Map;

/**
 * @author Administrator
 * @date 2018/10/31 0031
 */
public interface DispatchMapMessage {

    Map getBody() throws IOException;
}
