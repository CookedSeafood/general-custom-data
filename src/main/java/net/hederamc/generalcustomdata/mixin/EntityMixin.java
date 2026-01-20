package net.hederamc.generalcustomdata.mixin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.hederamc.generalcustomdata.api.CustomExplosionRadiusHolder;
import net.hederamc.generalcustomdata.api.CustomFuseHolder;
import net.hederamc.generalcustomdata.api.CustomIdHolder;
import net.hederamc.generalcustomdata.api.CustomOwnerHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin implements CustomExplosionRadiusHolder, CustomFuseHolder, CustomIdHolder, CustomOwnerHolder {
    @Nullable
    @Override
    public Identifier getCustomId() {
        Optional<String> optional = ((Entity)(Object)this).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("id");
        return optional.isPresent() ? Identifier.parse(optional.get()) : null;
    }

    @Override
    public void setCustomId(Identifier id) {
        ((Entity)(Object)this).setComponent(DataComponents.CUSTOM_DATA, CustomData.of(
            ((Entity)(Object)this).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().merge(
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
        return customId == null ? EntityType.getKey(this.getType()) : customId;
    }

    @Nullable
    @Override
    public Entity getCustomOwner() {
        Optional<String> optional = ((Entity)(Object)this).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("owner");
        return optional.isPresent() ? this.level().getEntity(UUID.fromString(optional.get())) : null;
    }

    @Override
    public void setCustomOwner(Entity owner) {
        ((Entity)(Object)this).setComponent(DataComponents.CUSTOM_DATA, CustomData.of(
            ((Entity)(Object)this).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().merge(
                new CompoundTag(
                    new HashMap<>(
                        Map.<String, Tag>of(
                            "owner",
                            StringTag.valueOf(owner.getStringUUID())
                        )
                    )
                )
            )
        ));
    }

    @Override
    public short getCustomFuse() {
        return ((Entity)(Object)this).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getShortOr("fuse", (short)0);
    }

    @Override
    public void setCustomFuse(short fuse) {
        ((Entity)(Object)this).setComponent(DataComponents.CUSTOM_DATA, CustomData.of(
            ((Entity)(Object)this).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().merge(
                new CompoundTag(
                    new HashMap<>(
                        Map.<String, Tag>of(
                            "fuse",
                            ShortTag.valueOf(fuse)
                        )
                    )
                )
            )
        ));
    }

    @Override
    public byte getCustomExplosionRadius() {
        return ((Entity)(Object)this).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getByteOr("explosion_radius", (byte)0);
    }

    @Override
    public void setCustomExplosionRadius(byte radius) {
        ((Entity)(Object)this).setComponent(DataComponents.CUSTOM_DATA, CustomData.of(
            ((Entity)(Object)this).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().merge(
                new CompoundTag(
                    new HashMap<>(
                        Map.<String, Tag>of(
                            "explosion_radius",
                            ByteTag.valueOf(radius)
                        )
                    )
                )
            )
        ));
    }

    @Shadow
    public abstract EntityType<?> getType();

    @Shadow
    public abstract Level level();
}
