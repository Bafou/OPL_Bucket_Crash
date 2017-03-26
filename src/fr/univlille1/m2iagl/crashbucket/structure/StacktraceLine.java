package fr.univlille1.m2iagl.crashbucket.structure;

import fr.univlille1.m2iagl.crashbucket.stacktracelinedataparser.StacktraceLineDataParser;
import java.util.*;

/**
 * A StacktraceLine represent a single line of a Stacktrace
 * 
 * Contain
 * 
 * @author Antoine PETIT
 *
 */
public class StacktraceLine {

	private List<StacktraceLineDataParser> stacktraceLineData = new ArrayList<>();
	private Integer lineNumber;

	public StacktraceLine() {
	}

	public List<StacktraceLineDataParser> getStacktraceLineData() {
		return stacktraceLineData;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(final Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public void addStacktraceLineData(final StacktraceLineDataParser stacktraceLineData) {
		this.stacktraceLineData.add(stacktraceLineData);
	}

	public void addAllStacktraceLineData(final Collection<? extends StacktraceLineDataParser> stacktracesLineData) {
		this.stacktraceLineData.addAll(stacktracesLineData);
	}

}