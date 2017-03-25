package fr.univlille1.m2iagl.crashbucket.analyzer;

import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.StacktraceLineDataParser;
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
                        score += data.getScore();
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
}