package net.npbq.icontools.recolor;

public class Recolor implements ColorFunction {

    private static final int RGB_MASK = 0x00ffffff;

    private final int srcRgb, targetRgb, srcAlpha, targetAlpha;

    public Recolor(int srcColor, int targetColor) {
        targetRgb = targetColor & RGB_MASK;
        srcAlpha = Color.alpha(srcColor);
        targetAlpha = Color.alpha(targetColor);
        srcRgb = srcColor & RGB_MASK;
    }

    @Override
    public int transform(int color) {
        int rgb = color & RGB_MASK;
        if (rgb == srcRgb) {
            int alpha = Color.alpha(color);
            alpha = (alpha * targetAlpha / srcAlpha);
            return targetRgb | (alpha << 24);
        } else
            return color;
    }

}
