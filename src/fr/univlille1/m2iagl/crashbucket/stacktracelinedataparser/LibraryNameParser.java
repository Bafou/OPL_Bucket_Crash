package fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser;

/**
 *  A StacktraceLineDataParser representing a library
 * @author Antoine PETIT
 *
 */
public class LibraryNameParser extends StacktraceLineDataParser {
    
    public LibraryNameParser(String libraryName) {
        super();
        setData(libraryName);
    }

	@Override
	public double getScore() {
		return 11.0;
	}
}
