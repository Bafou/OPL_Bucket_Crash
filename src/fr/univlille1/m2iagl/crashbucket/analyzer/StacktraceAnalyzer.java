package fr.univlille1.m2iagl.crashbucket.analyzer;

import fr.univlille1.m2iagl.crashbucket.constant.Constants;
import static fr.univlille1.m2iagl.crashbucket.helpers.FilesLoader.loadAllFiles;
import static fr.univlille1.m2iagl.crashbucket.helpers.OutputWriter.generateOutputResultFile;

import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.AdressLineParser;
import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.ClassLineParser;
import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.ClassNameParser;
import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.LibraryNameParser;
import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.MethodNameParser;
import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.StacktraceLineDataParser;
import fr.univlille1.m2iagl.crashbucket.structure.Crash;
import fr.univlille1.m2iagl.crashbucket.structure.StacktraceLine;
import java.io.*;
import java.util.*;

public class StacktraceAnalyzer {
    
    public static Map<String, String> assignementResult = new HashMap<>();

    private BufferedReader bufferedReader;
    private int lineBeginingFrom;
    private final int lineAt;
    private String stackTraceLineNumber;
        
    
    private final List<File> trainingRessources = new ArrayList<>();
    private final List<File> testingRessources = new ArrayList<>();
    
    private final Map<File,String> trainingRessourcesContent = new HashMap<>();
    private final Map<File,String> testingRessourcesContent = new HashMap<>();

    private final Map<String,Crash> trainingRessourcesStacktrace = new HashMap<>();
    private final Map<String,Crash> testingRessourcesStacktrace = new HashMap<>();
    
    public StacktraceAnalyzer() {
        this.lineBeginingFrom = -1;
        this.lineAt = -1;
    }

    public void assignStacktraceToBucket(final File testingFolder, final File trainingFolder, final String outputFileName) {
        loadAllFiles(trainingFolder, trainingRessources);
	loadAllFiles(testingFolder, testingRessources);

	loadFileContent(testingRessources, testingRessourcesContent);
	loadFileContent(trainingRessources, trainingRessourcesContent);

	getStacktraceContent(testingRessourcesStacktrace, testingRessourcesContent);
	getStacktraceContent(trainingRessourcesStacktrace, trainingRessourcesContent);
        
        for (final File testingFile : testingRessourcesContent.keySet()) {
            BucketDecider bucketDecider = 
                    new BucketDecider(trainingRessourcesStacktrace,testingRessourcesStacktrace, trainingRessourcesContent, testingRessourcesContent, testingFile);
            bucketDecider.InitiateAssignment();
        }
        generateOutputFile(outputFileName);
    }
        
    public void generateOutputFile(final String outputFileName) {
        final SortedSet<String> sortedList = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return Integer.valueOf(a).compareTo(Integer.valueOf(b));}});
        sortedList.addAll(assignementResult.keySet());
        for (final String testingFileName : sortedList)
            generateOutputResultFile(testingFileName,assignementResult.get(testingFileName),outputFileName,Constants.NAUTILUS_PATH);
	}

	public void loadFileContent(final List<File> fileList,  final Map<File,String> fileContent) {
		FileReader fileReader = null;
		for (final File file : fileList) {
			try {
				fileReader = new FileReader(file);
			} catch (final FileNotFoundException ex) {
				System.out.println("The file with the specified pathname does not exist");
			}
			BufferedReader bfReader = new BufferedReader(fileReader);
			try {
				String line = null;
				final StringBuilder sb = new StringBuilder("");
				while ((line = bfReader.readLine()) != null)
					sb.append(line).append("\n");
				fileContent.put(file, sb.toString());
			} catch (final IOException ex) {
				System.out.println("Failed or interrupted I/O operations.");
			} finally {
				try {
					bfReader.close();
				} catch (IOException ex) {
                                    System.out.println("Failed or interrupted I/O operations.");
				}
			}
		}
	}

	public void getStacktraceContent(final Map<String,Crash> StacktraceContent, final Map<File,String> fileContent) {
		String stacktraceData = null;
		for (final File file : fileContent.keySet()) {
			stacktraceData = fileContent.get(file);
			final Crash crashReport = new Crash();
			if (!StacktraceContent.containsKey(stacktraceData))
				StacktraceContent.put(stacktraceData, crashReport);
			else
				return;
                    try (final Scanner scanner = new Scanner(stacktraceData)) {
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

	public void getStacktraceLineData(final String line, final StacktraceLine stacktraceLine) {
            try (Scanner scanner = new Scanner(line)) {
                String word = null;
                StacktraceLineDataParser data;
                boolean asSeenAdress = false;
                while (scanner.hasNext()) {
                    String keywordValue = scanner.next();
                    switch (keywordValue) {
                        case "in":
                            word = scanner.next();
                            if (word != null) {
                                data = new MethodNameParser(word);
                                stacktraceLine.addStacktraceLineData(data);
                            }
                            break;
                        case "from":
                            word = scanner.next();
                            if (word != null) {
                            data = new LibraryNameParser(word);
                            stacktraceLine.addStacktraceLineData(data);
                            }
                            break;
                        case "at":
                            word = scanner.next();
                            if (word != null) {
                            	String[] allPart = word.split("/");
                            	String lastPart = allPart[allPart.length-1].split(":")[0];
                                data = new ClassNameParser(lastPart);
                                stacktraceLine.addStacktraceLineData(data);
                            }
                            break;
                        case ":":
                            word = scanner.next();
                            if(word != null){
                                data = new ClassLineParser(word);
                                stacktraceLine.addStacktraceLineData(data);
                            }
                            break;
                        default:
                        	if (!asSeenAdress && keywordValue.startsWith("0x")) {
                        		data = new AdressLineParser(keywordValue);
                        		stacktraceLine.addStacktraceLineData(data);
                        	}
                            break;
                    }
                }
            }
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
