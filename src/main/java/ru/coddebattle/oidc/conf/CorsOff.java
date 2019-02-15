package ru.coddebattle.oidc.conf;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Igor_Petrov@epam.com
 * Created at 2/15/2019
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Profile("!cors")
public @interface CorsOff {

}
