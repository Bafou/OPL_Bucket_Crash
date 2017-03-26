package fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser;

/**
 *  A StacktraceLineDataParser representing an className
 * @author Antoine PETIT
 *
 */
public class ClassNameParser extends StacktraceLineDataParser{
    
    public ClassNameParser(String className) {
        super();
        setData(className);
    }

	@Override
	public double getScore() {
		return 10.0;
	}
}
