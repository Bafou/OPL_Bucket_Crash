package fr.univlille1.m2iagl.crashbucket.analyzer;

import static fr.univlille1.m2iagl.crashbucket.helpers.FilesLoader.loadAllFiles;
import static fr.univlille1.m2iagl.crashbucket.helpers.OutputWriter.generateOutputResultFile;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.univlille1.m2iagl.crashbucket.constant.Constants;
import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.AdressLineParser;
import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.ClassNameParser;
import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.LibraryNameParser;
import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.MethodNameParser;
import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.StacktraceLineDataParser;
import fr.univlille1.m2iagl.crashbucket.structure.Bucket;
import fr.univlille1.m2iagl.crashbucket.structure.Crash;
import fr.univlille1.m2iagl.crashbucket.structure.StacktraceLine;

/**
 * The StacktraceAnalyzer is the core class, calling different element of the
 * Bucket and Crash
 * 
 * @author Antoine PETIT
 *
 */
public class StacktraceAnalyzer {

	public static Map<String, String> assignementResult = new HashMap<>();

	private final List<File> trainingRessources = new ArrayList<>();
	private final List<File> testingRessources = new ArrayList<>();

	private final Map<File, String> trainingRessourcesContent = new HashMap<>();
	private final Map<File, String> testingRessourcesContent = new HashMap<>();

	private final Map<String, Bucket> trainingRessourcesStacktrace = new HashMap<>();
	private final Map<String, Crash> testingRessourcesStacktrace = new HashMap<>();

	public StacktraceAnalyzer() {
		super();
	}

	/**
	 * Assign the stacktrace from testingFolder in the bucket contain in the trainingFolder
	 * Put the result in the file named outputFileName
	 * 
	 * @param testingFolder the folder that contains the stacktrace that should be put in the bucket
	 * @param trainingFolder the folder that contains the excictent buckets
	 * @param outputFileName the name of the file where the result will be stored
	 */
	public void assignStacktraceToBucket(final File testingFolder, final File trainingFolder,
			final String outputFileName) {
		loadAllFiles(trainingFolder, trainingRessources);
		loadAllFiles(testingFolder, testingRessources);

		loadFileContent(testingRessources, testingRessourcesContent);
		loadFileContent(trainingRessources, trainingRessourcesContent);

		getStacktraceContentTesting(testingRessourcesStacktrace, testingRessourcesContent);
		getStacktraceContentTraining(trainingRessourcesStacktrace, trainingRessourcesContent);

		final BucketDecider bucketDecider = new BucketDecider(trainingRessourcesStacktrace, testingRessourcesStacktrace);
		bucketDecider.InitiateAssignment();
		generateOutputFile(outputFileName);
	}

	/**
	 * Generate the outputfile with the value found in the assignementResult
	 * @param outputFileName the name of the outputFile
	 */
	public void generateOutputFile(final String outputFileName) {
		final SortedSet<String> sortedList = new TreeSet<>(new Comparator<String>() {
			@Override
			public int compare(final String a, final String b) {
				return Integer.valueOf(a).compareTo(Integer.valueOf(b));
			}
		});
		sortedList.addAll(assignementResult.keySet());
		for (final String testingFileName : sortedList)
			generateOutputResultFile(testingFileName, assignementResult.get(testingFileName), outputFileName,
					Constants.HOME_PATH);
	}

	/**
	 * Load the file content as a String for all the fileList and the put it in the fileContent Map
	 * @param fileList the file that need to be read
	 * @param fileContent a map which will contain the file and its associated content
	 */
	public void loadFileContent(final List<File> fileList, final Map<File, String> fileContent) {
		FileReader fileReader = null;
		for (final File file : fileList) {
			try {
				fileReader = new FileReader(file);
			} catch (final FileNotFoundException ex) {
				System.out.println("The file with the specified pathname does not exist");
			}
			final BufferedReader bfReader = new BufferedReader(fileReader);
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
				} catch (final IOException ex) {
					System.out.println("Failed or interrupted I/O operations.");
				}
			}
		}
	}

	/**
	 * Read the content of the testing stacktrace in order to associate a stacktrace id to the different elements contained in it
	 * @param stacktraceContent the map which will contain the result, associate the stacktrace id to its crash content (object)
	 * @param fileContent the map that contain the file and its associated content
	 */
	public void getStacktraceContentTesting(final Map<String, Crash> stacktraceContent,
			final Map<File, String> fileContent) {
		for (final File file : fileContent.keySet()) {
			String stacktraceData = fileContent.get(file);
			final Crash crashReport = new Crash();
			final String fileName = file.getName().substring(0, file.getName().length() - 4);
			if (!stacktraceContent.containsKey(fileName))
				stacktraceContent.put(fileName, crashReport);
			else
				return;
			try (final Scanner scanner = new Scanner(stacktraceData)) {
				String line = null;
				StacktraceLine stacktraceLine = new StacktraceLine();
				while (scanner.hasNext()) {
					line = scanner.nextLine();
					if (line.startsWith("#")) {
						stacktraceContent.get(fileName).addCrashLines(stacktraceLine);
						stacktraceLine = new StacktraceLine();
						Integer index = line.indexOf(" ");
						Integer crashLineNumber = Integer.parseInt(line.substring(1, index));
						stacktraceLine.setLineNumber(crashLineNumber);
						line = line.substring(index).trim();
					}
					getStacktraceLineData(line, stacktraceLine, crashReport);
				}
				stacktraceContent.get(fileName).addCrashLines(stacktraceLine);
			}
		}
	}

	/**
	 * Read the content of the training buckets in order to associate a bucket id to the different elements contained in it
	 * @param stackTraceContent the map which will contain the result, associate the bucket id to its content (object)
	 * @param fileContent the map that contain the file and its associated content
	 */
	public void getStacktraceContentTraining(final Map<String, Bucket> stackTraceContent,
			final Map<File, String> fileContent) {
		String stacktraceData = null;

		for (final File file : fileContent.keySet()) {
			final Crash crashReport = new Crash();
			stacktraceData = fileContent.get(file);
			Bucket bucket = stackTraceContent.get(file);
			if (bucket == null) {
				bucket = new Bucket(file.getParentFile().getParentFile().getName());
				stackTraceContent.put(file.getParentFile().getParentFile().getName(), bucket);
			}

			try (final Scanner scanner = new Scanner(stacktraceData)) {
				String line = null;
				StacktraceLine stacktraceLine = new StacktraceLine();
				while (scanner.hasNext()) {
					line = scanner.nextLine();
					if (line.startsWith("#")) {
						stacktraceLine = new StacktraceLine();
						Integer index = line.indexOf(" ");
						Integer crashLineNumber = Integer.parseInt(line.substring(1, index));
						stacktraceLine.setLineNumber(crashLineNumber);
						line = line.substring(index).trim();
					}
					getStacktraceLineData(line, stacktraceLine, crashReport);
				}
			}
			bucket.addCrash(crashReport);
		}
	}

	/**
	 * Use the content of a line to extract all the valuable elements, needed to compare, in it
	 * @param line the line that will be read
	 * @param stacktraceLine the object which will contain the valuable elements
	 * @param crashReport the crash where the stacktrace will be put
	 */
	public void getStacktraceLineData(final String line, final StacktraceLine stacktraceLine, final Crash crashReport) {
		List<StacktraceLineDataParser> allData = new ArrayList<StacktraceLineDataParser>();
		allData.addAll(getAllAddresses(line));
		allData.addAll(getAllClasses(line));
		allData.addAll(getAllLibraries(line));
		allData.addAll(getAllMethods(line));
		stacktraceLine.addAllStacktraceLineData(allData);
		crashReport.addCrashLines(stacktraceLine);
	}

	/**
	 * Return all the adresses contain in the stacktrace line
	 * @param line the stacktrace line that need to be read
	 * @return the list of all the adresses
	 */
	private List<StacktraceLineDataParser> getAllAddresses(final String line) {
		final List<StacktraceLineDataParser> allAdresses = new ArrayList<StacktraceLineDataParser>();
		final Matcher m = Pattern.compile("^0x[0-9a-fA-F]+ ").matcher(line);
		while (m.find()) {
			allAdresses.add(new AdressLineParser(m.group()));
		}
		return allAdresses;
	}

	/**
	 * Return all the classes (marked by a "at") contain in the stacktrace line
	 * @param line the stacktrace line that need to be read
	 * @return the list of all the adresses
	 */
	private List<StacktraceLineDataParser> getAllClasses(final String line) {
		final List<StacktraceLineDataParser> allClasses = new ArrayList<StacktraceLineDataParser>();
		final Matcher m = Pattern.compile(" at /*.*[^ ]").matcher(line);
		while (m.find()) {
			String match = m.group();
			match = match.replaceAll("at", "").replaceAll(":[0-9]+", "").replaceAll("/.*/", "").replaceAll("\\..*", "")
					.trim();
			allClasses.add(new ClassNameParser(match));
		}
		return allClasses;
	}

	/**
	 * Return all the libraries (marked by a "from") contain in the stacktrace line
	 * @param line the stacktrace line that need to be read
	 * @return the list of all the libraries
	 */
	private List<StacktraceLineDataParser> getAllLibraries(final String line) {
		List<StacktraceLineDataParser> allLibrary = new ArrayList<StacktraceLineDataParser>();
		Matcher m = Pattern.compile(" from /*.*[^ ]").matcher(line);
		while (m.find()) {
			String match = m.group();
			match = match.replaceAll("from", "").replaceAll(":[0-9]+", "").replaceAll("/.*/", "")
					.replaceAll("\\..*", "").trim();
			allLibrary.add(new LibraryNameParser(match));
		}
		return allLibrary;
	}

	/**
	 * Return all the methods contain in the stacktrace line
	 * @param line the stacktrace line that need to be read
	 * @return the list of all the methods
	 */
	private List<StacktraceLineDataParser> getAllMethods(final String line) {
		final List<StacktraceLineDataParser> allMethod = new ArrayList<StacktraceLineDataParser>();
		final Matcher m = Pattern.compile(" in .* \\(").matcher(line);
		final Matcher m2 = Pattern.compile("^[^0-9].* \\(").matcher(line);
		while (m.find()) {
			String match = m.group();
			match = match.replaceAll(" in ", "").replaceAll("\\(", "").replaceAll("<IA__", "").replaceAll(">$", "")
					.trim();
			allMethod.add(new MethodNameParser(match));
		}
		while (m2.find()) {
			String match = m2.group();
			match = match.replaceAll(" in ", "").replaceAll("\\(", "").replaceAll("<IA__", "").replaceAll(">$", "")
					.trim();
			allMethod.add(new MethodNameParser(match));
		}
		return allMethod;
	}

}
