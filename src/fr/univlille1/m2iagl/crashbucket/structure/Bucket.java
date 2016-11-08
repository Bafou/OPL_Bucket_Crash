package fr.univlille1.m2iagl.crashbucket.structure;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    
    private String bucketId;
    private List<Crash> crash; 
    	
    public Bucket(String bucketId){
        this.bucketId=bucketId;
        this.crash = new ArrayList();  
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public List<Crash> getCrash() {
        return crash;
    }  
}
