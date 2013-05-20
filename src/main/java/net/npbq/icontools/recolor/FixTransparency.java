package net.npbq.icontools.recolor;

public class FixTransparency implements ColorFunction {

    private final int srcR, srcG, srcB;

    public FixTransparency(int srcColor) {
        if (Color.alpha(srcColor) != 0xFF)
            throw new IllegalArgumentException("Cannot fix transparency if source color has an alpha channel");

        srcR = Color.red(srcColor);
        srcG = Color.green(srcColor);
        srcB = Color.blue(srcColor);
    }

    @Override
    public int transform(int color) {
        if ((color & 0x00ffffff) == 0x00ffffff)
            return 0x00FFFFFF; // full white at any alpha > full transparent
        else if (Color.alpha(color) == 0xFF) {
            int alpha = (alpha(srcR, Color.red(color)) + alpha(srcG, Color.green(color)) + alpha(srcB, Color.blue(color))) / 3;
            return Color.argb(alpha, srcR, srcG, srcB);
        }

        return color;
    }

    private static int alpha(int srcComponent, int component) {
        return (255 - component) * 255 / (255 - srcComponent);
    }

}
