package net.hederamc.generalcustomdata.mixin;

import net.hederamc.generalcustomdata.api.GeneralCustomDataConnection;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin implements GeneralCustomDataConnection {
    @Unique
    private boolean canConnectGeneralCustomData;

    @Override
    public boolean canConnectGeneralCustomData() {
        return this.canConnectGeneralCustomData;
    }

    @Override
    public void setCanConnectGeneralCustomData(boolean bool) {
        this.canConnectGeneralCustomData = bool;
    }
}
