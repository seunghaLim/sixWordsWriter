package toyProject.sixWordsWriter.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class sixWordsValidator implements ConstraintValidator<wordsLimit, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }

        value = value.strip();
        String[] result = value.split(" ");

        if (result.length == 6){
            return true;
        } else {
            return false;
        }

    }
}
