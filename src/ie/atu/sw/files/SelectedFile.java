package ie.atu.sw.files;

import ie.atu.sw.Runner;
import ie.atu.sw.utils.Methods;

import java.io.*;
import java.util.concurrent.*;

import static ie.atu.sw.utils.Methods.findBestSynonym;

public class SelectedFile extends ProgramFile {

    private EmbeddingsFile embeddingsFile;
    private Google1000File google1000File;
    private OutputFile outputFile;

    public SelectedFile(String filePath, EmbeddingsFile embeddingsFile, Google1000File google1000File, OutputFile outputFile) {
        super(filePath);
        this.embeddingsFile = embeddingsFile;
        this.google1000File = google1000File;
        this.outputFile = outputFile;
    }

    @Override
    public void process() throws IOException, InterruptedException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile.getFilePath()))) {

            // Read the entire content of the file into a single string
            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append(" ");
            }

            String[] words = contentBuilder.toString().trim().split("\\s+");
            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();


            int index = 0;
            // Process words
            for (String word : words) {
                if (google1000File.getWordEmbeddings().containsKey(word)) {
                    String synonym = findBestSynonym(word);
                    if(Runner.isDebugging) {
                        System.out.println("Swapping word: '" + word + "' with synonym: '" + synonym + "'");
                    }
                    Methods.printProgress(index++, words.length);
                    bw.write(synonym + " ");
                } else {
                    if(Runner.isDebugging) {
                        System.out.println("No synonym found for word: '" + word + "', keeping original.");
                    }
                    bw.write(word + " ");
                }
            }

            executor.shutdown();
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.err.println("Executor did not terminate in time.");
            }

            bw.flush();
        }
    }
}
