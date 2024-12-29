package ie.atu.sw.utils;

import ie.atu.sw.files.EmbeddingsFile;

import java.util.Map;

/**
 * The {@code Methods} class provides utility methods for working with word embeddings
 * and calculating similarities between words.
 */
public class Methods {

    /**
     * Finds the best synonym for a given word based on cosine similarity.
     *
     * @param word the word to find the best synonym for.
     * @return the best synonym for the word, or the word itself if no suitable synonym is found.
     *
     * Running time: O(n * m), where n is the number of embeddings and m is the length of each embedding vector.
     * Rationale: For each word in the embeddings map, we compute the cosine similarity which requires iterating over the vector.
     */
    public static String findBestSynonym(String word) {
        Map<String, double[]> embeddings = EmbeddingsFile.getEmbeddings();
        double[] wordEmbedding = embeddings.get(word);
        if (wordEmbedding == null) return word;

        String bestMatch = null;
        double maxSimilarity = -1;

        // Iterate through the embeddings to find the best match
        for (Map.Entry<String, double[]> entry : embeddings.entrySet()) {
            if (word.equals(entry.getKey())) continue; // Skip matching with the same word
            double similarity = computeCosineSimilarity(wordEmbedding, entry.getValue());
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                bestMatch = entry.getKey();
            }
        }

        // Print the result
        if (bestMatch != null) {
            System.out.println("Word: " + word + " -> Best Match: " + bestMatch);
        } else {
            System.out.println("Word: " + word + " -> No suitable match found.");
        }

        return bestMatch != null ? bestMatch : word;
    }

    /**
     * Computes the cosine similarity between two vectors.
     *
     * @param vec1 the first vector.
     * @param vec2 the second vector.
     * @return the cosine similarity between the two vectors.
     *
     * Running time: O(m), where m is the length of the vectors.
     * Rationale: The computation involves iterating through each dimension of the vector once.
     */
    public static double computeCosineSimilarity(double[] vec1, double[] vec2) {
        double dotProduct = 0.0, magnitude1 = 0.0, magnitude2 = 0.0;
        for (int i = 0; i < vec1.length; i++) {
            dotProduct += vec1[i] * vec2[i];
            magnitude1 += vec1[i] * vec1[i];
            magnitude2 += vec2[i] * vec2[i];
        }
        return dotProduct / (Math.sqrt(magnitude1) * Math.sqrt(magnitude2));
    }
}
