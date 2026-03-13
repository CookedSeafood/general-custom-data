package net.hederamc.generalcustomdata.network.protocol.common;

import io.netty.buffer.ByteBuf;
import net.hederamc.generalcustomdata.GeneralCustomData;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record GeneralCustomDataConnectionInitializerC2SPayload() implements CustomPacketPayload {
    public static final Identifier CONNECTION_INITIALIZER_PAYLOAD_ID = Identifier.fromNamespaceAndPath(GeneralCustomData.MOD_ID, "connection_initializer");
    public static final CustomPacketPayload.Type<GeneralCustomDataConnectionInitializerC2SPayload> ID = new CustomPacketPayload.Type<>(CONNECTION_INITIALIZER_PAYLOAD_ID);
    public static final GeneralCustomDataConnectionInitializerC2SPayload INSTANCE = new GeneralCustomDataConnectionInitializerC2SPayload();
    public static final StreamCodec<ByteBuf, GeneralCustomDataConnectionInitializerC2SPayload> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
