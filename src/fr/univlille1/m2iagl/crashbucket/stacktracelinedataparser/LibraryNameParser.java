package fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser;

public class LibraryNameParser extends StacktraceLineDataParser {
    
    public LibraryNameParser(String libraryName) {
        super();
        setData(libraryName);
    }

	@Override
	public double getScore() {
		return 10.0;
	}
}
