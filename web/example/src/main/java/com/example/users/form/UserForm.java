package com.example.users.form;

import com.example.users.service.Username;
import com.example.users.service.UserManagement;
import org.springframework.validation.BindingResult;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

public interface UserForm {
    String getUsername();

    String getPassword();

    String getRepeatedPassword();

    /**
     * Validates the {@link UserForm}.
     *
     * @param errors
     * @param userManagement
     */
    default void validate(BindingResult errors, UserManagement userManagement) {

        rejectIfEmptyOrWhitespace(errors, "username", "user.username.empty");
        rejectIfEmptyOrWhitespace(errors, "password", "user.password.empty");
        rejectIfEmptyOrWhitespace(errors, "repeatedPassword", "user.repeatedPassword.empty");

        if (!getPassword().equals(getRepeatedPassword())) {
            errors.rejectValue("repeatedPassword", "user.password.no-match");
        }

        try {
            userManagement.findByUsername(new Username(getUsername())).ifPresent(
                    user -> errors.rejectValue("username", "user.username.exists"));
        } catch (IllegalArgumentException o_O) {
            errors.rejectValue("username", "user.username.invalidFormat");
        }
    }
}
