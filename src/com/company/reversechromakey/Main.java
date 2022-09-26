package com.company.reversechromakey;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        final int imageWidth = 1920, imageHeight = 1441, colorTolerance = 90;

        final Color lineColor = new Color(181, 230, 29);

        Color backgroundColor = new Color(255, 255, 255, 255), highlightColor = new Color(0, 0, 0, 255);

        final boolean funMode = true;


        BufferedImage image = null;
        try {
            File input_file = new File(
                    "C:/Users/redst/Downloads/COWlove.jpg");

            // image file path create an object of
            // BufferedImage type and pass as parameter the
            // width,  height and image int
            // type. TYPE_INT_ARGB means that we are
            // representing the Alpha , Red, Green and Blue
            // component of the image pixel using 8 bit
            // integer value.

            image = new BufferedImage(
                    imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

            // Reading input file
            image = ImageIO.read(input_file);

            System.out.println("Reading complete.");
        }
        catch (IOException e) {
            System.out.println("Error: " + e);
        }
        if(!funMode) {
            for(int i = 0; i < imageWidth; i++) {
                for(int j = 0; j < imageHeight; j++) {
                    Color color = new Color(image.getRGB(i, j), true);
                    if(ColourDistance(color, lineColor) > colorTolerance ) {
                        image.setRGB(i, j, backgroundColor.hashCode());
                    } else image.setRGB(i, j, highlightColor.hashCode());
                }
            }
        } else {
            for (int i = 0; i < imageWidth; i++) {
                for (int j = 0; j < imageHeight; j++) {
                    Color color = new Color(image.getRGB(i, j), true);
                    if (ColourDistance(color, lineColor) > colorTolerance) {
                        image.setRGB(i, j, backgroundColor.hashCode());
                    } else image.setRGB(i, j, new Color(Color.HSBtoRGB((float) i/imageWidth, 1, (float) j/imageHeight)).hashCode());
                }
            }
        }


        try {
            // Output file path
            File output_file = new File(
                    "C:/Users/redst/Downloads/COWlovers.png");

            // Writing to file taking type and path as
            ImageIO.write(image, "png", output_file);

            System.out.println("Writing complete.");
        }
        catch (IOException e) {
            System.out.println("Error: " + e);
        }

    }

    public static double ColourDistance(Color c1, Color c2)
    {
        double rmean = ( c1.getRed() + c2.getRed() )/2.0;
        int r = c1.getRed() - c2.getRed();
        int g = c1.getGreen() - c2.getGreen();
        int b = c1.getBlue() - c2.getBlue();
        double weightR = 2 + rmean/256;
        double weightG = 4.0;
        double weightB = 2 + (255-rmean)/256;
        return Math.sqrt(weightR*r*r + weightG*g*g + weightB*b*b);
    }
}
