package sender;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestBody {
    String value() default "";

    int level() default 1;

    boolean required() default true;
}
