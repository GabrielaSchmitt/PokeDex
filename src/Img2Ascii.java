
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Img2Ascii {

    private BufferedImage img;
    private double pixval;

    public void convertToAscii(String imgurl) {
        try {
            img = ImageIO.read(new URL(imgurl));
        } catch (IOException e) {
            System.out.println("\nError - not possible to get image from server. ");
        }

        int cutedy = 15;
        int cutedx = 10;
        Color pixcol;
        for (int i = cutedy; i < img.getHeight() - cutedy; i++) {
            for (int j = cutedx; j < img.getWidth() - cutedx; j++) {
                pixcol = new Color(img.getRGB(j, i));
                pixval = (((pixcol.getRed() * 0.30) +
                        (pixcol.getBlue() * 0.59) +
                        (pixcol.getGreen() * 0.11)));
                System.out.print(strChar(pixval));
            }
            System.out.println();
        }
    }

    public String strChar(double g) {
        String str = " ";
        if (g >= 240) {
            str = " ";
        } else if (g >= 210) {
            str = ".";
        } else if (g >= 190) {
            str = "*";
        } else if (g >= 170) {
            str = "+";
        } else if (g >= 120) {
            str = "^";
        } else if (g >= 110) {
            str = "&";
        } else if (g >= 80) {
            str = "8";
        } else if (g >= 60) {
            str = "#";
        } else {
            str = "@";
        }
        return str + str;
    }
}