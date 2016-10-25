package fr.univlille1.m2iagl.crashbucket.base;

import java.util.ArrayList;

public class Stacktrace extends ArrayList<StacktraceElement>{
    private String stackTraceNumber;
    private boolean initial = true;
    private Crash bucket;
    private boolean inBucket;
    
    public String getStackTraceNumber() {
        return stackTraceNumber;
    }

    public Stacktrace() {
    	super();
        initial = true;
        this.inBucket = false;
    }
    
    public Stacktrace(Crash bucket) {
    	super();
        initial = true;
        this.bucket = bucket;
        this.inBucket = true;
    }
    
    public Crash getBucket(){
    	return this.bucket;
    }
    
    public Bucket getMainBucket(){
    	return this.getBucket().getBucket();
    }

    public boolean inBucket(){
    	return this.inBucket;
    }
}
