package com.example.rxjavalessonone.utils.validation_utils;

public class ValidatorImpl implements IValidator {

    public ValidatorImpl() {
    }

    @Override
    public boolean isLoginValid(String login) {
        return login.length() >= 8;
    }

    public boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }
}
