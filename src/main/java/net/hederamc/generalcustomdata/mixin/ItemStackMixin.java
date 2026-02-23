package net.hederamc.generalcustomdata.mixin;

import net.hederamc.generalcustomdata.api.CustomIdHolder;
import net.hederamc.generalcustomdata.api.CustomModifiersHolder;
import net.hederamc.generalcustomdata.api.CustomRarityHolder;
import net.hederamc.generalcustomdata.api.CustomStatusEffectsHolder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin
        implements CustomIdHolder, CustomRarityHolder, CustomModifiersHolder, CustomStatusEffectsHolder {
    @Override
    public Identifier getCustomIdOrId() {
        Identifier customId = this.getCustomId();

        if (customId == null) {
            return this.getItem().getId();
        }

        return customId;
    }

    @Shadow
    public abstract Item getItem();
}
