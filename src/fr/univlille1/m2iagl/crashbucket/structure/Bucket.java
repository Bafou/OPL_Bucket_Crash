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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bucketId == null) ? 0 : bucketId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bucket other = (Bucket) obj;
		if (bucketId == null) {
			if (other.bucketId != null)
				return false;
		} else if (!bucketId.equals(other.bucketId))
			return false;
		return true;
	}

}
