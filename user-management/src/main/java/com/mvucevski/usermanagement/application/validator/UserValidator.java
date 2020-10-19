package com.mvucevski.usermanagement.application.validator;

import com.mvucevski.usermanagement.port.payload.RegisterRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterRequest request = (RegisterRequest) target;

        if(request.getPassword().length() < 6){
            errors.rejectValue("password","Length", "Password must be at least 6 characters");
        }

        if(!request.getPassword().equals(request.getConfirmPassword())){
            errors.rejectValue("confirmPassword", "Match", "Passwords must match");
        }

    }
}
