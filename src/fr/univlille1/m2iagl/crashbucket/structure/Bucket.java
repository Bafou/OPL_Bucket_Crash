package fr.univlille1.m2iagl.crashbucket.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * A bucket containing a group of stacktrace that represent probably the same issues
 * @author Antoine PETIT
 *
 */
public class Bucket {
    
    private String bucketId;
    private List<Stacktrace> allStacktraces; 
    	
    public Bucket(final String bucketId){
        this.bucketId=bucketId;
        this.allStacktraces = new ArrayList<Stacktrace>();  
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(final String bucketId) {
        this.bucketId = bucketId;
    }
    
   public void addStacktrace(final Stacktrace crash) {
	   this.allStacktraces.add(crash);
   }

    public List<Stacktrace> getCrash() {
        return allStacktraces;
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
