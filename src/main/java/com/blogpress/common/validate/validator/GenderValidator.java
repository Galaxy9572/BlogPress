package com.blogpress.common.validate.validator;

import com.blogpress.user.enums.GenderEnum;
import com.blogpress.common.validate.annotation.GenderValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 性别校验器
 * @author JY
 */
public class GenderValidator implements ConstraintValidator<GenderValid, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return GenderEnum.getByCode(value) != null;
    }
}
