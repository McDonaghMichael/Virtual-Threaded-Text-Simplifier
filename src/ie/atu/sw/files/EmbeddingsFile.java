package ie.atu.sw.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * The {@code EmbeddingsFile} class represents a file containing word embeddings.
 * It processes the file to load embeddings into a {@code ConcurrentHashMap}.
 */
public class EmbeddingsFile extends ProgramFile {

    /**
     * A map that stores the word embeddings where the key is the word and the value is the embedding vector.
     */
    private static Map<String, double[]> embeddings;

    /**
     * Constructs an {@code EmbeddingsFile} object with the specified file path.
     *
     * @param filePath the path to the embeddings file.
     */
    public EmbeddingsFile(String filePath) {
        super(filePath);
        this.embeddings = new ConcurrentHashMap<>();
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
     * Processes the embeddings file to load word embeddings into the {@code embeddings} map.
     *
     * @throws IOException           if an I/O error occurs while reading the file.
     * @throws InterruptedException  if the thread is interrupted while processing.
     */
    @Override
    public void process() throws IOException, InterruptedException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
            List<Future<Void>> futures = new ArrayList<>();
            String line;

            // Read each line and process embeddings asynchronously
            while ((line = br.readLine()) != null) {
                String finalLine = line;
                futures.add(executor.submit(() -> {
                    String[] parts = finalLine.split(",\\s*");
                    if (parts.length > 1) {
                        double[] values = new double[parts.length - 1];
                        for (int i = 1; i < parts.length; i++) {
                            values[i - 1] = Double.parseDouble(parts[i]);
                        }
                        embeddings.put(parts[0], values);
                    }
                    return null;
                }));
            }

            // Wait for all asynchronous tasks to complete
            for (Future<Void> future : futures) {
                future.get();
            }

            executor.shutdown();
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.err.println("Executor did not terminate in time.");
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
