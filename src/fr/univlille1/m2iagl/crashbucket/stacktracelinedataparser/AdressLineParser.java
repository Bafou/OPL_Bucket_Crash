package fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser;

/**
 * A StacktraceLineDataParser representing an adress
 * @author Antoine PETIT
 *
 */
public class AdressLineParser extends StacktraceLineDataParser {

	
	public AdressLineParser(String adressLine) {
        super();
        setData(adressLine);
    }
	
	@Override
	public double getScore() {
		return 8.0;
	}

}
