# General Custom Data

A vanilla-data alligned custom-data retriever, writer, and ticker.

Currently not fully featured.

## Data Structure

### Item

```txt
[Compound] minecraft:custom_data
|- [List] modifiers
| |- [Compound]
|   |- [String] attribute: `namespace:path`.
|   |- [Double] base: Any.
|   |- [String] id: Any.
|   |- [String] operation: Can be `add_value`, `add_multiplied_base` and `add_multiplied_total`.
|   \- [String] slot: Can be `mainhand`, `offhand`, `feet`, `legs`, `chest` and `head`.
|- [List] status_effects
| |- [Compound]
|   |- [String] id: `namespace:path`.
|   |- [int] duration: Any.
\   \- [int] amplifier: Any.
```
