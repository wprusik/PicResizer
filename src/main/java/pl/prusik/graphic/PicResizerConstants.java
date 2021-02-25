package pl.prusik.graphic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.FilenameFilter;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
class PicResizerConstants {

    static String TITLE_ASCII = "               Welcome to" +
            "\n   ___  _     ___          _            \n" +
            "  / _ \\(_)___/ _ \\___ ___ (_)__ ___ ____\n" +
            " / ___/ / __/ , _/ -_|_-</ /_ // -_) __/\n" +
            "/_/  /_/\\__/_/|_|\\__/___/_//__/\\__/_/   \n" +
            "                                        ";

    private static final String[] IMAGE_EXTENSIONS = new String[]{"gif", "png", "bmp", "jpg", "jpeg"};

    static final FilenameFilter IMAGES_FILTER = (dir, name) -> {
        for (String ext : IMAGE_EXTENSIONS) {
            if (name.endsWith("." + ext)) {
                return true;
            }
        }
        return false;
    };

    @RequiredArgsConstructor
    enum AnsiColour {
        BLACK("\u001B[30m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m");

        @Getter
        private final String code;

        void print(String text) {
            System.out.print("\n" + this.code + text + "\u001B[0m");
        }
    }

}
