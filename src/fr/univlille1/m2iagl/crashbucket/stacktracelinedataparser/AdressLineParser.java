package fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser;

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
