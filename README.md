# Icon Recolor tool

**icon-recolor** is a simple java tool that can be used to change the color of a single color PNG icon, such as an Android action bar icon.

This takes the source and target color as input, and adjust all pixels with the same color as the source color (whatever the alpha channel value) to the target color. During conversion the alpha channel is also adjusted in proportion.

The tool can also add an additional phase that converts white areas to transparent, taking care of properly adjusting any antialiased edge with the base color.

## Installing

Build with gradle using `gradle installApp` then use the scripts under `build\install\icon-recolor\bin`.

## Syntax

    icon-recolor --fixTransparency --recolor --from sourceColor --to targetColor fileName+ 

## Examples

For example to convert a holo dark action bar icon to a blue color:

    icon-recolor --recolor --from "#CCFFFFFF" --to "#FF0066CC" icon.png