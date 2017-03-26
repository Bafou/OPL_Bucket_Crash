package fr.univlille1.m2iagl.crashbucket.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * A bucket containing an group of crash (stacktrace) that represent probably the same issues
 * @author Antoine PETIT
 *
 */
public class Bucket {
    
    private String bucketId;
    private List<Crash> allCrashes; 
    	
    public Bucket(final String bucketId){
        this.bucketId=bucketId;
        this.allCrashes = new ArrayList<Crash>();  
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(final String bucketId) {
        this.bucketId = bucketId;
    }
    
   public void addCrash(final Crash crash) {
	   this.allCrashes.add(crash);
   }

    public List<Crash> getCrash() {
        return allCrashes;
    }

}
