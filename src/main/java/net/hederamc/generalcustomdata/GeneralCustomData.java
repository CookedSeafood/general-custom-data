package net.hederamc.generalcustomdata;

import net.hederamc.generalcustomdata.command.CustomCommand;
import net.hederamc.generalcustomdata.network.protocol.common.GeneralCustomDataConnectionInitializerC2SPayload;
import net.minecraft.server.level.ServerPlayer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GeneralCustomData implements ModInitializer {
    public static final String MOD_ID = "general-custom-data";
    public static final String MOD_NAMESPACE = "general_custom_data";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        PayloadTypeRegistry.serverboundPlay().register(GeneralCustomDataConnectionInitializerC2SPayload.ID, GeneralCustomDataConnectionInitializerC2SPayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(GeneralCustomDataConnectionInitializerC2SPayload.ID, (payload, context) -> {
            ServerPlayer player = context.player();
            if (player == null) {
                return;
            }

            player.connection.setCanConnectGeneralCustomData(true);
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> CustomCommand.register(dispatcher, registryAccess));
    }
}
