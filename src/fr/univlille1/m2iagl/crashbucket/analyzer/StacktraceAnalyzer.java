package fr.univlille1.m2iagl.crashbucket.analyzer;

import fr.univlille1.m2iagl.crashbucket.structure.CrashReport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StacktraceAnalyzer {
    public static Map<String, String> assignementResult = new HashMap<>();

    private BufferedReader bufferedReader;
    private int lineBeginingFrom;
    private int lineAt;
    private String stackTraceLineNumber;
        
    
    public List<File> trainingRessources = new ArrayList<>();
    public List<File> testingRessources = new ArrayList<>();
    
    public Map<File,String> trainingRessourcesContent = new HashMap<>();
    public Map<File,String> testingRessourcesContent = new HashMap<>();

    public Map<String,CrashReport> trainingRessourcesStacktrace = new HashMap<>();
    public Map<String,CrashReport> testingRessourcesStacktrace = new HashMap<>();

	public StacktraceAnalyzer(final File Crash) throws FileNotFoundException {

		final FileReader fileReader = new FileReader(Crash);
		bufferedReader = new BufferedReader(fileReader);
		lineBeginingFrom = -1;
		lineAt = -1;

		final String[] parts = Crash.getAbsolutePath().split("/");

		System.out.println(Crash.getAbsolutePath());

		stackTraceLineNumber = parts[parts.length - 1].split("\\.")[0];
	}

	public StacktraceAnalyzer() {
		this.lineBeginingFrom = -1;
		this.lineAt = -1;
	}

	public String getstackTraceLineNumber() {
		return stackTraceLineNumber;
	}

	public int getStackTraceLineNumber(final String stackTraceLine) {
		int startFrom = stackTraceLine.indexOf('#');
		if (startFrom != -1) {
			int endAt = stackTraceLine.indexOf(' ');
			final String stringNumCouche = stackTraceLine.substring(startFrom + 1, endAt);
			return Integer.parseInt(stringNumCouche);
		}
		return -1;
	}

	private int getStacktraceLine(final String stackTraceLine) {
		String stackTraceLinePosition = "";
		for (int i = 0; i < stackTraceLine.length(); i++) {
			char c = stackTraceLine.charAt(i);
			if (c == ' ' || c == '\n' || c == '.') {
				return Integer.parseInt(stackTraceLinePosition);
			}
			stackTraceLinePosition = stackTraceLinePosition + stackTraceLine.charAt(i);
		}
		return Integer.parseInt(stackTraceLinePosition);
	}

	public String getLibraryName(final String stackTraceLine) {
		int position;
		if ((position = stackTraceLine.indexOf("from")) != -1) {
			final String libraryName = stackTraceLine.substring(position + 4);
			int endAt = libraryName.indexOf("so.");
			if (endAt != -1) {
				if (libraryName.contains("so.")) {
					String lineLeft = "";
					lineLeft = libraryName.substring(endAt + 3);
					lineBeginingFrom = getStacktraceLine(lineLeft);
				} else {
					lineBeginingFrom = -1;
				}
				return libraryName.substring(0, endAt + 2);
			} else {
				return null;
			}
		}
		return null;
	}

	public String getMethodeName(final String stackTraceLine) {
		int position;
		if ((position = stackTraceLine.indexOf("in")) != -1) {
			final String methodName = stackTraceLine.substring(position + 2);
			int endAt = methodName.indexOf("(");
			if (endAt != -1) {
				return methodName.substring(0, endAt - 1);
				// peut retourner "??"
			}
		}
		return null;
	}

	public Map<String, String> getMethodArgument(final String stackTraceLine) {
		final Map<String, String> arguments = new HashMap<>();
		int position, endAt;

		if (((position = stackTraceLine.indexOf("(")) != -1) && (endAt = stackTraceLine.indexOf(")")) != -1) {
			final String allMethodArguments = stackTraceLine.substring(position, endAt);
			extractArguments(arguments, allMethodArguments);
		}

		return arguments;
	}

	private void extractArguments(final Map<String, String> arguments, final String allMethodArguments) {
		final String[] tabAllMethodArguments = allMethodArguments.split(",");

		for (final String couple : tabAllMethodArguments) {
			final String[] couplePart = couple.split("=");
			arguments.put(couplePart[0].trim(), couplePart[1].trim());
		}
	}

	public boolean isBeginningLine(final String stackTraceLine) {
		int startFrom = stackTraceLine.indexOf('#');
		return startFrom != -1 && startFrom == 0;
	}
}
