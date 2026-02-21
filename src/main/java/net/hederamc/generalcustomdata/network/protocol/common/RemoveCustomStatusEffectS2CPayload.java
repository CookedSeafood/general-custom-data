package net.hederamc.generalcustomdata.network.protocol.common;

import io.netty.buffer.ByteBuf;
import net.hederamc.generalcustomdata.GeneralCustomData;
import net.hederamc.generalcustomdata.effect.CustomStatusEffect;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record RemoveCustomStatusEffectS2CPayload(int entityId, CustomStatusEffect statusEffect) implements CustomPacketPayload {
    public static final Identifier REMOVE_CUSTOM_STATUS_EFFECT_ID = Identifier.fromNamespaceAndPath(GeneralCustomData.MOD_ID, "remove_custom_status_effect");
    public static final CustomPacketPayload.Type<RemoveCustomStatusEffectS2CPayload> ID = new CustomPacketPayload.Type<>(REMOVE_CUSTOM_STATUS_EFFECT_ID);
    public static final StreamCodec<ByteBuf, RemoveCustomStatusEffectS2CPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            RemoveCustomStatusEffectS2CPayload::entityId,
            CustomStatusEffect.STREAM_CODEC,
            RemoveCustomStatusEffectS2CPayload::statusEffect,
            RemoveCustomStatusEffectS2CPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
