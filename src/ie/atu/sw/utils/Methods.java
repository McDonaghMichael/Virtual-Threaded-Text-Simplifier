package ie.atu.sw.utils;

import ie.atu.sw.files.EmbeddingsFile;
import java.util.Map;

/**
 * The {Methods} class provides methods for the project
 */
public class Methods {

    /**
     * Finds the best synonym for a given word based on cosine similarity.
     *
     * @param word the word to find the best synonym for.
     * @return the best synonym for the word.
     *
     * @runtimeNotation O(n * m), where n is the number of embeddings and m is the length of each embedding vector.
     * @runtimeExplanation For each word in the embeddings map, we compute the cosine similarity which requires iterating over the vector.
     */
    public static String findBestSynonym(String word) {

        // First we will get all of our embeddings
        Map<String, double[]> embeddings = EmbeddingsFile.getEmbeddings();

        // Depending on the word chosen we will now store all the vectors of that word
        double[] wordEmbedding = embeddings.get(word);

        // If none are found just return the same word
        if (wordEmbedding == null) return word;

        // Default value if none are found
        String bestMatch = null;
        double maxSimilarity = -1;

        // Now we will iterate through the embeddings to find the best match
        for (Map.Entry<String, double[]> entry : embeddings.entrySet()) {

            // If the word entered is the same best chosen synonym as the word entered, we skip
            if (!word.equals(entry.getKey())) {

                // Now we simply calculate the cosine similarity of the word and each of its vectors
                double similarity = computeCosineSimilarity(wordEmbedding, entry.getValue());

                // If the current vector is even more similar than the previous, swap it out
                if (similarity > maxSimilarity) {
                    maxSimilarity = similarity;
                    bestMatch = entry.getKey();
                }
            }
        }
        return bestMatch != null ? bestMatch : word;
    }

    /**
     * Method for computing cosine similarity.
     *
     * @param vec1 the first vector.
     * @param vec2 the second vector.
     * @return the cosine similarity between the two vectors.
     *
     * @runtimeNotation O(m), where m is the length of the vectors.
     * @runtimeExplanation The computation involves iterating through each of the vectors once.
     */
    public static double computeCosineSimilarity(double[] vec1, double[] vec2) {
        double dotProduct = 0.0, magnitude1 = 0.0, magnitude2 = 0.0;
        for (int i = 0; i < vec1.length; i++) {
            double v1 = vec1[i], v2 = vec2[i];
            dotProduct += v1 * v2;
            magnitude1 += v1 * v1;
            magnitude2 += v2 * v2;
        }

        double calcualtion = dotProduct / (Math.sqrt(magnitude1) * Math.sqrt(magnitude2));
        return calcualtion;
    }

    /**
     * Prints a progress bar to the console to indicate the progress of a task.
     *
     * @param index the current progress index (e.g., the number of completed tasks).
     * @param total the total number of tasks to complete.
     *
     **/

    public static void printProgress(int index, int total) {
        if (index > total) return;	//Out of range
        int size = 50; 				//Must be less than console width
        char done = '█';			//Change to whatever you like.
        char todo = '░';			//Change to whatever you like.

        //Compute basic metrics for the meter
        int complete = (100 * index) / total;
        int completeLen = size * complete / 100;

        /*
         * A StringBuilder should be used for string concatenation inside a
         * loop. However, as the number of loop iterations is small, using
         * the "+" operator may be more efficient as the instructions can
         * be optimized by the compiler. Either way, the performance overhead
         * will be marginal.
         */
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append((i < completeLen) ? done : todo);
        }

        /*
         * The line feed escape character "\r" returns the cursor to the
         * start of the current line. Calling print(...) overwrites the
         * existing line and creates the illusion of an animation.
         */
        System.out.print("\r" + sb + "] " + complete + "%");

        //Once the meter reaches its max, move to a new line.
        if (done == total) System.out.println("\n");
    }


}
