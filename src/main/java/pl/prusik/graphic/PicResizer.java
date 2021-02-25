package pl.prusik.graphic;

import java.io.IOException;

import static pl.prusik.graphic.PicResizerConstants.AnsiColour;

@SuppressWarnings("SpellCheckingInspection")
public class PicResizer {

    private static Parameters parameters = new Parameters();

    public static void main(String... args) throws IOException {
        printTitle();
        parameters.readParameters();
        resize();
    }

    private static void resize() throws IOException {
        ResizerFacade.of(parameters).doResize();
    }

    private static void printTitle() {
        AnsiColour.CYAN.print(PicResizerConstants.TITLE_ASCII);
    }
}
