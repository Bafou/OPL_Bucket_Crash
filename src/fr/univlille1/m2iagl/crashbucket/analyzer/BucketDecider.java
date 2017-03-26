package fr.univlille1.m2iagl.crashbucket.analyzer;

import java.io.File;
import java.util.Map;

import fr.univlille1.m2iagl.crashbucket.structure.Bucket;
import fr.univlille1.m2iagl.crashbucket.structure.Crash;

public class BucketDecider {

	private final Map<String, Bucket> trainingRessourcesStacktrace;
	private final Map<String, Crash> testingRessourcesStacktrace;
	private final Map<File, String> trainingRessourcesContent;
	private final Map<File, String> testingRessourcesContent;

	public BucketDecider(Map<String, Bucket> trainingRessourcesStacktrace,
			Map<String, Crash> testingRessourcesStacktrace, Map<File, String> trainingRessourcesContent,
			Map<File, String> testingRessourcesContent) {
		super();
		this.trainingRessourcesStacktrace = trainingRessourcesStacktrace;
		this.testingRessourcesStacktrace = testingRessourcesStacktrace;
		this.trainingRessourcesContent = trainingRessourcesContent;
		this.testingRessourcesContent = testingRessourcesContent;
	}

	public void InitiateAssignment() {
		for (String crashId : testingRessourcesStacktrace.keySet()) {
			Crash testingCrash = testingRessourcesStacktrace.get(crashId);

			Double highestMatchedScore = 0.0;
			String selectedBucket = "";
			for (String bucketId : trainingRessourcesStacktrace.keySet()) {
				Bucket trainingBucket = trainingRessourcesStacktrace.get(bucketId);
				double matchScore = StacktraceLinesPointsDecider.getStacktraceLinesComparaisonScore(testingCrash,
						trainingBucket);
				if (matchScore > highestMatchedScore) {
					selectedBucket = bucketId;
					highestMatchedScore = matchScore;
				}
			}

			StacktraceAnalyzer.assignementResult.put(
					crashId, selectedBucket);
		}

	}
}
