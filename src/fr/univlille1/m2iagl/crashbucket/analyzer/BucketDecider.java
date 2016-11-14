package fr.univlille1.m2iagl.crashbucket.analyzer;

import fr.univlille1.m2iagl.crashbucket.structure.Crash;
import fr.univlille1.m2iagl.crashbucket.structure.StacktraceLine;
import java.io.File;
import java.util.*;

public class BucketDecider {

    private final Map<String,Crash> trainingRessourcesStacktrace;
    private final Map<String,Crash> testingRessourcesStacktrace;
    private final Map<File,String> trainingRessourcesContent;
    private final Map<File,String> testingRessourcesContent;
    private final File testingRessourceFile;

    public BucketDecider(Map<String,Crash> trainingRessourcesStacktrace,
			Map<String,Crash> testingRessourcesStacktrace, Map<File, String> trainingRessourcesContent,
			Map<File,String> testingRessourcesContent, File testingRessourceFile) {
		super();
		this.trainingRessourcesStacktrace = trainingRessourcesStacktrace;
		this.testingRessourcesStacktrace = testingRessourcesStacktrace;
		this.trainingRessourcesContent = trainingRessourcesContent;
		this.testingRessourcesContent = testingRessourcesContent;
		this.testingRessourceFile = testingRessourceFile;
	}

    public void InitiateAssignment() {
        Map<String,Double> MatchedBucketId = new HashMap<>();
        for (File trainingRessourceFile : trainingRessourcesContent.keySet()) {
            if (!MatchedBucketId.containsKey(trainingRessourceFile.getParentFile().getParent()))
                MatchedBucketId.put(trainingRessourceFile.getParentFile().getParent(), 0.0);
            ArrayList<Double> matchedLineTotalScore = new ArrayList<>();
            for (StacktraceLine stacktraceLine : testingRessourcesStacktrace.get(testingRessourcesContent.get(testingRessourceFile))
                    .getStacktraceLines())
                if (trainingRessourcesStacktrace.get(trainingRessourcesContent.get(trainingRessourceFile)) != null)
                    for (StacktraceLine crashLineTraining : trainingRessourcesStacktrace.get(trainingRessourcesContent.get(trainingRessourceFile))
                            .getStacktraceLines()) {
                        double matchScore = StacktraceLinesPointsDecider.getStacktraceLinesComparaisonScore(stacktraceLine, crashLineTraining);
                        matchedLineTotalScore.add(matchScore);
					}
			double scoreResult = 0.0;
			for (Double distance : matchedLineTotalScore)
                            scoreResult += distance;
                        scoreResult /= matchedLineTotalScore.size();
                        if (MatchedBucketId.get(trainingRessourceFile.getParentFile().getParent()) <= scoreResult) {
                            MatchedBucketId.put(trainingRessourceFile.getParentFile().getParent(), scoreResult);
                        }
        }
        String bucketId = null;
        Double highestMatchedScore = 0.0;
        for (String matchedBucketId : MatchedBucketId.keySet()) {
            if (MatchedBucketId.get(matchedBucketId) > highestMatchedScore) {
                highestMatchedScore = MatchedBucketId.get(matchedBucketId);
                bucketId = matchedBucketId.substring(matchedBucketId.length() - 9);
            }
        }
        StacktraceAnalyzer.assignementResult.put(testingRessourceFile.getName().substring(0, testingRessourceFile.getName().length() - 4), bucketId);
    }
}
