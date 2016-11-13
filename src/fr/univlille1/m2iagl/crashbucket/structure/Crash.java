package fr.univlille1.m2iagl.crashbucket.structure;

import java.util.*;

public class Crash {

    private Bucket bucket;
    private List<StacktraceLine> stacktraceLines;

    public Crash() {
        super();
        stacktraceLines = new ArrayList<>();
    }
    
    public Bucket getBucket(){
    	return this.bucket;
    }

    public List<StacktraceLine> getStacktraceLines() {
        return stacktraceLines;
    }

    public void addCrashLines(StacktraceLine stacktraceLine) {
        stacktraceLines.add(stacktraceLine);
    }
}