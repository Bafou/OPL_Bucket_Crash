package fr.univlille1.m2iagl.crashbucket.structure;

import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.StacktraceData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StacktraceLine {

    private int stackTraceLineNumber;
    private boolean firstElement = true;
    private String offsetValue;
    private String methodName;
    
    private List<StacktraceData> keywords = new ArrayList<>();
    private Integer lineNumber;
        
    private Map<String,String> methodArguments = new HashMap<>();
    private String className;
    
    private Map<String,String> environmentVariable = new HashMap<>();
    
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

    public StacktraceLine() {
    }

    public List<StacktraceData> getKeywords() {
        return keywords;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void addKeyword(StacktraceData keyword) {
        keywords.add(keyword);
    }
    
    public Map<String, String> getEnvironmentVariable() {
        return environmentVariable;
    }

    public void setEnvironmentVariable(Map<String, String> environmentVariable) {
        this.environmentVariable = environmentVariable;
    }
}