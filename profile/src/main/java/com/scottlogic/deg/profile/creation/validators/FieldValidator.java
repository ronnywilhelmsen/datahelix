package com.scottlogic.deg.profile.creation.validators;

import com.scottlogic.deg.common.validators.ValidationResult;
import com.scottlogic.deg.common.validators.Validator;
import com.scottlogic.deg.profile.creation.dtos.FieldDTO;

public class FieldValidator implements Validator<FieldDTO>
{
    @Override
    public ValidationResult validate(FieldDTO field)
    {
        ValidationResult fieldMustBeSpecified = fieldMustBeSpecified(field);
        if(!fieldMustBeSpecified.isValid) return fieldMustBeSpecified;
        return ValidationResult.combine(nameMustBeSpecified(field), typeMustBeSpecified(field));
    }

    private ValidationResult fieldMustBeSpecified(FieldDTO field)
    {
        return field != null
            ? ValidationResult.success()
            : ValidationResult.failure("Field must not be null");

    }

    private ValidationResult nameMustBeSpecified(FieldDTO field)
    {
        return field.name != null && !field.name.isEmpty()
            ? ValidationResult.success()
            : ValidationResult.failure("Field name must be specified");

    }

    private ValidationResult typeMustBeSpecified(FieldDTO field)
    {
        return field.type != null
            ? ValidationResult.success()
            : ValidationResult.failure("Field type must be specified | Field: "
            + (field.name == null ? "Unnamed field" : field.name));
    }

}

