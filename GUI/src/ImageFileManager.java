import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

/**
 * ImageFileManager is a utility class with static methods to load and save images.
 *
 * The files on disk can be in JPG or PNG image format. For files written
 * by this class, the format is determined by the constant IMAGE_FORMAT.
 *
 * @author Michael Kolling and David J Barnes
 * @version 2.0
 */
public class ImageFileManager {
    // A constant for the image format that this writer uses for writing.
    // Available formats are "jpg" and "png".
    private static final String IMAGE_FORMAT = "jpg";

    /**
     * Read an image file from disk and return it as an OFImage object.
     * This method can read JPG and PNG file formats.
     * In case of any problem (e.g., the file does not exist, is in an undecodable format,
     * or any other read error), this method returns null.
     *
     * @param imageFile The image file to be loaded.
     * @return The OFImage object or null if it could not be read.
     */
    public static OFImage loadImage(File imageFile) {
        try {
            BufferedImage image = ImageIO.read(imageFile);
            if (image == null) {
                // Could not load the image - probably an invalid file format
                return null;
            }
            return new OFImage(image);
        } catch (IOException exc) {
            // Return null if an error occurs while reading the image
            return null;
        }
    }

    /**
     * Write an OFImage object to disk. The file format is determined by IMAGE_FORMAT.
     * In case of any problem, the method silently returns.
     *
     * @param image The OFImage object to be saved.
     * @param file  The file to save to.
     */
    public static void saveImage(OFImage image, File file) {
        try {
            ImageIO.write(image, IMAGE_FORMAT, file);
        } catch (IOException exc) {
            // Silently handle any errors during image saving
        }
    }
}
