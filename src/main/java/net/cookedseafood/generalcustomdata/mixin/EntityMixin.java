package net.cookedseafood.generalcustomdata.mixin;

import java.util.HashMap;
import java.util.Map;
import net.cookedseafood.generalcustomdata.api.EntityApi;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtString;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityApi {
    @Override
    public String getCustomId() {
        return ((Entity)(Object)this).getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getString("id", "");
    }

    @Override
    public void setCustomId(String id) {
        ((Entity)(Object)this).setComponent(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(
            ((Entity)(Object)this).getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().copyFrom(
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
        return customId == "" ? EntityType.getId(this.getType()).toString() : customId;
    }

    @Shadow
    public abstract EntityType<?> getType();
}
