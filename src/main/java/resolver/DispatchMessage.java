package resolver;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * @author Administrator
 * @date 2018/10/31 0031
 */
public interface DispatchMessage {

    JSONObject getBody() throws IOException;
}
