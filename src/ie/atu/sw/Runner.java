package ie.atu.sw;

import ie.atu.sw.files.EmbeddingsFile;
import ie.atu.sw.files.Google1000File;
import ie.atu.sw.files.OutputFile;
import ie.atu.sw.utils.ConsoleColour;

import java.io.Console;
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
	 * The main method that runs the application.
	 *
	 * @param args command-line arguments
	 * @throws Exception if an error occurs during file processing.
	 */
	public static void main(String[] args) throws Exception {

		EmbeddingsFile embeddingsFile = null;
		Google1000File google1000File = null;
		OutputFile outputFile = new OutputFile("./out.txt");

		boolean exit = false;

		while (!exit) {
			displayMenu(embeddingsFile, google1000File, outputFile);

			System.out.print("Select Option [1-?]> ");
			String choice = scanner.nextLine();

			switch (choice) {
				case "1":
					System.out.print("Enter the path for the Embeddings File: ");
					embeddingsFile = new EmbeddingsFile(scanner.nextLine());
					embeddingsFile.process();
					break;
				case "2":
					if (embeddingsFile == null) {
						System.out.println("Please specify the Embeddings File first.");
						break;
					}
					System.out.print("Enter the path for the Google 1000 File: ");
					google1000File = new Google1000File(scanner.nextLine(), embeddingsFile, outputFile);
					break;
				case "3":
					System.out.print("Enter the path for the Output File: ");
					outputFile.setFilePath(scanner.nextLine());
					break;
				case "4":
					if (embeddingsFile != null && google1000File != null) {
						google1000File.process();
					} else {
						System.out.println("Please specify all required files before proceeding.");
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
	private static void displayMenu(EmbeddingsFile embeddingsFile, Google1000File google1000File, OutputFile outputFile) {
		System.out.println("************************************************************");
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

		System.out.println("(3) Specify an Output File (default: " + outputFile.getFilePath() + ")");
		System.out.print(ConsoleColour.RESET);

		if (embeddingsFile == null || google1000File == null) {
			System.out.print(ConsoleColour.RED_BOLD);
			System.out.println("(4) Execute and Save Results");
			System.out.print(ConsoleColour.RESET);
		} else {
			System.out.print(ConsoleColour.WHITE_BOLD);
			System.out.println("(4) Execute and Save Results");
			System.out.print(ConsoleColour.RESET);
		}

		System.out.println("(?) Quit");
		System.out.print(ConsoleColour.RESET);
	}
}
