package fr.univlille1.m2iagl.crashbucket.structure;

import java.util.*;

import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.StacktraceLineDataParser;

/**
 * A stacktrace represent the log given when a crah occured
 * @author Antoine PETIT
 *
 */
public class Stacktrace {

	private Bucket bucket;
	private List<StacktraceLine> stacktraceLines;
	private List<StacktraceLineDataParser> allData;

	public Stacktrace() {
		super();
		stacktraceLines = new ArrayList<>();
		allData = new ArrayList<>();
	}

	public List<StacktraceLineDataParser> getAllData() {
		return allData;
	}

	public Bucket getBucket() {
		return this.bucket;
	}

	public List<StacktraceLine> getStacktraceLines() {
		return stacktraceLines;
	}

	public void addStacktracelines(final StacktraceLine stacktraceLine) {
		stacktraceLines.add(stacktraceLine);
		for (final StacktraceLineDataParser data : stacktraceLine.getStacktraceLineData()) {
			if (allData.contains(data)) {
				allData.get(allData.indexOf(data)).addLineApparition(stacktraceLine.getLineNumber());
			} else {
				data.addLineApparition(stacktraceLine.getLineNumber());
				allData.add(data);
			}
		}
	}

	public void addAllStacktracelines(final Collection<StacktraceLine> stacktraceLines) {
		for (final StacktraceLine stacktraceLine : stacktraceLines) {
			this.stacktraceLines.add(stacktraceLine);
			for (StacktraceLineDataParser data : stacktraceLine.getStacktraceLineData()) {
				if (allData.contains(data)) {
					allData.get(allData.indexOf(data)).addLineApparition(stacktraceLine.getLineNumber());
				} else {
					data.addLineApparition(stacktraceLine.getLineNumber());
					allData.add(data);
				}
			}
		}
	}
}