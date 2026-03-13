package net.hederamc.generalcustomdata.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.List;
import net.hederamc.fishbonetrehalose.api.Text;
import net.hederamc.generalcustomdata.effect.CustomStatusEffect;
import net.hederamc.generalcustomdata.effect.CustomStatusEffectEpisode;
import net.hederamc.generalcustomdata.effect.CustomStatusEffectIdentifier;
import net.hederamc.generalcustomdata.effect.CustomStatusEffectManager;
import net.hederamc.generalcustomdata.suggestion.CustomStatusEffectSuggestionProvider;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class CustomCommand {
    private static final SimpleCommandExceptionType UNREGISTED_EFFECT_EXCEPTION = new SimpleCommandExceptionType(
            Text.literal("The effect is not registed"));
    private static final SimpleCommandExceptionType REQUIRES_LIVING_ENTITY_EXCEPTION = new SimpleCommandExceptionType(
            Text.literal("A living entity is required to run this command here"));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess) {
        dispatcher.register(
                Commands.literal("custom")
                        .then(
                                Commands.literal("effect")
                                        .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                                        .then(
                                                Commands.literal("list")
                                                        .executes(
                                                                context -> listEffect(
                                                                        context.getSource()))
                                                        .then(
                                                                Commands.argument("entities", EntityArgument.entity())
                                                                        .executes(
                                                                                context -> listEffect(
                                                                                        context.getSource(),
                                                                                        EntityArgument.getEntity(context, "entities")))))
                                        .then(
                                                Commands.literal("give")
                                                        .then(
                                                                Commands.argument("entities", EntityArgument.entities())
                                                                        .then(
                                                                                Commands.argument("effect", StringArgumentType.string())
                                                                                        .suggests(new CustomStatusEffectSuggestionProvider())
                                                                                        .executes(
                                                                                                context -> giveEffect(
                                                                                                        context.getSource(),
                                                                                                        EntityArgument.getEntities(context, "entities"),
                                                                                                        StringArgumentType.getString(context, "effect")))
                                                                                        .then(
                                                                                                Commands.argument("duration", IntegerArgumentType.integer(1))
                                                                                                        .executes(
                                                                                                                context -> giveEffect(
                                                                                                                        context.getSource(),
                                                                                                                        EntityArgument.getEntities(context, "entities"),
                                                                                                                        StringArgumentType.getString(context, "effect"),
                                                                                                                        IntegerArgumentType.getInteger(context, "duration")))
                                                                                                        .then(
                                                                                                                Commands.argument("amplifier", IntegerArgumentType.integer(0))
                                                                                                                        .executes(
                                                                                                                                context -> giveEffect(
                                                                                                                                        context.getSource(),
                                                                                                                                        EntityArgument.getEntities(context, "entities"),
                                                                                                                                        StringArgumentType.getString(context, "effect"),
                                                                                                                                        IntegerArgumentType.getInteger(context, "duration"),
                                                                                                                                        IntegerArgumentType.getInteger(context, "amplifier")))))
                                                                                        .then(
                                                                                                Commands.literal("infinite")
                                                                                                        .executes(
                                                                                                                context -> giveEffect(
                                                                                                                        context.getSource(),
                                                                                                                        EntityArgument.getEntities(context, "entities"),
                                                                                                                        StringArgumentType.getString(context, "effect"),
                                                                                                                        -1))
                                                                                                        .then(
                                                                                                                Commands.argument("amplifier", IntegerArgumentType.integer(0))
                                                                                                                        .executes(
                                                                                                                                context -> giveEffect(
                                                                                                                                        context.getSource(),
                                                                                                                                        EntityArgument.getEntities(context, "entities"),
                                                                                                                                        StringArgumentType.getString(context, "effect"),
                                                                                                                                        -1,
                                                                                                                                        IntegerArgumentType.getInteger(context, "amplifier"))))))))
                                        .then(
                                                Commands.literal("clear")
                                                        .executes(
                                                                context -> clearEffect(
                                                                        context.getSource()))
                                                        .then(
                                                                Commands.argument("entities", EntityArgument.entities())
                                                                        .executes(
                                                                                context -> clearEffect(
                                                                                        context.getSource(),
                                                                                        EntityArgument.getEntities(context, "entities")))
                                                                        .then(
                                                                                Commands.argument("effect", StringArgumentType.string())
                                                                                        .suggests(new CustomStatusEffectSuggestionProvider())
                                                                                        .executes(
                                                                                                context -> clearEffect(
                                                                                                        context.getSource(),
                                                                                                        EntityArgument.getEntities(context, "entities"),
                                                                                                        StringArgumentType.getString(context, "effect"))))))));
    }

    public static int listEffect(CommandSourceStack source) throws CommandSyntaxException {
        return listEffect(source, source.getEntityOrException());
    }

    public static int listEffect(CommandSourceStack source, Entity target) throws CommandSyntaxException {
        if (!(target instanceof LivingEntity)) {
            throw REQUIRES_LIVING_ENTITY_EXCEPTION.create();
        }

        CustomStatusEffectManager manager = ((LivingEntity) target).getCustomStatusEffectManager();

        if (manager.isEmpty()) {
            source.sendSuccess(() -> Text.literal("Target has no custom status effect"), false);
            return 0;
        }

        Text feedback = Text.literal("Target has " + manager.size() + " custom status effects:");
        manager.forEach(effect -> feedback.append("\n" + effect.getId().getId().toString() + " " + effect.getPlaylist().getActiveEpisode().toString()));
        source.sendSuccess(() -> (MutableComponent) feedback, false);
        return 0;
    }

    public static int giveEffect(CommandSourceStack source, Collection<? extends Entity> targets, String effect)
            throws CommandSyntaxException {
        return giveEffect(source, targets, effect, 1);
    }

    public static int giveEffect(CommandSourceStack source, Collection<? extends Entity> targets, String effect,
            int duration) throws CommandSyntaxException {
        return giveEffect(source, targets, effect, duration, 0);
    }

    public static int giveEffect(CommandSourceStack source, Collection<? extends Entity> targets, String effect,
            int duration, int amplifier) throws CommandSyntaxException {
        CustomStatusEffectIdentifier id = CustomStatusEffectIdentifier.fromRegistry(
                Identifier.bySeparator(effect, '.'));

        if (id == null) {
            UNREGISTED_EFFECT_EXCEPTION.create();
        }

        for (Entity target : targets) {
            if (!(target instanceof LivingEntity)) {
                continue;
            }

            ((LivingEntity) target).addCustomStatusEffect(
                    CustomStatusEffect.of(id).withEpisode(CustomStatusEffectEpisode.of(duration, amplifier)));
        }

        int count = targets.size();
        if (count == 1) {
            source.sendSuccess(
                    () -> Text.fromEmpty()
                            .append("Applied effect " + id.getId().toString() + " to ")
                            .append(targets.iterator().next().getDisplayName()),
                    true);
        } else {
            source.sendSuccess(
                    () -> Text.literal("Applied effect " + id.getId().toString() + " to " + count + " targets"),
                    true);
        }

        return Command.SINGLE_SUCCESS;
    }

    public static int clearEffect(CommandSourceStack source) throws CommandSyntaxException {
        return clearEffect(source, List.of(source.getEntityOrException()));
    }

    public static int clearEffect(CommandSourceStack source, Collection<? extends Entity> targets) {
        for (Entity target : targets) {
            if (!(target instanceof LivingEntity)) {
                continue;
            }

            ((LivingEntity) target).clearCustomStatusEffect();
        }

        int count = targets.size();
        if (count == 1) {
            source.sendSuccess(
                    () -> Text.fromEmpty()
                            .append("Removed every effect from ")
                            .append(targets.iterator().next().getDisplayName()),
                    true);
        } else {
            source.sendSuccess(
                    () -> Text.literal("Removed every effect from " + count + " targets"),
                    true);
        }

        return Command.SINGLE_SUCCESS;
    }

    public static int clearEffect(CommandSourceStack source, Collection<? extends Entity> targets, String effect) {
        CustomStatusEffectIdentifier id = CustomStatusEffectIdentifier.fromRegistry(
                Identifier.bySeparator(effect, '.'));

        if (id == null) {
            UNREGISTED_EFFECT_EXCEPTION.create();
        }

        for (Entity target : targets) {
            if (!(target instanceof LivingEntity)) {
                continue;
            }

            ((LivingEntity) target).removeCustomStatusEffect(id);
        }

        int count = targets.size();
        if (count == 1) {
            source.sendSuccess(
                    () -> Text.fromEmpty()
                            .append("Removed effect " + id.getId().toString() + " from ")
                            .append(targets.iterator().next().getDisplayName()),
                    true);
        } else {
            source.sendSuccess(
                    () -> Text.literal("Removed effect " + id.getId().toString() + " from " + count + " targets"),
                    true);
        }

        return Command.SINGLE_SUCCESS;
    }
}
