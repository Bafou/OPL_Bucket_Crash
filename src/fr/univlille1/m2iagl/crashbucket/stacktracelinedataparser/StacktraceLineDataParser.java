package fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser;

import java.util.ArrayList;
import java.util.List;

public abstract class StacktraceLineDataParser {
    
    private String data;
    private List<Integer> apparitionLineNumber;
    
    public StacktraceLineDataParser () {
    	apparitionLineNumber = new ArrayList<Integer>();
    }
    
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    public abstract double getScore();
    
    public List<Integer> getApparitionLineNumber() {
    	return apparitionLineNumber;
    }
    
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
