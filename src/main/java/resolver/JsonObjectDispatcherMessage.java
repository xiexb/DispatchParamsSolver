package resolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;
import sender.RequestBody;

import java.io.IOException;

/**
 * @author Administrator
 * @date 2018/11/1 0001
 */
public class JsonObjectDispatcherMessage implements DispatchMessage {

    private DispatchRequest dispatchRequest;

    private MethodParameter methodParameter;

    public JsonObjectDispatcherMessage(DispatchRequest dispatchRequest, MethodParameter methodParameter) {
        this.dispatchRequest = dispatchRequest;
        this.methodParameter = methodParameter;
    }

    @Override
    public JSONObject getBody() throws IOException {
        JSONObject bodyJson = JSON.parseObject(dispatchRequest.getNativeMsg());
        RequestBody anno = methodParameter.getParameterAnnotation(RequestBody.class);
        if (anno.level() == 1) {
            return bodyJson;
        } else if (anno.level() == 2) {
            String name = anno.value();
            if (name.isEmpty()) {
                name = methodParameter.getParameterName();
            }
            return bodyJson.getJSONObject(name);
        } else {
            throw new IOException("convert class level too deep");
        }
    }
}
