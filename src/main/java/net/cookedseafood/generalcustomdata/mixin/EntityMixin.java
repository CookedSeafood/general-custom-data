package net.cookedseafood.generalcustomdata.mixin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.cookedseafood.generalcustomdata.api.EntityApi;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtShort;
import net.minecraft.nbt.NbtString;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
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

    @Nullable
    @Override
    public Entity getCustomOwner() {
        return this.getWorld().getEntity(UUID.fromString(((Entity)(Object)this).getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getString("owner", "00000000-0000-0000-0000-0000000000000000")));
    }

    @Override
    public void setCustomOwner(Entity owner) {
        ((Entity)(Object)this).setComponent(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(
            ((Entity)(Object)this).getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().copyFrom(
                new NbtCompound(
                    new HashMap<>(
                        Map.<String, NbtElement>of(
                            "owner",
                            NbtString.of(owner.getUuidAsString())
                        )
                    )
                )
            )
        ));
    }

    @Override
    public short getCustomFuse() {
        return ((Entity)(Object)this).getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getShort("fuse", (short)0);
    }

    @Override
    public void setCustomFuse(short fuse) {
        ((Entity)(Object)this).setComponent(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(
            ((Entity)(Object)this).getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().copyFrom(
                new NbtCompound(
                    new HashMap<>(
                        Map.<String, NbtElement>of(
                            "fuse",
                            NbtShort.of(fuse)
                        )
                    )
                )
            )
        ));
    }

    @Override
    public byte getCustomExplosionRadius() {
        return ((Entity)(Object)this).getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getByte("explosion_radius", (byte)0);
    }

    @Override
    public void setCustomExplosionRadius(byte radius) {
        ((Entity)(Object)this).setComponent(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(
            ((Entity)(Object)this).getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().copyFrom(
                new NbtCompound(
                    new HashMap<>(
                        Map.<String, NbtElement>of(
                            "explosion_radius",
                            NbtByte.of(radius)
                        )
                    )
                )
            )
        ));
    }

    @Shadow
    public abstract EntityType<?> getType();

    @Shadow
    public abstract World getWorld();
}
