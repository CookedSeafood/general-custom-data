package net.hederamc.generalcustomdata.mixin;

import net.hederamc.fishbonetrehalose.api.EntityTypeHolder;
import net.hederamc.generalcustomdata.api.CustomExplosionRadiusHolder;
import net.hederamc.generalcustomdata.api.CustomFuseHolder;
import net.hederamc.generalcustomdata.api.CustomIdHolder;
import net.hederamc.generalcustomdata.api.CustomOwnerHolder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public abstract class EntityMixin
        implements CustomExplosionRadiusHolder, CustomFuseHolder, CustomIdHolder, CustomOwnerHolder, EntityTypeHolder {
    @Override
    public Identifier getCustomIdOrId() {
        Identifier customId = this.getCustomId();

        if (customId == null) {
            return this.getType().getId();
        }

        return customId;
    }
}
