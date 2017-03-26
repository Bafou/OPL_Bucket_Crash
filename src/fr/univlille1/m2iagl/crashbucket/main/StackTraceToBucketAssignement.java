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

		final long startTime = System.nanoTime();
		final File trainingFolder = new File(Constants.NAUTILUS_TRAINING_PATH);
		final File testingFolder = new File(Constants.NAUTILUS_TESTING_PATH);
		final String outputFileName = Constants.ANALYSIS_ASSIGNEMENT_OUTPUT_FILE;
		final String nautilusFolderPath = Constants.NAUTILUS_PATH;

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
