package com.example.imageeditor;

import javafx.scene.image.Image;
import java.io.File;
import java.util.List;

/**
 * The Utility class contains useful methods that can be used
 * throughout the project.
 *
 * @author Stephen Power
 */

public class Utilities {

    /**
     * Method used to test if a file is valid
     *
     * @param file input file
     * @return true is the file is valid
     */
    public static boolean validFile(File file) {
        return file != null;
    }

    /**
     * Method used to divide two double values
     * @param x input double value
     * @param y input double value
     * @return result of x divided by y
     */
    public static double division(double x, double y) {
       double result = x / y;

       return result;
    }

    /**
     * Method used to verify that the orientation of an image is landscape
     * @param image input image
     * @return true if the orientation of the image is landscape
     */
    public static boolean landscapeImage(Image image) {
        double height = image.getHeight();
        double width = image.getWidth();

        return width > height;
    }

    /**
     * Method used to verify that the orientation of an image is portrait
     * @param image input image
     * @return true if the orientation of the image is portrait
     */
    public static boolean portraitImage(Image image) {
        double height = image.getHeight();
        double width = image.getWidth();

        return width < height;
    }

    /**
     * Method used to verify that the orientation of an image is square
     * @param image input image
     * @return true if the orientation of the image is square
     */
    public static boolean squareImage(Image image) {
        double height = image.getHeight();
        double width = image.getWidth();

        return width == height;
    }

    /**
     * Method used to display a double value to two decimal places
     * @param x input double value
     * @return value of x to two decimal places
     */
    public static double decimalFormat(Double x) {
        String strX = String.format("%.2f", x);

        return Double.parseDouble(strX);
    }

    /**
     * Method used to verify that an index value of a list is valid
     * @param index input index value of a list
     * @param List input list
     * @return true if the index value is not less than 0 or greater than the size of the list
     */
    public static boolean validIndex(int index, List List) {
        return index >= 0 && index < List.size();
    }
}
