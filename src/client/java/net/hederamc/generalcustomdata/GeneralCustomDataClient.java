package net.hederamc.generalcustomdata;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.hederamc.generalcustomdata.network.protocol.common.GeneralCustomDataConnectionInitializerC2SPayload;

public class GeneralCustomDataClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.

        ClientPlayConnectionEvents.JOIN.register((listener, sender, client) -> ClientPlayNetworking.send(GeneralCustomDataConnectionInitializerC2SPayload.INSTANCE));
    }
}
