package com.blogpress.common.validate.validator;

import com.blogpress.common.validate.annotation.DateValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 日期校验器
 * @author JY
 */
public class DateValidator implements ConstraintValidator<DateValid, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDate.parse(value, FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
