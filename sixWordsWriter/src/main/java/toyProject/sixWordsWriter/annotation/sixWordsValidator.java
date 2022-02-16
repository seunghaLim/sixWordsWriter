package toyProject.sixWordsWriter.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class sixWordsValidator implements ConstraintValidator<sixWords, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }

        String[] result = value.split(" ");

        if (result.length == 6){
            return true;
        } else {
            return false;
        }



    }
}
