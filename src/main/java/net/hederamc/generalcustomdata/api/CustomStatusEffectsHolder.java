package net.hederamc.generalcustomdata.api;

import java.util.function.Consumer;
import java.util.function.Function;
import net.hederamc.fishbonetrehalose.api.CustomDataHolder;
import net.hederamc.generalcustomdata.effect.CustomStatusEffect;
import net.hederamc.generalcustomdata.effect.CustomStatusEffectIdentifier;
import net.hederamc.generalcustomdata.effect.CustomStatusEffectManager;
import net.minecraft.nbt.ListTag;
import org.jspecify.annotations.Nullable;

public interface CustomStatusEffectsHolder extends CustomDataHolder {
    default ListTag getCustomStatusEffects() {
        return this.getCustomDataOrEmpty().getTag().getListOrEmpty("status_effects");
    }

    default void setCustomStatusEffects(ListTag statusEffects) {
        this.getOrCreateCustomData().getTag().put("status_effects", statusEffects);
    }

    @Nullable
    default ListTag removeCustomStatusEffects() {
        return (ListTag)this.getCustomDataOrEmpty().getTag().remove("status_effects");
    }

    default CustomStatusEffectManager getCustomStatusEffectManager() {
        return CustomStatusEffectManager.fromNbt(this.getCustomStatusEffects());
    }

    default boolean hasCustomStatusEffect(CustomStatusEffectIdentifier id) {
        return this.getCustomStatusEffectManager().contains(id);
    }

    /**
     * Returns the effect of which the specific id is the id, or
     * {@code null} if this holder has no such effect.
     *
     * <p>More formally, if this holder has an effect with an id
     * {@code i} such that {@code Objects.equals(id, i)},
     * then this method returns the effect; otherwise it
     * returns {@code null}.  (There can be at most one such effect.)
     *
     * The returned effect will not be ticked, nor be backed up by
     * any storage.
     *
     * @param id the id whose associated effect is to be returned
     * @return the effect of which the specific id is the id, or
     *         {@code null} if this holder has no such effect
     */
    @Nullable
    default CustomStatusEffect getCustomStatusEffect(CustomStatusEffectIdentifier id) {
        return this.getCustomStatusEffectManager().get(id);
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

    default boolean addAllCustomStatusEffect(CustomStatusEffectManager manager) {
        return this.modifyCustomStatusEffectManager(manager::addAllTo);
    }
}
