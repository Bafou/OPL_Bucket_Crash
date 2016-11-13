package fr.univlille1.m2iagl.crashbucket.main;

import fr.univlille1.m2iagl.crashbucket.analyzer.StacktraceAnalyzer;
import fr.univlille1.m2iagl.crashbucket.constant.Paths;
import java.io.File;

public class StackTraceToBucketAssignement {

    public static void main(String[] args) {
        
        final File trainingFolder = new File(Paths.NAUTILUS_TRAINING);
        final File testingFolder = new File(Paths.NAUTILUS_TESTING);
        
        StacktraceAnalyzer stacktraceAnalyzer = new StacktraceAnalyzer();
	stacktraceAnalyzer.assignStacktraceToBucket(testingFolder, trainingFolder);
    }
}
    

