package net.npbq.icontools.recolor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class IconRecolor {

    @SuppressWarnings("static-access")
    public static void main(String[] args) throws IOException, ParseException {
        CommandLineParser parser = new BasicParser();

        Options options = new Options();

        options.addOption(OptionBuilder.withLongOpt("fixTransparency").withDescription("Convert white to transparent")
                .create());
        options.addOption(OptionBuilder.withLongOpt("recolor").withDescription("Convert srcColor to targetColor")
                .create());
        options.addOption(OptionBuilder.withLongOpt("from").withDescription("Source color").hasArg()
                .withArgName("#srcColor").create());
        options.addOption(OptionBuilder.withLongOpt("to").withDescription("Target color").hasArg()
                .withArgName("#targetColor").create());

        CommandLine cmd = parser.parse(options, args);

        int srcColor = Color.parseColor(cmd.getOptionValue("from"));

        List<ColorFunction> functions = new ArrayList<>();
        if (cmd.hasOption("fixTransparency")) {
            System.out.println("Will be fixing transparency");
            functions.add(new FixTransparency(srcColor));
        }

        if (cmd.hasOption("recolor")) {
            System.out.println("Will be recoloring");
            int targetColor = Color.parseColor(cmd.getOptionValue("to"));
            functions.add(new Recolor(srcColor, targetColor));
        }

        if (functions.isEmpty())
            throw new IllegalArgumentException("No function specified");

        for (String imgPath : cmd.getArgs()) {
            File img = new File(imgPath);
            process(img, img, functions);
        }
    }

    public static void process(File srcPath, File targetPath, Iterable<ColorFunction> functions) throws IOException {
        System.out.printf("Processing %s%n", srcPath.getAbsolutePath());

        BufferedImage img = ImageIO.read(srcPath);
        int h = img.getHeight();
        int w = img.getWidth();
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int color = img.getRGB(x, y);
                for (ColorFunction function : functions)
                    color = function.transform(color);
                img.setRGB(x, y, color);
            }
        }

        ImageIO.write(img, "png", targetPath);
    }

}
