package net.npbq.icontools.recolor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.common.primitives.UnsignedInts;

public class IconRecolor {

    private static final int RGB_MASK = 0x00ffffff;
    private static final int ALPHA_MASK = 0xff000000;

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Usage: ImageRecolor sourceColor targetColor file+");
            return;
        }

        int srcColor = UnsignedInts.decode(args[0]);
        int targetColor = UnsignedInts.decode(args[1]);

        for (int i = 2; i < args.length; i++) {
            File imgPath = new File(args[i]);
            recolor(imgPath, srcColor, imgPath, targetColor);
        }
    }

    public static void recolor(File srcPath, int srcColor, File targetPath, int targetColor) throws IOException {
        int srcAlpha = (srcColor & ALPHA_MASK) >>> 24;
        int targetAlpha = (targetColor & ALPHA_MASK) >>> 24;

        BufferedImage img = ImageIO.read(srcPath);
        int h = img.getHeight();
        int w = img.getWidth();
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int c = img.getRGB(x, y);
                if ((c & RGB_MASK) == (srcColor & RGB_MASK)) {
                    int alpha = (c & ALPHA_MASK) >>> 24;
                    alpha = (alpha * targetAlpha / srcAlpha);
                    c = (targetColor & RGB_MASK) | (alpha << 24);
                    img.setRGB(x, y, c);
                }
            }
        }

        ImageIO.write(img, "png", targetPath);
    }

}
