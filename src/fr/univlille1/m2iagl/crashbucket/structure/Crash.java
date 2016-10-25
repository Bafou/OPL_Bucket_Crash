package fr.univlille1.m2iagl.crashbucket.structure;

import java.util.ArrayList;
import java.util.List;

public class Crash {
    
    private Bucket bucket;
    private List<StacktraceLine> stacktraceLines; 
    
    public Crash(Bucket bucket) {
    	super();
    	this.bucket = bucket;
        stacktraceLines = new ArrayList();
    }

    public Crash() {
        super();
        stacktraceLines = new ArrayList();
    }
    
    public Bucket getBucket(){
    	return this.bucket;
    }
    
    public void addStacktraceLine(StacktraceLine stacktraceLine){
        this.stacktraceLines.add(stacktraceLine);
    }
    
    public List<StacktraceLine> getStacktraceLine(){
        return this.stacktraceLines;
    }  
}
