package net.hederamc.generalcustomdata.mixin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.hederamc.generalcustomdata.api.CustomIdHolder;
import net.hederamc.generalcustomdata.api.CustomModifiersHolder;
import net.hederamc.generalcustomdata.api.CustomRarityHolder;
import net.hederamc.generalcustomdata.api.CustomStatusEffectsHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements CustomIdHolder, CustomRarityHolder, CustomModifiersHolder, CustomStatusEffectsHolder {
    @Nullable
    @Override
    public Identifier getCustomId() {
        Optional<String> optional = ((ItemStack)(Object)this).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("id");
        return optional.isPresent() ? Identifier.parse(optional.get()) : null;
    }

    @Override
    public void setCustomId(Identifier id) {
        ((ItemStack)(Object)this).set(DataComponents.CUSTOM_DATA, CustomData.of(
            ((ItemStack)(Object)this).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().merge(
                new CompoundTag(
                    new HashMap<>(
                        Map.<String, Tag>of(
                            "id",
                            StringTag.valueOf(id.toString())
                        )
                    )
                )
            )
        ));
    }

    @Override
    public Identifier getCustomIdOrId() {
        Identifier customId = this.getCustomId();
        return customId == null ? ((ItemStack)(Object)this).getItem().getId() : customId;
    }

    @Nullable
    @Override
    public String getCustomRarity() {
        return ((ItemStack)(Object)this).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("rarity", null);
    }

    @Override
    public void setCustomRarity(String rarity) {
        ((ItemStack)(Object)this).set(DataComponents.CUSTOM_DATA, CustomData.of(
            ((ItemStack)(Object)this).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().merge(
                new CompoundTag(
                    new HashMap<>(
                        Map.<String, Tag>of(
                            "rarity",
                            StringTag.valueOf(rarity)
                        )
                    )
                )
            )
        ));
    }

    @Override
    public String getCustomRarityOrRarity() {
        String customRarity = this.getCustomRarity();
        return customRarity == "" ? this.getRarity().getSerializedName() : customRarity;
    }

    @Override
    public ListTag getCustomModifiers() {
        return ((ItemStack)(Object)this).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getListOrEmpty("modifiers");
    }

    @Override
    public ListTag getCustomStatusEffects() {
        return ((ItemStack)(Object)this).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getListOrEmpty("status_effects");
    }

    @Override
    public void setCustomStatusEffects(ListTag statusEffects) {
        ((ItemStack)(Object)this).set(DataComponents.CUSTOM_DATA, CustomData.of(
            ((ItemStack)(Object)this).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().merge(
                new CompoundTag(
                    new HashMap<>(
                        Map.<String, Tag>of(
                            "status_effects",
                            statusEffects
                        )
                    )
                )
            )
        ));
    }

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract Rarity getRarity();
}
