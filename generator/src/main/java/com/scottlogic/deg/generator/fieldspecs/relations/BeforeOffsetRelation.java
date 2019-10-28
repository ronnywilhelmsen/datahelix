package com.scottlogic.deg.generator.fieldspecs.relations;

import com.scottlogic.deg.common.profile.Field;
import com.scottlogic.deg.common.profile.Granularity;
import com.scottlogic.deg.common.util.defaults.LinearDefaults;
import com.scottlogic.deg.generator.fieldspecs.*;
import com.scottlogic.deg.generator.generation.databags.DataBagValue;
import com.scottlogic.deg.generator.profile.constraints.Constraint;
import com.scottlogic.deg.generator.restrictions.linear.LinearRestrictions;

public class BeforeOffsetRelation <T extends Comparable<T>> implements FieldSpecRelations {
    private final Field main;
    private final Field other;
    private final boolean inclusive;
    private final LinearDefaults<T> defaults;
    private final Granularity<T> offsetGranularity;
    private final int offset;

    public BeforeOffsetRelation(Field main, Field other, boolean inclusive, LinearDefaults<T> defaults, Granularity<T> offsetGranularity, int offset) {
        this.main = main;
        this.other = other;
        this.inclusive = inclusive;
        this.defaults = defaults;
        this.offsetGranularity = offsetGranularity;
        this.offset = offset;
    }

    @Override
    public FieldSpec createModifierFromOtherFieldSpec(FieldSpec otherFieldSpec) {
        if (otherFieldSpec instanceof NullOnlyFieldSpec){
            return FieldSpecFactory.nullOnly();
        }
        if (otherFieldSpec instanceof WhitelistFieldSpec) {
            throw new UnsupportedOperationException("cannot combine sets with before relation, Issue #1489");
        }

        LinearRestrictions<T> otherRestrictions = (LinearRestrictions)((RestrictionsFieldSpec) otherFieldSpec).getRestrictions();
        T max = otherRestrictions.getMax();
        T offsetMax = offsetGranularity.getPrevious(max, offset);

        return createFromMax(offsetMax, otherRestrictions.getGranularity());
    }

    @Override
    public FieldSpec createModifierFromOtherValue(DataBagValue otherFieldGeneratedValue) {
        if (otherFieldGeneratedValue.getValue() == null) return FieldSpecFactory.fromType(main.getType());

        T offsetValue = offsetGranularity.getPrevious((T) otherFieldGeneratedValue.getValue(), offset);
        return  createFromMax(offsetValue, defaults.granularity());
    }

    private FieldSpec createFromMax(T max, Granularity<T> granularity) {
        if (!inclusive){
            max = granularity.getPrevious(max);
        }

        return FieldSpecFactory.fromRestriction(new LinearRestrictions<>(defaults.min(), max, granularity));
    }

    @Override
    public Field main() {
        return main;
    }

    @Override
    public Field other() {
        return other;
    }

    @Override
    public FieldSpecRelations inverse() {
         return new AfterOffsetRelation(other, main, inclusive, defaults, offsetGranularity,-1 * offset);
    }

    @Override
    public String toString() {
        return String.format("%s is before %s%s %s %s", main, inclusive ? "or equal to " : "", other, offset >= 0 ? "plus" : "minus", Math.abs(offset));
    }

    @Override
    public Constraint negate() {
        throw new UnsupportedOperationException("Negating relations with an offset is not supported");
    }
}
