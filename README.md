# General Custom Data

General Custom Data is a vanilla-data alligned custom-data retriever, writer, and ticker.

Currently not fully featured.

## Command

`/custom effect list [<entity>]` Print a list of custom effects on the `entity`.

`/custom effect add <entity> <effect> [<duration|infinite>] [<amplifier>]` Add custom effect to `entity`.

`/custom effect clear [<entity>]` Remove custom effect from `entity`.

`/custom reset [<effect>]` Set corresponding component to a new empty component.

## Data Structure

```txt
[Compound] minecraft:custom_data
|- [List] modifiers
| |- [Compound]
|   |- [String] attribute: `namespace:path`.
|   |- [Double] base: Any.
|   |- [String] id: Any.
|   |- [String] operation: Can be `add_value`, `add_multiplied_base` and `add_multiplied_total`.
|   \- [String] slot: Can be `mainhand`, `offhand`, `feet`, `legs`, `chest` and `head`.
|- [String] id: `namespace:path`.
|- [List] status_effects
| |- [Compound]
|   |- [String] id: `namespace:path`.
|   |- [int] duration: Any.
\   \- [int] amplifier: Any.
```

## Method

```java
public final class ItemStack{
    public String getCustomId() {}

    public String getCustomIdOrId() {}

    public NbtList getCustomModifiers() {}

    public NbtList getCustomStatusEffects() {}
}
```

```java
public abstract class LivingEntity{
    public NbtCompound getCustomStatusEffects() {}

    public void setCustomStatusEffects(NbtCompound customStatusEffects) {}

    public ServerCustomStatusEffectManager getCustomStatusEffectManager() {}

    public boolean hasCustomStatusEffect(CustomStatusEffectIdentifier id) {}

    public <T> T modifyCustomStatusEffectManager(Function<ServerCustomStatusEffectManager, T> action) {}

    public void modifyCustomStatusEffectManager(Consumer<ServerCustomStatusEffectManager> action) {}

    public boolean addCustomStatusEffect(CustomStatusEffect statusEffect) {}

    public boolean setCustomStatusEffect(CustomStatusEffect statusEffect) {}

    public CustomStatusEffectPlaylist removeCustomStatusEffect(CustomStatusEffectIdentifier id) {}

    public void clearCustomStatusEffect() {}

    public void tickCustomStatusEffect() {}

    public double getCustomModifiedValue(String attribute, double base) {}

    public NbtList getCustomModifiers(String attribute) {}

    public NbtList getCustomModifiers() {}
}
```
