package ie.atu.sw.files;

/**
 * The {@code OutputFile} class represents a file where processed data will be written.
 * This class extends {@code ProgramFile} and provides specific handling for output files.
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
     * Processes the output file. Currently, no specific processing is implemented for the output file.
     * Override this method to add custom output file processing logic if needed.
     */
    @Override
    public void process() {
        // Output file specific processing, if needed
    }
}
