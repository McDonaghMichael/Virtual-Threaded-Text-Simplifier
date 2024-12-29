package ie.atu.sw.files;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static ie.atu.sw.utils.Methods.findBestSynonym;

/**
 * The {@code Google1000File} class represents a file containing a list of words.
 * It processes the file to find synonyms for each word using the provided embeddings file
 * and writes the results to the specified output file.
 */
public class Google1000File extends ProgramFile {

    /**
     * The embeddings file used to find synonyms.
     */
    private EmbeddingsFile embeddingsFile;

    /**
     * The output file where the results will be written.
     */
    private OutputFile outputFile;

    /**
     * Constructs a {@code Google1000File} object with the specified file path, embeddings file, and output file.
     *
     * @param filePath      the path to the Google 1000 file.
     * @param embeddingsFile the embeddings file used for finding synonyms.
     * @param outputFile     the output file where the results will be saved.
     */
    public Google1000File(String filePath, EmbeddingsFile embeddingsFile, OutputFile outputFile) {
        super(filePath);
        this.embeddingsFile = embeddingsFile;
        this.outputFile = outputFile;
    }

    /**
     * Processes the Google 1000 file by finding the best synonym for each word
     * and writing the results to the output file.
     *
     * @throws IOException           if an I/O error occurs while reading or writing files.
     * @throws InterruptedException  if the thread is interrupted while processing.
     */
    @Override
    public void process() throws IOException, InterruptedException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile.getFilePath()))) {

            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
            List<Future<String>> futures = new ArrayList<>();
            String word;

            // Read each word from the file and find its best synonym asynchronously
            while ((word = br.readLine()) != null) {
                String finalWord = word.trim();
                futures.add(executor.submit(() -> findBestSynonym(finalWord)));
            }

            // Write the results to the output file
            for (Future<String> future : futures) {
                bw.write(future.get() + " ");
            }

            executor.shutdown();
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.err.println("Executor did not terminate in time.");
            }
            bw.flush();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
