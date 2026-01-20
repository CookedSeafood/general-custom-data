package net.hederamc.generalcustomdata.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.apache.commons.lang3.mutable.MutableDouble;

public interface CustomModifiersHolder {
    default double getCustomModifiedValue(String attribute, double base) {
        ListTag modifiers = this.getCustomModifiers(attribute);
        MutableDouble modified = new MutableDouble(base);

        modifiers.stream()
            .map(CompoundTag.class::cast)
            .filter(modifier -> "add_value".equals(modifier.getStringOr("operation", "add_value")))
            .forEach(modifier -> modified.add(modifier.getDoubleOr("base", 0.0)));

        MutableDouble multiplier = new MutableDouble(1.0);

        modifiers.stream()
            .map(CompoundTag.class::cast)
            .filter(modifier -> "add_multiplied_base".equals(modifier.getStringOr("operation", "add_value")))
            .forEach(modifier -> multiplier.add(modifier.getDoubleOr("base", 0.0)));

        modified.setValue(modified.doubleValue() * multiplier.doubleValue());

        modifiers.stream()
            .map(CompoundTag.class::cast)
            .filter(modifier -> "add_multiplied_total".equals(modifier.getStringOr("operation", "add_value")))
            .forEach(modifier -> modified.setValue((1.0 + modifier.getDoubleOr("base", 0.0)) * modified.doubleValue()));

        return modified.doubleValue();
    }

    default ListTag getCustomModifiers(String attribute) {
        return this.getCustomModifiers().parallelStream()
            .map(CompoundTag.class::cast)
            .filter(modifier -> attribute.equals(modifier.getStringOr("attribute", "")))
            .collect(ListTag::new, ListTag::add, ListTag::addAll);
    }

    default ListTag getCustomModifiers() {
        throw new UnsupportedOperationException();
    }
}
