package net.hederamc.gcd.suggestion;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;
import net.hederamc.gr.registry.Registries;
import net.hederamc.gr.registry.Registry;
import net.hederamc.gcd.effect.CustomStatusEffectIdentifier;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;

public class CustomStatusEffectSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        Registry<CustomStatusEffectIdentifier> registry = Registries.get(CustomStatusEffectIdentifier.class);

        if (registry != null) {
            registry.keySet().forEach(id -> {
                String candidate = id.toString().replace(':', '.');

                if (CommandSource.shouldSuggest(builder.getRemaining(), candidate)) {
                    builder.suggest(candidate);
                }
            });
        }

        return builder.buildFuture();
    }
}
