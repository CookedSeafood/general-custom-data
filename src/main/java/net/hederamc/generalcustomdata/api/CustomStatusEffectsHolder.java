package net.hederamc.generalcustomdata.api;

import java.util.function.Consumer;
import java.util.function.Function;
import net.hederamc.generalcustomdata.effect.CustomStatusEffect;
import net.hederamc.generalcustomdata.effect.CustomStatusEffectIdentifier;
import net.hederamc.generalcustomdata.effect.CustomStatusEffectManager;
import net.minecraft.nbt.ListTag;

public interface CustomStatusEffectsHolder {
    default ListTag getCustomStatusEffects() {
        throw new UnsupportedOperationException();
    }

    default void setCustomStatusEffects(ListTag statusEffects) {
        throw new UnsupportedOperationException();
    }

    default CustomStatusEffectManager getCustomStatusEffectManager() {
        return CustomStatusEffectManager.fromHolder(this);
    }

    default boolean hasCustomStatusEffect(CustomStatusEffectIdentifier id) {
        return this.getCustomStatusEffectManager().contains(id);
    }

    default <T> T modifyCustomStatusEffectManager(Function<CustomStatusEffectManager, T> action) {
        CustomStatusEffectManager manager = this.getCustomStatusEffectManager();

        T result = action.apply(manager);

        this.setCustomStatusEffects(manager.toNbt());
        return result;
    }

    default void modifyCustomStatusEffectManager(Consumer<CustomStatusEffectManager> action) {
        CustomStatusEffectManager manager = this.getCustomStatusEffectManager();

        action.accept(manager);

        this.setCustomStatusEffects(manager.toNbt());
    }

    default boolean addCustomStatusEffect(CustomStatusEffect statusEffect) {
        return this.modifyCustomStatusEffectManager((Function<CustomStatusEffectManager, Boolean>)statusEffect::addTo);
    }

    default boolean setCustomStatusEffect(CustomStatusEffect statusEffect) {
        return this.modifyCustomStatusEffectManager(statusEffect::setTo);
    }

    default boolean removeCustomStatusEffect(CustomStatusEffectIdentifier id) {
        return this.modifyCustomStatusEffectManager(id::removeFrom);
    }

    default void clearCustomStatusEffect() {
        this.modifyCustomStatusEffectManager(CustomStatusEffectManager::clear);
    }

    default void tickCustomStatusEffect() {
        this.modifyCustomStatusEffectManager(CustomStatusEffectManager::tick);
    }
}
