package fr.univlille1.m2iagl.crashbucket.base;

import java.util.ArrayList;

public class StacktraceElement extends ArrayList<String>{
    
    private String id;
    private String fileName = null;
    private String functionName = null;
    private String libraryName = null;
    
    private Stacktrace stacktrace;
        
    public String getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public StacktraceElement() {
    	super();
    }
    
    public StacktraceElement(String id, String fileName, String functionName, String libraryName) {
    	this.id = id;
        this.fileName = fileName;
    	this.functionName = functionName;
    	this.libraryName = libraryName;
    }

    public StacktraceElement(Stacktrace stacktrace) {
        super();
        this.stacktrace = stacktrace;
    }
    
    public Stacktrace getStackTrace(){
        return this.stacktrace;
    }
    
    public Crash getBucket(){
    	return this.getStackTrace().getBucket();
    }
    
    public Bucket getMainBucket(){
    	return this.getBucket().getMainBucket();
    }
}
