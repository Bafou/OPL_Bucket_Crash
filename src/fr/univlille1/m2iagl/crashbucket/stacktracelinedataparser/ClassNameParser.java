package fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser;

public class ClassNameParser extends StacktraceLineDataParser{
    
    public ClassNameParser(String className) {
        super();
        setData(className);
    }
}