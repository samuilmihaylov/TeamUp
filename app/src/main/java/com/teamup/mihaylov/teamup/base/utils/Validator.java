package com.teamup.mihaylov.teamup.base.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by samui on 10.10.2017 г..
 */

public class Validator {
    public static Boolean isValidEmail(String email){
        String regex = Constants.emailRegex;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return false;
        }

        return true;
    }
}
