# General Custom Data

General Custom Data is a vanilla-data alligned custom-data retriever, writer, and ticker.

Currently not fully featured.

## Command

- `/custom effect list [<entity>]` Print a list of custom effects on the `entity`.
- `/custom effect add <entity> <effect> [<duration|infinite>] [<amplifier>]` Add custom effect to `entity`.
- `/custom effect clear [<entity>] [<effect>]` Remove custom effect from `entity`.
- `/custom reset [<effect>]` Set corresponding component to a new empty component.

## Data Structure

![Compound](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/compound.png) **custom_data**: Parent tag.  
&ensp;|- ![List](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/list.png) **modifiers**  
&ensp;|&emsp;&nbsp;\\- ![Compound](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/compound.png)  
&ensp;|&emsp;&emsp;&ensp;&nbsp;|- ![String](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/string.png) **attribute**: `namespace:path`.  
&ensp;|&emsp;&emsp;&ensp;&nbsp;|- ![Double](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/double.png) **base**: Any.  
&ensp;|&emsp;&emsp;&ensp;&nbsp;|- ![String](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/string.png) **id**: Any.  
&ensp;|&emsp;&emsp;&ensp;&nbsp;|- ![String](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/string.png) **operation**: Can be `add_value`, `add_multiplied_base` and `add_multiplied_total`.  
&ensp;|&emsp;&emsp;&ensp;&nbsp;\\- ![String](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/string.png) **slot**: Can be `mainhand`, `offhand`, `feet`, `legs`, `chest` and `head`.  
&ensp;|- ![String](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/string.png) **id**: `namespace:path`.  
&ensp;|- ![List](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/list.png) **status_effects**  
&ensp;|&emsp;&nbsp;\\- ![Compound](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/compound.png)  
&ensp;|&emsp;&emsp;&ensp;&nbsp;|- ![String](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/string.png) **id**: `namespace:path`.  
&ensp;|&emsp;&emsp;&ensp;&nbsp;|- ![Int](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/int.png) **duration**: Any.  
&ensp;|&emsp;&emsp;&ensp;&nbsp;\\- ![Int](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/int.png) **amplifier**: Any.  
&ensp;\\- ![String](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/string.png) **rarity**: Any.

![Compound](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/compound.png) **data**: Parent tag.  
&ensp;|- ![Byte](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/byte.png) **explosion_radius**: Any.  
&ensp;|- ![Short](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/short.png) **fuse**: Any.  
&ensp;|- ![String](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/string.png) **id**: `namespace:path`.  
&ensp;\\- ![String](https://github.com/CookedSeafood/nbtsheet/raw/62168868b43a6a67da11d3520f804ab003c01457/string.png) **owner**: UUID.

## Method

```java
public final class ItemStack{
    public String getCustomId() {}

    public void setCustomId(String id) {}

    public String getCustomIdOrId() {}

    public String getCustomRarity() {}

    public void setCustomRarity(String rarity) {}

    public String getCustomRarityOrRarity() {}

    public NbtList getCustomModifiers() {}

    public NbtList getCustomStatusEffects() {}
}
```

```java
public abstract class Entity{
    public String getCustomId() {}

    public void setCustomId(String id) {}

    public String getCustomIdOrId() {}

    public Entity getCustomOwner() {}

    public void setCustomOwner(Entity owner) {}

    public short getCustomFuse() {}

    public void setCustomFuse(short fuse) {}

    public byte getCustomExplosionRadius() {}

    public void setCustomExplosionRadius(byte radius) {}
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
