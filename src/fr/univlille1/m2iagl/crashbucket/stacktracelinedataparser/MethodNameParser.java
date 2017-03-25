package fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser;

public class MethodNameParser extends StacktraceLineDataParser {
    
    public MethodNameParser(String methodName) {
        super();
        setData(methodName);
    }

	@Override
	public double getScore() {
		return 1.0;
	}
}
