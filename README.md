# Icon Recolor tool

**icon-recolor** is a simple java tool that can be used to change the color of a single color PNG icon, such as an Android action bar icon.

This takes the source and target color as input, and adjust all pixels with the same color as the source color (whatever the alpha channel value) to the target color. During conversion the alpha channel is also adjusted in proportion.

## Installing

Build with gradle using `gradle installApp` then use the scripts under `build\install\icon-recolor\bin`.

## Syntax

    icon-recolor sourceColor targetColor fileName+ 

## Examples

For example to convert a holo dark action bar icon to a blue color:

    icon-recolor "#CCFFFFFF" "#FF0066CC" icon.png