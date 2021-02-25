package pl.prusik.graphic;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static pl.prusik.graphic.PicResizerConstants.AnsiColour;
import static pl.prusik.graphic.PicResizerConstants.IMAGES_FILTER;

@SuppressWarnings("SpellCheckingInspection")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ResizerFacade {

    private final File imagesDirectory;
    private final Integer width;
    private final Integer height;
    private final Double scalingFactor;
    private final boolean keepProportions;

    private File[] images;
    private String outputPath;

    static ResizerFacade of(Parameters parameters) {
        return new ResizerFacade(parameters.getImagesDirectory(), parameters.getWidth(), parameters.getHeight(),
                parameters.getScalingFactor(), parameters.isKeepProportions());
    }

    void doResize() throws IOException {
        getAllImages();
        if (ArrayUtils.isNotEmpty(images)) {
            AnsiColour.GREEN.print("Found " + images.length + " images.");
            resizeImages();
        }
    }

    private void resizeImages() throws IOException {
        createOutputDirectory();
        for (int i = 0; i < images.length; i++) {
            String info = String.format("(%s/%s) Resizing image %s...", i, images.length, images[i].getName());
            AnsiColour.YELLOW.print(info);
            resizeImage(images[i]);
            System.out.print(" \u2713");
        }
        AnsiColour.GREEN.print("\nImages resized successfully!\nOutput path: " + outputPath);
    }

    private void createOutputDirectory() throws IOException {
        outputPath = imagesDirectory.getAbsolutePath() + File.separator + "output-" + System.currentTimeMillis();
        if (!new File(outputPath).exists()) {
            Files.createDirectory(Paths.get(outputPath));
        }
    }

    private void resizeImage(File image) throws IOException {
        if (keepProportions) {
            resizeByProportions(image);
        } else {
            resizeByDimensions(image);
        }
    }

    private void resizeByProportions(File image) throws IOException {
        BufferedImage inputImage = ImageIO.read(image);
        int targetWidth = (int) (inputImage.getWidth() * scalingFactor);
        int targetHeight = (int) (inputImage.getHeight() * scalingFactor);
        resizeByDimensions(inputImage, targetWidth, targetHeight, image.getName());
    }

    private void resizeByDimensions(File image) throws IOException {
        resizeByDimensions(ImageIO.read(image), width, height, image.getName());
    }

    private void resizeByDimensions(BufferedImage image, int targetWidth, int targetHeight, String targetName) throws IOException {
        BufferedImage targetImage = new BufferedImage(targetWidth, targetHeight, image.getType());
        Graphics2D graphics = targetImage.createGraphics();
        graphics.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        graphics.dispose();
        ImageIO.write(targetImage, getExtension(targetName), new File(outputPath + File.separator + targetName));
    }

    private void getAllImages() {
        System.out.println("\nLooking for images to resize...");
        images = imagesDirectory.listFiles(IMAGES_FILTER);
        if (ArrayUtils.isEmpty(images)) {
            AnsiColour.RED.print("No images found");
        }
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
