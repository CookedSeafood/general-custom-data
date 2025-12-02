package net.hederamc.gcd.mixin;

import java.util.HashMap;
import java.util.Map;
import net.hederamc.gcd.api.ItemStackApi;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemStackApi {
    @Override
    public String getCustomId() {
        return ((ItemStack)(Object)this).getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getString("id", "");
    }

    @Override
    public void setCustomId(String id) {
        ((ItemStack)(Object)this).set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(
            ((ItemStack)(Object)this).getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().copyFrom(
                new NbtCompound(
                    new HashMap<>(
                        Map.<String, NbtElement>of(
                            "id",
                            NbtString.of(id)
                        )
                    )
                )
            )
        ));
    }

    @Override
    public String getCustomIdOrId() {
        String customId = this.getCustomId();
        return customId == "" ? ((ItemStack)(Object)this).getIdAsString() : customId;
    }

    @Override
    public String getCustomRarity() {
        return ((ItemStack)(Object)this).getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getString("rarity", "");
    }

    @Override
    public void setCustomRarity(String rarity) {
        ((ItemStack)(Object)this).set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(
            ((ItemStack)(Object)this).getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().copyFrom(
                new NbtCompound(
                    new HashMap<>(
                        Map.<String, NbtElement>of(
                            "rarity",
                            NbtString.of(rarity)
                        )
                    )
                )
            )
        ));
    }

    @Override
    public String getCustomRarityOrRarity() {
        String customRarity = this.getCustomRarity();
        return customRarity == "" ? this.getRarity().asString() : customRarity;
    }

    @Override
    public NbtList getCustomModifiers() {
        return ((ItemStack)(Object)this).getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getListOrEmpty("modifiers");
    }

    @Override
    public NbtList getCustomStatusEffects() {
        return ((ItemStack)(Object)this).getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getListOrEmpty("status_effects");
    }

    @Shadow
    public abstract Rarity getRarity();
}
