package fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser;

import java.util.ArrayList;
import java.util.List;

/**
 * A StacktraceLineDataParser is a data contain in a stacktrace line, representing a class, a method, etc
 * It help to give point depending of the type of data seen in the stacktrace line
 * @author Antoine PETIT
 *
 */
public abstract class StacktraceLineDataParser {
    
    private String data;
    private List<Integer> apparitionLineNumber;
    
    public StacktraceLineDataParser () {
    	apparitionLineNumber = new ArrayList<Integer>();
    }
    
    /**
     * Return the "value" of the data (ex : the class name)
     * @return the "value" of the data (ex : the class name)
     */
    public String getData() {
        return data;
    }

    /**
     * Set the "value" of the data
     * @param data the value
     */
    public void setData(final String data) {
        this.data = data;
    }
    
    /**
     * Give the score associated to the specific type
     * @return
     */
    public abstract double getScore();
    
    /**
     * Give the list of all line where the data as been seen
     * @return all the position of the data
     */
    public List<Integer> getApparitionLineNumber() {
    	return apparitionLineNumber;
    }
    
    /**
     * Add a line to the list of position of this specif data
     * @param line
     */
    public void addLineApparition (int line) {
    	apparitionLineNumber.add(line);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
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
		StacktraceLineDataParser other = (StacktraceLineDataParser) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}
    
}
