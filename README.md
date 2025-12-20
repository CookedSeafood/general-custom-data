# General Custom Data

General Custom Data is a vanilla-data alligned custom-data retriever, writer, and ticker.

Currently not fully featured.

## Command

- `/custom effect list [<entity>]` Print a list of custom effects on the `entity`.
- `/custom effect give <entity> <effect> [<duration|infinite>] [<amplifier>]` Add custom effect to `entity`.
- `/custom effect clear [<entity>] [<effect>]` Remove custom effect from `entity`.
- `/custom reset [<effect>]` Set corresponding component to a new empty component.

## Data Structure

![Compound](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/compound.png) **custom_data**: Parent tag.  
&ensp;|- ![String](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/string.png) **id**: `namespace:path`.  
&ensp;|- ![List](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/list.png) **modifiers**  
&ensp;|&emsp;&nbsp;\\- ![Compound](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/compound.png) A modifier.  
&ensp;|&emsp;&emsp;&ensp;&nbsp;|- ![String](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/string.png) **attribute**: `namespace:path`.  
&ensp;|&emsp;&emsp;&ensp;&nbsp;|- ![Double](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/double.png) **base**: Any.  
&ensp;|&emsp;&emsp;&ensp;&nbsp;|- ![String](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/string.png) **id**: Any.  
&ensp;|&emsp;&emsp;&ensp;&nbsp;|- ![String](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/string.png) **operation**: `add_value`, `add_multiplied_base`, `add_multiplied_total`.  
&ensp;|&emsp;&emsp;&ensp;&nbsp;\\- ![String](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/string.png) **slot**: `mainhand`, `offhand`, `feet`, `legs`, `chest`, `head`.  
&ensp;|- ![String](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/string.png) **rarity**: Any.  
&ensp;\\- ![List](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/list.png) **status_effects**  
&emsp;&emsp;\\- ![Compound](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/compound.png) A status effect.  
&emsp;&emsp;&emsp;&ensp;|- ![String](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/string.png) **id**: `namespace:path`.  
&emsp;&emsp;&emsp;&ensp;|- ![Int](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/int.png) **amplifier**: Any.  
&emsp;&emsp;&emsp;&ensp;\\- ![Int](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/int.png) **duration**: Any.

![Compound](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/compound.png) **data**: Parent tag.  
&ensp;|- ![Byte](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/byte.png) **explosion_radius**: Any.  
&ensp;|- ![Short](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/short.png) **fuse**: Any.  
&ensp;|- ![String](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/string.png) **id**: `namespace:path`.  
&ensp;|- ![String](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/string.png) **owner**: UUID.  
&ensp;\\- ![Compound](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/compound.png) **status_effects**  
&emsp;&emsp;\\- ![List](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/list.png) **\<id\>**: `namespace:path`.  
&emsp;&emsp;&emsp;&ensp;\\- ![Compound](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/compound.png) A status effect. Ordered by amplifier from highest.  
&emsp;&emsp;&emsp;&emsp;&emsp;|- ![Int](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/int.png) **amplifier**: Any.  
&emsp;&emsp;&emsp;&emsp;&emsp;\\- ![Int](https://github.com/CookedSeafood/nbtsheet/raw/0cfc19cc5644a82c921d39f9c40729aca3dea33d/int.png) **duration**: Any.

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

    public boolean addCustomStatusEffect(CustomStatusEffectIdentifier id, int duration, int amplifier) {}

    public boolean setCustomStatusEffect(CustomStatusEffect statusEffect) {}

    public boolean setCustomStatusEffect(CustomStatusEffectIdentifier id, int duration, int amplifier) {}

    public CustomStatusEffectPlaylist removeCustomStatusEffect(CustomStatusEffectIdentifier id) {}

    public void clearCustomStatusEffect() {}

    public void tickCustomStatusEffect() {}

    public double getCustomModifiedValue(String attribute, double base) {}

    public NbtList getCustomModifiers(String attribute) {}

    public NbtList getCustomModifiers() {}
}
```
