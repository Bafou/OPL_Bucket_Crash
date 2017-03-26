package fr.univlille1.m2iagl.crashbucket.structure;

import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.StacktraceLineDataParser;
import java.util.*;

public class StacktraceLine {

    private int stackTraceLineNumber;
    private boolean firstElement = true;
    private String offsetValue;
    private String methodName;
    
    private List<StacktraceLineDataParser> stacktraceLineData = new ArrayList<>();
    private Integer lineNumber;
        
    private Map<String,String> methodArguments = new HashMap<>();
    private String className;
    
    private Map<String,String> environmentVariable = new HashMap<>();
    public StacktraceLine() {
    }
    
    public boolean isFirstElement() {
        return firstElement;
    }

    public void setFirstElement(boolean firstElement) {
        this.firstElement = firstElement;
    }

    public int getStackTraceNumber() {
        return stackTraceLineNumber;
    }
    
    public void setStackTraceNumber(int stackTraceNumber){
        this.stackTraceLineNumber = stackTraceNumber;
    }
    
    public String getOffsetValue() {
        return offsetValue;
    }

    public void setOffsetValue(String offsetValue) {
        this.offsetValue = offsetValue;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Map<String, String> getMethodArguments() {
        return methodArguments;
    }

    public void setMethodArguments(Map<String, String> methodArguments) {
        this.methodArguments = methodArguments;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<StacktraceLineDataParser> getStacktraceLineData() {
        return stacktraceLineData;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void addStacktraceLineData(StacktraceLineDataParser stacktraceLineData) {
        this.stacktraceLineData.add(stacktraceLineData);
    }
    
    public void addAllStacktraceLineData(Collection<? extends StacktraceLineDataParser> stacktracesLineData) {
        this.stacktraceLineData.addAll(stacktracesLineData);
    }
    
    public Map<String,String> getEnvironmentVariable() {
        return environmentVariable;
    }

    public void setEnvironmentVariable(Map<String,String> environmentVariable) {
        this.environmentVariable = environmentVariable;
    }
}