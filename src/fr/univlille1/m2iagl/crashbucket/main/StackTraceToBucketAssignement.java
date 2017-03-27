package fr.univlille1.m2iagl.crashbucket.main;

import fr.univlille1.m2iagl.crashbucket.analyzer.StacktraceAnalyzer;
import fr.univlille1.m2iagl.crashbucket.constant.Constants;
import java.io.File;

/**
 * Main class of the project
 * 
 * It's able to take a stacktrace and put it in the correspondant bucket
 * 
 * In order to do this it use a similarity/point system
 * 
 * @author Antoine PETIT
 *
 */
public class StackTraceToBucketAssignement {

	/**
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {

		runFirstSet();
		StacktraceAnalyzer.assignementResult.clear();
		runSecondSet();
		StacktraceAnalyzer.assignementResult.clear();
		runThirdSet();

	}

	private static void runFirstSet() {
		final long startTime = System.nanoTime();
		final File trainingFolder = new File(Constants.TRAINING_PATH_1);
		final File testingFolder = new File(Constants.TESTING_PATH_1);
		final String outputFileName = Constants.ANALYSIS_ASSIGNEMENT_OUTPUT_FILE_1;
		final String nautilusFolderPath = Constants.HOME_PATH;

		final File file = new File(nautilusFolderPath + File.separator + outputFileName + ".txt");
		if (file.exists()) {
			file.delete();
		}
		StacktraceAnalyzer stacktraceAnalyzer = new StacktraceAnalyzer();
		System.out.println("------------------------------------------------------------------------" + "\n");
		System.out.println("Stacktrace Analysis started...");
		stacktraceAnalyzer.assignStacktraceToBucket(testingFolder, trainingFolder, outputFileName);
		final long endTime = System.nanoTime();
		final long duration = endTime - startTime;
		final double seconds = ((double) duration / 1000000000);
		System.out.println("Analysis successfully done in " + seconds + " Seconds. See result in " + nautilusFolderPath
				+ "\\" + outputFileName + ".txt" + "\n");
		System.out.println("------------------------------------------------------------------------");
	}

	private static void runSecondSet() {
		final long startTime = System.nanoTime();
		final File trainingFolder = new File(Constants.TRAINING_PATH_2);
		final File testingFolder = new File(Constants.TESTING_PATH_2);
		final String outputFileName = Constants.ANALYSIS_ASSIGNEMENT_OUTPUT_FILE_2;
		final String nautilusFolderPath = Constants.HOME_PATH;

		final File file = new File(nautilusFolderPath + File.separator + outputFileName + ".txt");
		if (file.exists()) {
			file.delete();
		}
		StacktraceAnalyzer stacktraceAnalyzer = new StacktraceAnalyzer();
		System.out.println("------------------------------------------------------------------------" + "\n");
		System.out.println("Stacktrace Analysis started...");
		stacktraceAnalyzer.assignStacktraceToBucket(testingFolder, trainingFolder, outputFileName);
		final long endTime = System.nanoTime();
		final long duration = endTime - startTime;
		final double seconds = ((double) duration / 1000000000);
		System.out.println("Analysis successfully done in " + seconds + " Seconds. See result in " + nautilusFolderPath
				+ "\\" + outputFileName + ".txt" + "\n");
		System.out.println("------------------------------------------------------------------------");
	}

	private static void runThirdSet() {
		final long startTime = System.nanoTime();
		final File trainingFolder = new File(Constants.TRAINING_PATH_3);
		final File testingFolder = new File(Constants.TESTING_PATH_3);
		final String outputFileName = Constants.ANALYSIS_ASSIGNEMENT_OUTPUT_FILE_3;
		final String nautilusFolderPath = Constants.HOME_PATH;

		final File file = new File(nautilusFolderPath + File.separator + outputFileName + ".txt");
		if (file.exists()) {
			file.delete();
		}
		StacktraceAnalyzer stacktraceAnalyzer = new StacktraceAnalyzer();
		System.out.println("------------------------------------------------------------------------" + "\n");
		System.out.println("Stacktrace Analysis started...");
		stacktraceAnalyzer.assignStacktraceToBucket(testingFolder, trainingFolder, outputFileName);
		final long endTime = System.nanoTime();
		final long duration = endTime - startTime;
		final double seconds = ((double) duration / 1000000000);
		System.out.println("Analysis successfully done in " + seconds + " Seconds. See result in " + nautilusFolderPath
				+ "\\" + outputFileName + ".txt" + "\n");
		System.out.println("------------------------------------------------------------------------");
	}
}
