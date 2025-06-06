package voltaic.api.configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FloatValue {
	String comment() default "";

	float def();
}
