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
public class StackTraceToBucketExercise {

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
		StacktraceAnalyzer stacktraceAnalyzer1 = new StacktraceAnalyzer();
		System.out.println("------------------------------------------------------------------------" + "\n");
		System.out.println("Stacktrace Analysis started...");
		System.out.println("Bucket bien trouvé : " + stacktraceAnalyzer1.testBucketDeciderOnBucket(trainingFolder));
		final long endTime = System.nanoTime();
		final long duration = endTime - startTime;
		final double seconds = ((double) duration / 1000000000);
		System.out.println("Analysis successfully done in " + seconds + " Seconds. " );
		System.out.println("------------------------------------------------------------------------");
	}

	private static void runSecondSet() {
		final long startTime = System.nanoTime();
		final File trainingFolder = new File(Constants.TRAINING_PATH_2);


		StacktraceAnalyzer stacktraceAnalyzer1 = new StacktraceAnalyzer();
		System.out.println("------------------------------------------------------------------------" + "\n");
		System.out.println("Stacktrace Analysis started...");
		System.out.println("Bucket bien trouvé : " + stacktraceAnalyzer1.testBucketDeciderOnBucket(trainingFolder));
		final long endTime = System.nanoTime();
		final long duration = endTime - startTime;
		final double seconds = ((double) duration / 1000000000);
		System.out.println("Analysis successfully done in " + seconds + " Seconds. " );
		System.out.println("------------------------------------------------------------------------");
	}

	private static void runThirdSet() {
		final long startTime = System.nanoTime();
		final File trainingFolder = new File(Constants.TRAINING_PATH_3);

		StacktraceAnalyzer stacktraceAnalyzer1 = new StacktraceAnalyzer();
		System.out.println("------------------------------------------------------------------------" + "\n");
		System.out.println("Stacktrace Analysis started...");
		System.out.println("Bucket bien trouvé : " + stacktraceAnalyzer1.testBucketDeciderOnBucket(trainingFolder));
		final long endTime = System.nanoTime();
		final long duration = endTime - startTime;
		final double seconds = ((double) duration / 1000000000);
		System.out.println("Analysis successfully done in " + seconds + " Seconds. " );
		System.out.println("------------------------------------------------------------------------");
	}
	
}
