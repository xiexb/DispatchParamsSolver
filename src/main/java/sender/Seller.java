package sender;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Administrator
 * @date 2018/11/1 0001
 */
@Getter
@Setter
@ToString
@DispatcherConverter
public class Seller {
    String name;

    int age;

    String fruit;
}
