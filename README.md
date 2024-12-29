## Virtual Threaded Text Simplifier
### Main Features
1. **Embeddings File Processing**:
- Class: `EmbeddingsFile`
- Method: `process`
- Loads large-scale word embeddings from a specified file into a `ConcurrentHashMap`.
- Implements asynchronous processing for efficient data handling.
- Big-O Complexity: O(n * m), where n is the number of words, and m is the vector size.

2. **Google 1000 File Handling**:
- Class: `Google1000File`
- Method: `process`
- Reads a list of input words and identifies the best synonyms using embeddings.
- Employs the `findBestSynonym` utility method to compute synonyms based on cosine similarity.
- Outputs the results to the specified file.
- Big-O Complexity: O(n * k * m), where n is the number of input words, k is the number of
  embedding words, and m is the vector size.

3. **Output File Management**:
- Class: `OutputFile`
- Placeholder `process` method for future enhancements in file-specific handling.

4. **Abstract File Management**:
- Class: `ProgramFile`
- Serves as a base class for all file-handling implementations.
- Defines the `process` abstract method for standardized operations across derived classes.

5. **Utility Methods**:
- Class: `Methods`
- Method: `findBestSynonym`
    - Finds the most contextually similar word using cosine similarity.
    - Skips identical matches to avoid redundant synonyms.
- Method: `computeCosineSimilarity`
    - Computes the cosine similarity between two numerical vectors.
    - Big-O Complexity: O(m), where m is the vector size.

6. **Interactive Runner**:
- Class: `Runner`
- Provides a dynamic menu interface for user interaction.
- Highlights missing file inputs with red text for clarity.
- Facilitates the overall workflow by coordinating inputs, processing, and outputs.

### Efficiency and Performance 
- **Virtual Threads**: Enhances parallelism and reduces blocking during large dataset operations.
- **Big-O Analysis**: Each method's efficiency has been optimized to handle real-world datasets.

### Usage
- Start the application and follow the menu prompts to specify file paths, process data, and save
results. 
- Designed for flexibility, allowing custom paths for all file types.
### Customization
- The modular design supports easy addition of new file handlers or processing methods.
- Classes and methods are documented with Javadoc for ease of maintenance and extension