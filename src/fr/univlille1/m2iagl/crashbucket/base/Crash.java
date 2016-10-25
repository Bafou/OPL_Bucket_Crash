package fr.univlille1.m2iagl.crashbucket.base;

import java.util.ArrayList;

public class Crash extends ArrayList<Stacktrace>{
    
    private String bucketId;
    private Bucket bucket;
    
    public Crash(Bucket bucket) {
    	super();
    	this.bucket = bucket;
    }

    public Crash(String bucketId) {
        super();
        this.bucketId = bucketId;
    }
    
    public String getBucketId() {
        return bucketId;
    }
    
    public void setBucketId(String bucketId){
    	this.bucketId = bucketId;
    }
    
    public Bucket getBucket(){
    	return this.bucket;
    }
}
