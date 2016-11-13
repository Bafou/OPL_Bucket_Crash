package fr.univlille1.m2iagl.crashbucket.analyzer;

import fr.univlille1.m2iagl.crashbucket.structure.StacktraceLine;
import fr.univlille1.m2iagl.crashbucket.structure.Crash;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            ArrayList<Double> matchedLine = new ArrayList<>();
            for (StacktraceLine crashLineTesting : testingRessourcesStacktrace.get(testingRessourcesContent.get(testingRessourceFile))
                    .getCrashLines())
                if (trainingRessourcesStacktrace.get(trainingRessourcesContent.get(trainingRessourceFile)) != null)
                    for (StacktraceLine crashLineTraining : trainingRessourcesStacktrace.get(trainingRessourcesContent.get(trainingRessourceFile))
                            .getCrashLines()) {
                        double matchScore = StacktraceLinesPointsDecider.getStacktraceLinesComparaisonPoints(crashLineTesting, crashLineTraining);
                        matchedLine.add(matchScore);
					}
			double sum = 0;
			for (Double distance : matchedLine)
                            sum += distance;
                        sum /= matchedLine.size();
                        if (MatchedBucketId.get(trainingRessourceFile.getParentFile().getParent()) < sum) {
                            MatchedBucketId.put(trainingRessourceFile.getParentFile().getParent(), sum);
                        }
        }
        String bucketId = null;
        Double maxMatchedValue = 0.0;
        for (String bucketName : MatchedBucketId.keySet()) {
            if (MatchedBucketId.get(bucketName) > maxMatchedValue) {
                maxMatchedValue = MatchedBucketId.get(bucketName);
                bucketId = bucketName.substring(bucketName.length() - 9);
            }
        }
        StacktraceAnalyzer.assignementResult.put(testingRessourceFile.getName().substring(0, testingRessourceFile.getName().length() - 4), bucketId);
    }
}
