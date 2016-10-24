package fr.univlille1.m2iagl.crashbucket.base;

import java.util.ArrayList;

public class Stacktrace extends ArrayList<StacktraceElement>{
    private String stackTraceNumber;
    private boolean initial = true;
    private Bucket bucket;
    private boolean inBucket;
    
    public String getStackTraceNumber() {
        return stackTraceNumber;
    }

    public Stacktrace() {
    	super();
        initial = true;
        this.inBucket = false;
    }
    
    public Stacktrace(Bucket bucket) {
    	super();
        initial = true;
        this.bucket = bucket;
        this.inBucket = true;
    }
    
    public Bucket getBucket(){
    	return this.bucket;
    }
    
    public MainBucket getMainBucket(){
    	return this.getBucket().getMainBucket();
    }

    public boolean inBucket(){
    	return this.inBucket;
    }
}
