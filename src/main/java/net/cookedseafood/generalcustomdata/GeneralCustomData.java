package net.cookedseafood.generalcustomdata;

import net.cookedseafood.generalcustomdata.command.CustomCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralCustomData implements ModInitializer {
    public static final String MOD_ID = "general-custom-data";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final byte VERSION_MAJOR = 0;
    public static final byte VERSION_MINOR = 1;
    public static final byte VERSION_PATCH = 6;

    public static final String MOD_NAMESPACE = "general_custom_data";

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> CustomCommand.register(dispatcher, registryAccess));
    }
}
