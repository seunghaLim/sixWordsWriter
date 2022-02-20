package toyProject.sixWordsWriter.annotation;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // 1
@Retention(RetentionPolicy.RUNTIME) // 2
@Constraint(validatedBy = sixWordsValidator.class) // 3
public @interface wordsLimit {
    String message() default "6단어로 입력해주세요"; // 4
    Class[] groups() default {};
    Class[] payload() default {};
}

