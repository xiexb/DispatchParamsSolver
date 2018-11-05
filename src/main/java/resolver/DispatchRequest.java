package resolver;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import sender.RealSender;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Administrator
 * @date 2018/10/22 0022
 */
@Getter
@Setter
public class DispatchRequest {
    private final Map<String, Object> parameters = new LinkedHashMap<>();
    private final Map<String, Object> attributes = new LinkedHashMap<>();
    private String nativeMsg;

    public DispatchRequest(String nativeMsg) {
        this.nativeMsg = nativeMsg;
        JSONObject data = JSONObject.parseObject(nativeMsg);
        for (String key : data.keySet()) {
            parameters.put(key, data.get(key));
        }

        attributes.put("sender", new RealSender());
    }

    public Object getAttributeValue(String name) {
        return name != null ? this.attributes.get(name) : null;
    }

    public Object getParameterValue(String name) {
        return name != null ? this.parameters.get(name) : null;
    }
}
