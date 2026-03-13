package net.hederamc.generalcustomdata.network.protocol.common;

import io.netty.buffer.ByteBuf;
import net.hederamc.generalcustomdata.GeneralCustomData;
import net.hederamc.generalcustomdata.effect.CustomStatusEffect;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record AddCustomStatusEffectS2CPayload(int entityId, CustomStatusEffect statusEffect) implements CustomPacketPayload {
    public static final Identifier ADD_CUSTOM_STATUS_EFFECT_PAYLOAD_ID = Identifier.fromNamespaceAndPath(GeneralCustomData.MOD_ID, "add_custom_status_effect");
    public static final CustomPacketPayload.Type<AddCustomStatusEffectS2CPayload> ID = new CustomPacketPayload.Type<>(ADD_CUSTOM_STATUS_EFFECT_PAYLOAD_ID);
    public static final StreamCodec<ByteBuf, AddCustomStatusEffectS2CPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            AddCustomStatusEffectS2CPayload::entityId,
            CustomStatusEffect.STREAM_CODEC,
            AddCustomStatusEffectS2CPayload::statusEffect,
            AddCustomStatusEffectS2CPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
