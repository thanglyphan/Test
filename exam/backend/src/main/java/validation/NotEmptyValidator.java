package validation;

/**
 * Created by thang on 06.10.2016.
 */
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyValidator implements ConstraintValidator<NotEmpty,String> {

    @Override
    public void initialize(NotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null){
            return false;
        }

        return  ! value.trim().isEmpty();
    }
}