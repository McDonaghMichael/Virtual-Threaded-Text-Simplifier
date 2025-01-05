package ie.atu.sw;

import ie.atu.sw.files.EmbeddingsFile;
import ie.atu.sw.files.Google1000File;
import ie.atu.sw.files.OutputFile;
import ie.atu.sw.files.SelectedFile;
import ie.atu.sw.utils.ConsoleColour;

import java.io.File;
import java.util.*;

/**
 * The {@code Runner} class is the main class for the project.
 * It provides a menu-driven interface for specifying input files, processing data, and saving results.
 */
public class Runner {

	/**
	 * A {@code Scanner} instance for reading user input from the console.
	 */
	private static Scanner scanner = new Scanner(System.in);

	/**
	 * A {@code isDebugging} instance for whether to show the actual words being processed
	 */
	public static Boolean isDebugging = false;

	/**
	 * The main method that runs the application.
	 *
	 * @param args command-line arguments
	 * @throws Exception if an error occurs during file processing.
	 */
	public static void main(String[] args) throws Exception {

		EmbeddingsFile embeddingsFile = null;
		Google1000File google1000File = null;
		SelectedFile selectedFile = null;
		OutputFile outputFile = new OutputFile("./out.txt");

		boolean exit = false;

		while (!exit) {
			displayMenu(embeddingsFile, google1000File, outputFile, selectedFile);

			System.out.print("Select Option [1-?]> ");
			String choice = scanner.nextLine();

			switch (choice) {
				case "1":
					System.out.print("Enter the path for the Embeddings File: ");
					String embeddingsPath = scanner.nextLine();
					if (new File(embeddingsPath).exists()) {
						embeddingsFile = new EmbeddingsFile(embeddingsPath);
						embeddingsFile.process();
					} else {
						System.out.println("File does not exist. Please try again.");
					}
					break;
				case "2":
					System.out.print("Enter the path for the Google 1000 File: ");
					String googlePath = scanner.nextLine();
					if (new File(googlePath).exists()) {
						google1000File = new Google1000File(googlePath, embeddingsFile, outputFile);
						google1000File.process();
					} else {
						System.out.println("File does not exist. Please try again.");
					}
					break;
				case "3":
					System.out.print("Enter the path for the Text File: ");
					String selectedPath = scanner.nextLine();
					if (new File(selectedPath).exists()) {
						selectedFile = new SelectedFile(selectedPath, embeddingsFile, google1000File, outputFile);
					} else {
						System.out.println("File does not exist. Please try again.");
					}
					break;
				case "4":
					System.out.print("Enter the path for the Output File: ");
					String outputPath = scanner.nextLine();
					outputFile.setFilePath(outputPath);
					System.out.println("Output file path set to: " + outputPath);
					break;
				case "5":
					if (embeddingsFile != null && google1000File != null && selectedFile != null) {
						selectedFile.process();
					} else {
						System.out.println("Please specify all required files before proceeding.");
					}
					break;
				case "6":
					if (isDebugging) {
						isDebugging = false;
						System.out.println("Debugging is set to false.");
					} else {
						isDebugging = true;
						System.out.println("Debugging is set to true.");
					}
					break;
				case "?":
					exit = true;
					System.out.println("Exiting...");
					break;
				default:
					System.out.println("Invalid option. Please try again.");
			}
		}
	}

	/**
	 * Displays the main menu of the application.
	 *
	 * @param outputFile the {@code OutputFile} object to display the default output file path.
	 */
	private static void displayMenu(EmbeddingsFile embeddingsFile, Google1000File google1000File, OutputFile outputFile, SelectedFile selectedFile) {
		System.out.println("\n************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*             Virtual Threaded Text Simplifier             *");
		System.out.println("************************************************************");

		if (embeddingsFile == null) {
			System.out.print(ConsoleColour.RED_BOLD);
			System.out.println("(1) Specify Embeddings File");
			System.out.print(ConsoleColour.RESET);
		} else {
			System.out.print(ConsoleColour.WHITE_BOLD);
			System.out.println("(1) Specify Embeddings File");
			System.out.print(ConsoleColour.RESET);
		}

		if (google1000File == null) {
			System.out.print(ConsoleColour.RED_BOLD);
			System.out.println("(2) Specify Google 1000 File");
			System.out.print(ConsoleColour.RESET);
		} else {
			System.out.print(ConsoleColour.WHITE_BOLD);
			System.out.println("(2) Specify Google 1000 File");
			System.out.print(ConsoleColour.RESET);
		}

		if (selectedFile == null) {
			System.out.print(ConsoleColour.RED_BOLD);
			System.out.println("(3) Specify Selected Text File");
			System.out.print(ConsoleColour.RESET);
		} else {
			System.out.print(ConsoleColour.WHITE_BOLD);
			System.out.println("(3) Specify Selected Text File");
			System.out.print(ConsoleColour.RESET);
		}

		System.out.print(ConsoleColour.WHITE_BOLD);
		System.out.println("(4) Specify an Output File (default: " + outputFile.getFilePath() + ")");
		System.out.print(ConsoleColour.RESET);

		if (embeddingsFile == null || google1000File == null || selectedFile == null) {
			System.out.print(ConsoleColour.RED_BOLD);
			System.out.println("(5) Execute and Save Results");
			System.out.print(ConsoleColour.RESET);
		} else {
			System.out.print(ConsoleColour.WHITE_BOLD);
			System.out.println("(5) Execute and Save Results");
			System.out.print(ConsoleColour.RESET);
		}

		if (isDebugging) {
			System.out.print(ConsoleColour.WHITE_BOLD);
			System.out.print("(6) Debugging ");
			System.out.print(ConsoleColour.GREEN_UNDERLINED);
			System.out.print("Enabled");
			System.out.println(ConsoleColour.RESET);
		} else {
			System.out.print(ConsoleColour.WHITE_BOLD);
			System.out.print("(6) Debugging ");
			System.out.print(ConsoleColour.RED_UNDERLINED);
			System.out.print("Disabled");
			System.out.println(ConsoleColour.RESET);
		}

		System.out.print(ConsoleColour.WHITE_BOLD);
		System.out.println("(?) Quit");
		System.out.print(ConsoleColour.RESET);
	}
}
