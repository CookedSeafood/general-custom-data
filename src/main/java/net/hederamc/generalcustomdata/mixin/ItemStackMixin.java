package net.hederamc.generalcustomdata.mixin;

import net.hederamc.generalcustomdata.api.CustomIdHolder;
import net.hederamc.generalcustomdata.api.CustomModifiersHolder;
import net.hederamc.generalcustomdata.api.CustomRarityHolder;
import net.hederamc.generalcustomdata.api.CustomStatusEffectsHolder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements CustomIdHolder, CustomRarityHolder, CustomModifiersHolder, CustomStatusEffectsHolder {
    @Override
    public Identifier getCustomIdOrId() {
        Identifier customId = this.getCustomId();

        if (customId == null) {
            return this.getItem().id();
        }

        return customId;
    }

    @Override
    public String getCustomRarityOrRarity() {
        String customRarity = this.getCustomRarity();

        if (customRarity == null) {
            return this.getRarity().getSerializedName();
        }

        return customRarity;
    }

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract Rarity getRarity();
}
