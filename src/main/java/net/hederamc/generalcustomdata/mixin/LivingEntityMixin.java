package net.hederamc.generalcustomdata.mixin;

import net.hederamc.generalcustomdata.api.CustomModifiersHolder;
import net.hederamc.generalcustomdata.api.CustomStatusEffectsHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements CustomModifiersHolder, CustomStatusEffectsHolder {
    @Inject(
        method = "tick()V",
        at = @At("TAIL")
    )
    private void tickCustomStatusEffect(CallbackInfo info) {
        if (((LivingEntity)(Object)this).level().isClientSide()) {
            return;
        }

        this.tickCustomStatusEffect();
    }

    @Inject(
        method = "completeUsingItem()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;finishUsingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;",
            shift = At.Shift.AFTER
        )
    )
    private void applyItemCustomStatusEffects(CallbackInfo info) {
        this.addAllCustomStatusEffect(this.getActiveItem().getCustomStatusEffectManager());
    }

    @Override
    public ListTag getCustomModifiers() {
        ListTag modifiers = this.getCustomDataOrEmpty().tag().getListOrEmpty("modifiers");
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            String name = slot.getName();
            this.getItemBySlot(slot).getCustomModifiers().stream()
                .map(CompoundTag.class::cast)
                .filter(modifier -> modifier.getStringOr("slot", name).equals(name))
                .forEach(modifier -> modifiers.add(modifier));
        }

        return modifiers;
    }

    @Shadow
    public abstract ItemStack getActiveItem();

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);
}
