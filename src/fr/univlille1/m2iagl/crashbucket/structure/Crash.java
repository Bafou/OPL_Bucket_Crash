package fr.univlille1.m2iagl.crashbucket.structure;

import java.util.ArrayList;
import java.util.List;

public class Crash {

    private Bucket bucket;
    private List<StacktraceLine> crashLines;

    public Crash() {
        super();
        crashLines = new ArrayList<>();
    }
    
    public Bucket getBucket(){
    	return this.bucket;
    }

    public List<StacktraceLine> getCrashLines() {
        return crashLines;
    }

    public void addCrashLines(StacktraceLine crashLine) {
        crashLines.add(crashLine);
    }
}