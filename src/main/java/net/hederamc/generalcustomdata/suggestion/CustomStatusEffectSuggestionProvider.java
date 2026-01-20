package net.hederamc.generalcustomdata.suggestion;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;
import net.hederamc.generalcustomdata.effect.CustomStatusEffectIdentifier;
import net.hederamc.genericregistry.registry.Registries;
import net.hederamc.genericregistry.registry.Registry;
import net.minecraft.commands.CommandSourceStack;

public class CustomStatusEffectSuggestionProvider implements SuggestionProvider<CommandSourceStack> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        Registry<CustomStatusEffectIdentifier> registry = Registries.get(CustomStatusEffectIdentifier.class);
        if (registry != null) {
            registry.keySet().forEach(id -> builder.suggest(id.toString().replace(':', '.')));
        }

        return builder.buildFuture();
    }
}
