package ie.atu.sw.files;

/**
 * The {@code ProgramFile} abstract class for file handling in the project.
 */
abstract class ProgramFile {

    /**
     * The file path associated with the file.
     */
    protected String filePath;

    /**
     * Constructs a {@code ProgramFile} object with the specified file path.
     *
     * @param filePath the path to the file.
     */
    public ProgramFile(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the file path.
     *
     * @return the file path.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the file path.
     *
     * @param filePath the new file path to set.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Abstract method to process the file.
     *
     * @throws Exception if an error occurs during processing.
     */
    public abstract void process() throws Exception;
}
