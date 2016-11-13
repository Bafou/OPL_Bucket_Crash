package fr.univlille1.m2iagl.crashbucket.analyzer;

import fr.univlille1.m2iagl.crashbucket.constant.Paths;
import static fr.univlille1.m2iagl.crashbucket.helpers.FilesLoader.loadAllFiles;
import static fr.univlille1.m2iagl.crashbucket.helpers.OutputWriter.generateOutputResultFile;
import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.Class;
import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.Library;
import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.Method;
import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.StacktraceData;
import fr.univlille1.m2iagl.crashbucket.structure.StacktraceLine;
import fr.univlille1.m2iagl.crashbucket.structure.Crash;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class StacktraceAnalyzer {
    public static Map<String, String> assignementResult = new HashMap<>();

    private BufferedReader bufferedReader;
    private int lineBeginingFrom;
    private final int lineAt;
    private String stackTraceLineNumber;
        
    
    public List<File> trainingRessources = new ArrayList<>();
    public List<File> testingRessources = new ArrayList<>();
    
    public Map<File,String> trainingRessourcesContent = new HashMap<>();
    public Map<File,String> testingRessourcesContent = new HashMap<>();

    public Map<String,Crash> trainingRessourcesStacktrace = new HashMap<>();
    public Map<String,Crash> testingRessourcesStacktrace = new HashMap<>();

    public void assignStacktraceToBucket(File testingFolder, File trainingFolder) {
        loadAllFiles(trainingFolder, trainingRessources);
	loadAllFiles(testingFolder, testingRessources);

	loadFileContent(testingRessources, testingRessourcesContent);
	loadFileContent(trainingRessources, trainingRessourcesContent);

	getStacktraceContent(testingRessourcesStacktrace, testingRessourcesContent);
	getStacktraceContent(trainingRessourcesStacktrace, trainingRessourcesContent);
        
        for (File testingFile : testingRessourcesContent.keySet()) {
            BucketDecider testingFileAnalyzeThread = 
                    new BucketDecider(trainingRessourcesStacktrace,testingRessourcesStacktrace, trainingRessourcesContent, testingRessourcesContent, testingFile);
            testingFileAnalyzeThread.InitiateAssignment();
        }
        generateOutputFile();
    }
        
    public void generateOutputFile() {
        SortedSet<String> sortedList = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return Integer.valueOf(a).compareTo(Integer.valueOf(b));}});
        sortedList.addAll(assignementResult.keySet());
        for (String testingFileName : sortedList)
            generateOutputResultFile(testingFileName,assignementResult.get(testingFileName),"AssignementResultOutput",Paths.NAUTILUS);
	}

	public void loadFileContent(List<File> fileList, Map<File,String> fileContent) {
		FileReader fileReader = null;
		for (File file : fileList) {
			try {
				fileReader = new FileReader(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			BufferedReader bfReader = new BufferedReader(fileReader);
			try {
				String line = null;
				StringBuilder sb = new StringBuilder("");
				while ((line = bfReader.readLine()) != null)
					sb.append(line).append("\n");
				fileContent.put(file, sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					bfReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void getStacktraceContent(Map<String,Crash> StacktraceContent, Map<File,String> fileContent) {
		String stacktraceData = null;
		for (File file : fileContent.keySet()) {
			stacktraceData = fileContent.get(file);
			Crash crashReport = new Crash();
			if (!StacktraceContent.containsKey(stacktraceData))
				StacktraceContent.put(stacktraceData, crashReport);
			else
				return;
                    try (Scanner scanner = new Scanner(stacktraceData)) {
                        String line = null;
                        StacktraceLine stacktraceLine = new StacktraceLine();
                        while (scanner.hasNext()) {
                            line = scanner.nextLine();
                            if (line.startsWith("#")) {
                                StacktraceContent.get(stacktraceData).addCrashLines(stacktraceLine);
                                stacktraceLine = new StacktraceLine();
                                Integer index = line.indexOf(" ");
                                Integer crashLineNumber = Integer.parseInt(line.substring(1, index));
                                stacktraceLine.setLineNumber(crashLineNumber);
                            }
                            getStacktraceLineData(line, stacktraceLine);
                        }
                        StacktraceContent.get(stacktraceData).addCrashLines(stacktraceLine);
                    }
		}

	}

	public void getStacktraceLineData(String line, StacktraceLine stacktraceLine) {
            try (Scanner scanner = new Scanner(line)) {
                String word = null;
                StacktraceData keyword;
                while (scanner.hasNext()) {
                    String keywordValue = scanner.next();
                    switch (keywordValue) {
                        case "in":
                            word = scanner.next();
                            if (word != null) {
                                keyword = new Method(word);
                                stacktraceLine.addKeyword(keyword);
                            }
                            break;
                        case "at":
                            word = scanner.next();
                            if (word != null) {
                                keyword = new Class(word);
                                stacktraceLine.addKeyword(keyword);
                            }
                            break;
                        case "from":
                            word = scanner.next();
                            if (word != null) {
                                keyword = new Library(word);
                                stacktraceLine.addKeyword(keyword);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
	}

        //888
        
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
					String lineLeft = null;
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
