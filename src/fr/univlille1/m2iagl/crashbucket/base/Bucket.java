package fr.univlille1.m2iagl.crashbucket.base;

import java.util.ArrayList;

public class Bucket extends ArrayList<Stacktrace>{
    
    private String bucketId;
    private MainBucket mainBucket;
    
    public Bucket(MainBucket mainBucket) {
    	super();
    	this.mainBucket = mainBucket;
    }

    public Bucket(String bucketId) {
        super();
        this.bucketId = bucketId;
    }
    
    public String getBucketId() {
        return bucketId;
    }
    
    public void setBucketId(String bucketId){
    	this.bucketId = bucketId;
    }
    
    public MainBucket getMainBucket(){
    	return this.mainBucket;
    }
}
