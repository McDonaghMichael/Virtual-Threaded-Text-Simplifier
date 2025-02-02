package ie.atu.sw.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * The {@code EmbeddingsFile} class is a file containing word embeddings.
 */
public class EmbeddingsFile extends ProgramFile {

    /**
     * A map that stores the word embeddings where the key is the word and the value is the embedding vector.
     */
    private static Map<String, double[]> embeddings;

    /**
     * Constructs an {@code EmbeddingsFile} object with the file path.
     *
     * @param filePath the path to the embeddings file.
     */
    public EmbeddingsFile(String filePath) {
        super(filePath);
        this.embeddings = new HashMap<>();
    }

    /**
     * Retrieves the embeddings map.
     *
     * @return the map containing words and their corresponding embedding vectors.
     */
    public static Map<String, double[]> getEmbeddings() {
        return embeddings;
    }

    /**
     * Processes the embeddings file to load word embeddings into the {@code embeddings} variable.
     *
     * @throws IOException           if an I/O error occurs while reading the file.
     * @throws InterruptedException  if the thread is interrupted while processing.
     */
    @Override
    public void process() throws IOException, InterruptedException {

        // First we use the BufferReader to read in all the content from the EmbeddingsFile
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // Create an ExecutorService to handle all of our virtual threads
            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

            // Then we make an array for storing all the results
            List<Future<Void>> futures = new ArrayList<>();
            String line;

            // Read each line and process embeddings asynchronously
            while ((line = br.readLine()) != null) {
                String finalLine = line;
                futures.add(executor.submit(() -> {

                    /** Now we must split each line of the embeddings
                     *  into an array, where element 0 is the word and
                     *  the rest of the array are the vectors
                     */
                    String[] parts = finalLine.split(",\\s*");
                    if (parts.length > 1) {

                        // Here we store all the vectors from element 1 onwards
                        double[] values = new double[parts.length - 1];
                        for (int i = 1, j = 0; i < parts.length; i++, j++) {
                            values[j] = Double.parseDouble(parts[i]);
                        }
                        embeddings.put(parts[0], values);
                    }
                    return null;
                }));
            }

            System.out.println("Embeddings loaded.");

            // Wait for all asynchronous tasks to complete
            for (Future<Void> future : futures) {
                future.get();
            }

            // Terminate the executor once finished
            executor.shutdown();
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.err.println("Executor did not terminate in time.");
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
