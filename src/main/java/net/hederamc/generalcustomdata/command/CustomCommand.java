package net.hederamc.generalcustomdata.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.List;
import net.hederamc.generalcustomdata.effect.CustomStatusEffect;
import net.hederamc.generalcustomdata.effect.CustomStatusEffectEpisode;
import net.hederamc.generalcustomdata.effect.CustomStatusEffectIdentifier;
import net.hederamc.generalcustomdata.effect.CustomStatusEffectManager;
import net.hederamc.generalcustomdata.suggestion.CustomStatusEffectSuggestionProvider;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class CustomCommand {
    private static final SimpleCommandExceptionType UNREGISTED_EFFECT_EXCEPTION =
        new SimpleCommandExceptionType(Component.literal("The effect is not registed"));
    private static final SimpleCommandExceptionType REQUIRES_LIVING_ENTITY_EXCEPTION =
        new SimpleCommandExceptionType(Component.literal("A living entity is required to run this command here"));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess) {
        dispatcher.register(
            Commands.literal("custom")
            .then(
                Commands.literal("effect")
                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(
                    Commands.literal("list")
                    .executes(context -> executeListEffect(context.getSource()))
                    .then(
                        Commands.argument("entities", EntityArgument.entity())
                        .executes(context -> executeListEffect(context.getSource(), EntityArgument.getEntity(context, "entities")))
                    )
                )
                .then(
                    Commands.literal("give")
                    .then(
                        Commands.argument("entities", EntityArgument.entities())
                        .then(
                            Commands.argument("effect", StringArgumentType.string())
                            .suggests(new CustomStatusEffectSuggestionProvider())
                            .executes(context -> executeGiveEffect(context.getSource(), EntityArgument.getEntities(context, "entities"), StringArgumentType.getString(context, "effect")))
                            .then(
                                Commands.argument("duration", IntegerArgumentType.integer(1))
                                .executes(context -> executeGiveEffect(context.getSource(), EntityArgument.getEntities(context, "entities"), StringArgumentType.getString(context, "effect"), IntegerArgumentType.getInteger(context, "duration")))
                                .then(
                                    Commands.argument("amplifier", IntegerArgumentType.integer(0))
                                    .executes(context -> executeGiveEffect(context.getSource(), EntityArgument.getEntities(context, "entities"), StringArgumentType.getString(context, "effect"), IntegerArgumentType.getInteger(context, "duration"), IntegerArgumentType.getInteger(context, "amplifier")))
                                )
                            )
                            .then(
                                Commands.literal("infinite")
                                .executes(context -> executeGiveEffect(context.getSource(), EntityArgument.getEntities(context, "entities"), StringArgumentType.getString(context, "effect"), -1))
                                .then(
                                    Commands.argument("amplifier", IntegerArgumentType.integer(0))
                                    .executes(context -> executeGiveEffect(context.getSource(), EntityArgument.getEntities(context, "entities"), StringArgumentType.getString(context, "effect"), -1, IntegerArgumentType.getInteger(context, "amplifier")))
                                )
                            )
                        )
                    )
                )
                .then(
                    Commands.literal("clear")
                    .executes(context -> executeClearEffect(context.getSource()))
                    .then(
                        Commands.argument("entities", EntityArgument.entities())
                        .executes(context -> executeClearEffect(context.getSource(), EntityArgument.getEntities(context, "entities")))
                        .then(
                            Commands.argument("effect", StringArgumentType.string())
                            .suggests(new CustomStatusEffectSuggestionProvider())
                            .executes(context -> executeClearEffect(context.getSource(), EntityArgument.getEntities(context, "entities"), StringArgumentType.getString(context, "effect")))
                        )
                    )
                )
            )
        );
    }

    public static int executeListEffect(CommandSourceStack source) throws CommandSyntaxException {
        return executeListEffect(source, source.getEntityOrException());
    }

    public static int executeListEffect(CommandSourceStack source, Entity target) throws CommandSyntaxException {
        if (!(target instanceof LivingEntity)) {
            throw REQUIRES_LIVING_ENTITY_EXCEPTION.create();
        }

        CustomStatusEffectManager manager = ((LivingEntity)target).getCustomStatusEffectManager();

        if (manager.isEmpty()) {
            source.sendSuccess(() -> Component.literal("Target has no custom status effect"), false);
            return 0;
        }

        MutableComponent feedback = Component.literal("Target has "+ manager.size() + " custom status effects:");
        manager.forEach(effect -> feedback.append("\n" + effect.getId().getId().toString() + " " + effect.getPlaylist().getActiveEpisode().toString()));
        source.sendSuccess(() -> feedback, false);
        return 0;
    }

    public static int executeGiveEffect(CommandSourceStack source, Collection<? extends Entity> targets, String effect) throws CommandSyntaxException {
        return executeGiveEffect(source, targets, effect, 1, 0);
    }

    public static int executeGiveEffect(CommandSourceStack source, Collection<? extends Entity> targets, String effect, int duration) throws CommandSyntaxException {
        return executeGiveEffect(source, targets, effect, duration, 0);
    }

    public static int executeGiveEffect(CommandSourceStack source, Collection<? extends Entity> targets, String effect, int duration, int amplifier) throws CommandSyntaxException {
        CustomStatusEffectIdentifier id = CustomStatusEffectIdentifier.fromRegistry(Identifier.bySeparator(effect, '.'));

        if (id == null) {
            UNREGISTED_EFFECT_EXCEPTION.create();
        }

        for (Entity target : targets) {
            if (!(target instanceof LivingEntity)) {
                continue;
            }

            ((LivingEntity)target).addCustomStatusEffect(CustomStatusEffect.of(id).withEpisode(CustomStatusEffectEpisode.of(duration, amplifier)));
        }

        int count = targets.size();

        if (count == 1) {
            source.sendSuccess(() -> Component.literal("Applied effect " + id.getId().toString() + " to ").append(targets.iterator().next().getDisplayName()), true);
        } else {
            source.sendSuccess(() -> Component.literal("Applied effect " + id.getId().toString() + " to " + count + " targets"), true);
        }

        return Command.SINGLE_SUCCESS;
    }

    public static int executeClearEffect(CommandSourceStack source) throws CommandSyntaxException {
        return executeClearEffect(source, source.getEntityOrException());
    }

    public static int executeClearEffect(CommandSourceStack source, Entity target) {
        return executeClearEffect(source, List.of(target));
    }

    public static int executeClearEffect(CommandSourceStack source, Collection<? extends Entity> targets) {
        for (Entity target : targets) {
            if (!(target instanceof LivingEntity)) {
                continue;
            }

            ((LivingEntity)target).clearCustomStatusEffect();
        }

        int count = targets.size();

        if (count == 1) {
            source.sendSuccess(() -> Component.literal("Removed every effect from ").append(targets.iterator().next().getDisplayName()), true);
        } else {
            source.sendSuccess(() -> Component.literal("Removed every effect from " + count + " targets"), true);
        }

        return Command.SINGLE_SUCCESS;
    }

    public static int executeClearEffect(CommandSourceStack source, Collection<? extends Entity> targets, String effect) {
        CustomStatusEffectIdentifier id = CustomStatusEffectIdentifier.fromRegistry(Identifier.bySeparator(effect, '.'));

        if (id == null) {
            UNREGISTED_EFFECT_EXCEPTION.create();
        }

        for (Entity target : targets) {
            if (!(target instanceof LivingEntity)) {
                continue;
            }

            ((LivingEntity)target).removeCustomStatusEffect(id);
        }

        int count = targets.size();

        if (count == 1) {
            source.sendSuccess(() -> Component.literal("Removed effect " + id.getId().toString() + " from ").append(targets.iterator().next().getDisplayName()), true);
        } else {
            source.sendSuccess(() -> Component.literal("Removed effect " + id.getId().toString() + " from " + count + " targets"), true);
        }

        return Command.SINGLE_SUCCESS;
    }
}
