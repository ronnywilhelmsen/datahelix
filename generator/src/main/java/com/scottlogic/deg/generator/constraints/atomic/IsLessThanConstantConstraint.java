package com.scottlogic.deg.generator.constraints.atomic;

import com.scottlogic.deg.generator.Field;
import com.scottlogic.deg.generator.constraints.ConstraintRule;

import java.util.Objects;

public class IsLessThanConstantConstraint implements AtomicConstraint {
    public final Field field;
    private final ConstraintRule rule;
    public final Number referenceValue;

    public IsLessThanConstantConstraint(Field field, Number referenceValue, ConstraintRule rule) {
        this.referenceValue = referenceValue;
        this.field = field;
        this.rule = rule;
    }

    @Override
    public String toDotLabel() {
        return String.format("%s < %s", field.name, referenceValue);
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o instanceof ViolatedAtomicConstraint) {
            return o.equals(this);
        }
        if (o == null || getClass() != o.getClass()) return false;
        IsLessThanConstantConstraint constraint = (IsLessThanConstantConstraint) o;
        return Objects.equals(field, constraint.field) && Objects.equals(referenceValue, constraint.referenceValue);
    }

    @Override
    public int hashCode(){
        return Objects.hash(field, referenceValue);
    }

    @Override
    public String toString() { return String.format("`%s` < %s", field.name, referenceValue); }

    @Override
    public ConstraintRule getRule() {
        return rule;
    }
}
