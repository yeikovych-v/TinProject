package v.yeikovych.tinprojectsp.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import v.yeikovych.tinprojectsp.dto.auth.RegisterUserDto;

public class MatchingPasswordsValidator implements ConstraintValidator<MatchingPasswords, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        RegisterUserDto user = (RegisterUserDto) o;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}