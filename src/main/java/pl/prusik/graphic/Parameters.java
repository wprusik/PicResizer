package pl.prusik.graphic;

import lombok.Getter;

import java.io.File;
import java.util.Scanner;

import static pl.prusik.graphic.PicResizerConstants.AnsiColour;

@Getter
@SuppressWarnings("unused")
class Parameters {

    private final Scanner sc = new Scanner(System.in);
    private File imagesDirectory;
    private Integer width;
    private Integer height;
    private Double scalingFactor;
    private boolean keepProportions;

    void readParameters() {
        readImagesDirectory();
        readScalingParameters();
    }

    private void readImagesDirectory() {
        System.out.print("\nDirectory with images to resize: ");
        imagesDirectory = new File(sc.next().trim());
        if (!imagesDirectory.exists() || !imagesDirectory.isDirectory()) {
            AnsiColour.RED.print("Given directory does not exist!");
            readImagesDirectory();
        }
    }

    private void readScalingParameters() {
        readScalingMode();
        if (keepProportions) {
            this.scalingFactor = readNumberParameter(Double.class, "Scaling factor");
        } else {
            readTargetDimensions();
        }
    }

    private void readTargetDimensions() {
        this.height = readNumberParameter(Integer.class, "Target height (px)");
        this.width = readNumberParameter(Integer.class, "Target width (px)");
    }

    private void readScalingMode() {
        System.out.print("\nKeep proportions (y/n): ");
        keepProportions = sc.next().equalsIgnoreCase("y");
    }

    @SuppressWarnings("unchecked")
    private <T extends Number> T readNumberParameter(Class<T> type, String label) {
        System.out.print("\n" + label + ": ");
        String value = sc.next().trim();
        if (isNumber(value)) {
            if (type.equals(Double.class)) {
                return (T) Double.valueOf(value);
            } else {
                return (T) Integer.valueOf(value);
            }
        } else {
            printNumberWarning();
            return readNumberParameter(type, label);
        }
    }

    private boolean isNumber(String text) {
        return text.matches("[0-9]{1,13}(\\.[0-9]*)?");
    }

    private void printNumberWarning() {
        AnsiColour.YELLOW.print("Given argument is not a number!");
    }
}
