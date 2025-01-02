package ie.atu.sw.files;

/**
 * The {@code OutputFile} class represents a file where data will be outputted.
 * This class extends {@code ProgramFile} and provides handling for output files.
 */
public class OutputFile extends ProgramFile {

    /**
     * Constructs an {@code OutputFile} object with the specified file path.
     *
     * @param filePath the path to the output file.
     */
    public OutputFile(String filePath) {
        super(filePath);
    }

    /**
     * Processes the output file. None is needed for this file however.
     */
    @Override
    public void process() {
    }
}
