package ie.atu.sw.files;

import ie.atu.sw.Runner;
import ie.atu.sw.utils.Methods;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * The {@code Google1000File} class is a file containing a list of words.
 * It processes the file to find embeddings for each word and writes the results to the output file.
 */
public class Google1000File extends ProgramFile {

    /**
     * The embeddings file used to retrieve word vectors.
     */
    private EmbeddingsFile embeddingsFile;

    /**
     * Hashmap for storing the words from the google file and their vectors
     */
    private Map<String, double[]> wordEmbeddingsMap;

    /**
     * The output file where the results will be written.
     */
    private OutputFile outputFile;

    /**
     * Constructs a {@code Google1000File} object with the file path, embeddings file, and output file.
     *
     * @param filePath       the path to the Google 1000 file.
     * @param embeddingsFile the embeddings file used for finding word vectors.
     * @param outputFile     the output file where the results will be saved.
     */
    public Google1000File(String filePath, EmbeddingsFile embeddingsFile, OutputFile outputFile) {
        super(filePath);
        this.embeddingsFile = embeddingsFile;
        this.outputFile = outputFile;
    }

    /**
     * Processes the Google 1000 file by loading words into a map, retrieving their embeddings,
     * and writing the results to the output file.
     *
     * @throws IOException           if an I/O error occurs while reading or writing files.
     * @throws InterruptedException  if the thread is interrupted while processing.
     */
    @Override
    public void process() throws IOException, InterruptedException {

        // Load all words and their embeddings into a map
        wordEmbeddingsMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile.getFilePath()))) {

            String word;

            // Read each word from the file and load its embedding
            while ((word = br.readLine()) != null) {
                word = word.trim();
                double[] embedding = EmbeddingsFile.getEmbeddings().get(word);

                if (embedding != null) {
                    wordEmbeddingsMap.put(word, embedding);
                } else {
                    if(Runner.isDebugging) {
                        System.out.println("No embedding found for word: " + word);
                    }
                }
            }

            bw.flush();
        }
    }

    public Map<String,double[]> getWordEmbeddings() {
        return wordEmbeddingsMap;
    }

    public void setWordEmbeddings(Map<String,double[]> wordEmbeddingsMap) {
        this.wordEmbeddingsMap = wordEmbeddingsMap;
    }
}
