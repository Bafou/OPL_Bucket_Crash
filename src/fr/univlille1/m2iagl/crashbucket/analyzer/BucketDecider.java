package fr.univlille1.m2iagl.crashbucket.analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.StacktraceLineDataParser;
import fr.univlille1.m2iagl.crashbucket.structure.Bucket;
import fr.univlille1.m2iagl.crashbucket.structure.Stacktrace;

/**
 * Class that will calculate the score between a stacktrace and all the buckets
 * existing in the actual environment
 * 
 * @author Antoine PETIT
 *
 */
public class BucketDecider {

	private final Map<String, Bucket> trainingRessourcesStacktrace;
	private final Map<String, Stacktrace> testingRessourcesStacktrace;

	public BucketDecider(final Map<String, Bucket> trainingRessourcesStacktrace,
			Map<String, Stacktrace> testingRessourcesStacktrace) {
		super();
		this.trainingRessourcesStacktrace = trainingRessourcesStacktrace;
		this.testingRessourcesStacktrace = testingRessourcesStacktrace;
	}

	public BucketDecider(final Map<String, Bucket> trainingRessourcesStacktrace) {
		super();
		this.trainingRessourcesStacktrace = trainingRessourcesStacktrace;
		this.testingRessourcesStacktrace = new HashMap<String, Stacktrace>();
	}

	/**
	 * Return the bucket where the crash should be set
	 * @param crash the crash treated
	 * @return the bucket where the crash should be set
	 */
	public String decideBucket(final Stacktrace crash) {
		Double highestMatchedScore = 0.0;
		String selectedBucket = "";
		for (final String bucketId : trainingRessourcesStacktrace.keySet()) {
			final Bucket trainingBucket = trainingRessourcesStacktrace.get(bucketId);
			double matchScore = getStacktraceBucketComparaisonScore(crash, trainingBucket);
			if (matchScore > highestMatchedScore) {
				selectedBucket = bucketId;
				highestMatchedScore = matchScore;
			}
		}
		return selectedBucket;
	}
	
	
	/**
	 * Begin the assignement between the testingRessourcesStacktrace containing
	 * all the stacktrace to categorise and the trainingRessourcesStacktrace
	 * containing all the bucket
	 */
	public void InitiateAssignment() {
		for (final String crashId : testingRessourcesStacktrace.keySet()) {
			final Stacktrace testingCrash = testingRessourcesStacktrace.get(crashId);

			StacktraceAnalyzer.assignementResult.put(crashId, decideBucket(testingCrash));
		}

	}

	/**
	 * Calculate the comparaison score between a stacktrace (crash) and a bucket
	 * 
	 * @param testingCrash
	 *            the stacktrace that will be compared
	 * @param bucket
	 *            the bucket that will be compared
	 * @return the score of "match" between the stacktrace and the bucket
	 */
	public static double getStacktraceBucketComparaisonScore(final Stacktrace testingCrash, final Bucket bucket) {
		double currentScore = 0.0;
		for (final Stacktrace crash : bucket.getCrash()) {
			int score = 0;
			int nbMatch = 0;
			for (StacktraceLineDataParser data : testingCrash.getAllData()) {
				List<Integer> testingDataLines = data.getApparitionLineNumber();
				if (crash.getAllData().contains(data)) {
					StacktraceLineDataParser trainingData = crash.getAllData().get(crash.getAllData().indexOf(data));
					List<Integer> trainingDataLines = trainingData.getApparitionLineNumber();
					nbMatch += Integer.min(testingDataLines.size(), trainingDataLines.size());

					for (int i = 0; i < Integer.min(testingDataLines.size(), trainingDataLines.size()); i++) {
						score += data.getScore() / ((testingDataLines.get(i) + 1) * (trainingDataLines.get(i) + 1));
					}
				}

			}
			double percentTesting = nbMatch * 100.0 / testingCrash.getStacktraceLines().size();
			double percentTraining = nbMatch * 100.0 / crash.getStacktraceLines().size();

			double averageTestTrain = (percentTesting + percentTraining) / 2.0;

			score += averageTestTrain;

			if (currentScore < score) {
				currentScore = score;
			}
		}

		return currentScore;

	}
	
	public void addToBucket(final String bucketId, final Stacktrace crash) {
		trainingRessourcesStacktrace.get(bucketId).addStacktrace(crash);	
	}
	
	public void removeFromBucket(final String bucketId, final Stacktrace crash) {
		trainingRessourcesStacktrace.get(bucketId).getCrash().remove(crash);	
	}
}
