package fr.univlille1.m2iagl.crashbucket.analyzer;

import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.StacktraceData;
import fr.univlille1.m2iagl.crashbucket.structure.StacktraceLine;

public class StacktraceLinesPointsDecider {

	public static double getStacktraceLinesComparaisonPoints(StacktraceLine crashLine1, StacktraceLine crashLine2) {
		if (crashLine1 == null || crashLine2 == null || crashLine1.getLineNumber() == null
				|| crashLine2.getLineNumber() == null)
			return 0.0;
		else {
			return getSimilarityPoints(crashLine1, crashLine2);
		}
	}

	private static double getSimilarityPoints(StacktraceLine crashLine1, StacktraceLine crashLine2) {
		Double score = 0.0;
		if (crashLine1.getLineNumber().toString().equals(crashLine2.getLineNumber().toString()))
			score += 200;
		for (StacktraceData keyword1 : crashLine1.getKeywords())
			for (StacktraceData keyword2 : crashLine2.getKeywords())
				if (!keyword1.getValue().equals("??") && !keyword2.getValue().equals("??"))
					if (keyword1.getClass().getName().equals(keyword2.getClass().getName())
							&& keyword1.getValue().equals(keyword2.getValue()))
						score += 200;
		return score;
	}
}