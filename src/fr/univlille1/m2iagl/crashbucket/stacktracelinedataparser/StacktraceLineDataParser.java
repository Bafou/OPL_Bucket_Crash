package fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser;

public abstract class StacktraceLineDataParser {
    
    private String data;
    
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    public abstract double getScore();
}
