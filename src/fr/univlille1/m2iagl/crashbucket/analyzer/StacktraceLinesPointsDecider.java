package fr.univlille1.m2iagl.crashbucket.analyzer;

import java.util.ArrayList;
import java.util.List;

import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.StacktraceLineDataParser;
import fr.univlille1.m2iagl.crashbucket.structure.Bucket;
import fr.univlille1.m2iagl.crashbucket.structure.Crash;
import fr.univlille1.m2iagl.crashbucket.structure.StacktraceLine;

public class StacktraceLinesPointsDecider {

	// Améliorer les scores !!
	private static double getSimilarityScore(final StacktraceLine stacktraceLine, final StacktraceLine stacktraceLineToCompare) {
        Double score = 0.0;
//        if (stacktraceLine.getLineNumber().equals(stacktraceLineToCompare.getLineNumber()))
//            score += 500.0;
        for (final StacktraceLineDataParser data : stacktraceLine.getStacktraceLineData())
        {
            for (final StacktraceLineDataParser dataToCompare : stacktraceLineToCompare.getStacktraceLineData()) {
                if (!data.getData().equals("??") && !dataToCompare.getData().equals("??"))
                    if (data.getClass().getName().equals(dataToCompare.getClass().getName())
                            && data.getData().equals(dataToCompare.getData()))
                        score += data.getScore()/(stacktraceLine.getLineNumber()*stacktraceLineToCompare.getLineNumber());
        			}
        }

        return score;
    }

	public static double getStacktraceLinesComparaisonScore(final StacktraceLine stacktraceLine,
			final StacktraceLine stacktraceLineToCompare) {
		if (stacktraceLine == null || stacktraceLineToCompare == null || stacktraceLine.getLineNumber() == null
				|| stacktraceLineToCompare.getLineNumber() == null)
			return 0.0;
		else {
			return getSimilarityScore(stacktraceLine, stacktraceLineToCompare);
		}
	}
	
	public static double getStacktraceLinesComparaisonScore(final Crash testingCrash,
			final Bucket bucket) {
		double currentScore = 0.0;
		for (Crash crash : bucket.getCrash()) {
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