package fr.univlille1.m2iagl.crashbucket.analyzer;

import java.util.List;
import java.util.Map;

import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.StacktraceLineDataParser;
import fr.univlille1.m2iagl.crashbucket.structure.Bucket;
import fr.univlille1.m2iagl.crashbucket.structure.Crash;

/**
 * Class that will calculate the score between a stacktrace and all the bucket existing in the actual environment
 * 
 * @author Antoine PETIT
 *
 */
public class BucketDecider {

	private final Map<String, Bucket> trainingRessourcesStacktrace;
	private final Map<String, Crash> testingRessourcesStacktrace;

	public BucketDecider(final Map<String, Bucket> trainingRessourcesStacktrace,
			Map<String, Crash> testingRessourcesStacktrace) {
		super();
		this.trainingRessourcesStacktrace = trainingRessourcesStacktrace;
		this.testingRessourcesStacktrace = testingRessourcesStacktrace;
	}

	/**
	 * Begin the assignement between the testingRessourcesStacktrace containing all the stacktrace to categorise and
	 * the trainingRessourcesStacktrace containing all the bucket
	 */
	public void InitiateAssignment() {
		for (final String crashId : testingRessourcesStacktrace.keySet()) {
			final Crash testingCrash = testingRessourcesStacktrace.get(crashId);

			Double highestMatchedScore = 0.0;
			String selectedBucket = "";
			for (final String bucketId : trainingRessourcesStacktrace.keySet()) {
				final Bucket trainingBucket = trainingRessourcesStacktrace.get(bucketId);
				double matchScore = getStacktraceBucketComparaisonScore(testingCrash,
						trainingBucket);
				if (matchScore > highestMatchedScore) {
					selectedBucket = bucketId;
					highestMatchedScore = matchScore;
				}
			}

			StacktraceAnalyzer.assignementResult.put(crashId, selectedBucket);
		}

	}
	
	/**
	 * Calculate the comparaison score between a stacktrace (crash) and a bucket
	 * @param testingCrash the stacktrace that will be compared
	 * @param bucket the bucket that will be compared
	 * @return the score of "match" between the stacktrace and the bucket
	 */
	public static double getStacktraceBucketComparaisonScore(final Crash testingCrash,
			final Bucket bucket) {
		double currentScore = 0.0;
		for (final Crash crash : bucket.getCrash()) {
			int score = 0;
			int nbMatch = 0;
			for (StacktraceLineDataParser data : testingCrash.getAllData()) {
				List<Integer> testingDataLines = data.getApparitionLineNumber();
				if (crash.getAllData().contains(data)) {
					StacktraceLineDataParser trainingData = crash.getAllData().get(crash.getAllData().indexOf(data));
					List<Integer> trainingDataLines = trainingData.getApparitionLineNumber();
					nbMatch += Integer.min(testingDataLines.size(),trainingDataLines.size());
					
					for (int i = 0; i < Integer.min(testingDataLines.size(),trainingDataLines.size()); i++) {
						score += data.getScore() / ((testingDataLines.get(i) + 1)  * (trainingDataLines.get(i)+1));
					}
				}
				
			}
			double percentTesting = nbMatch * 100.0 / testingCrash.getStacktraceLines().size();
			double percentTraining = nbMatch * 100.0 / crash.getStacktraceLines().size();
			
			double averageTestTrain = (percentTesting + percentTraining) /2.0;
			
			score += averageTestTrain /7.0;
			
			if (currentScore < score) {
				currentScore = score;
			}
		}
		
		return currentScore;
		

	}
}
