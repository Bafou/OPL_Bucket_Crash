package fr.univlille1.m2iagl.crashbucket.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class StacktraceAnalyzer {
    
    private BufferedReader bufferedReader;
    private int lineBeginingFrom;
    private int lineAt;
    private String stackTraceLineNumber;
    
    public StacktraceAnalyzer(File Crash) throws FileNotFoundException {
		
		FileReader fileReader = new FileReader(Crash);
		bufferedReader = new BufferedReader(fileReader);
		lineBeginingFrom=-1;
		lineAt=-1;
		
		String[] parts = Crash.getAbsolutePath().split("/");
		
                //Revoir celle là 
                System.out.println(Crash.getAbsolutePath());
		
		stackTraceLineNumber = parts[parts.length-1].split("\\.")[0];
	}
    
    public StacktraceAnalyzer(){
        this.lineBeginingFrom=-1;
        this.lineAt=-1;
    }
    
    public String getstackTraceLineNumber(){
        return stackTraceLineNumber;
    }
    
    public int getStackTraceLineNumber(String stackTraceLine) {
        int startFrom = stackTraceLine.indexOf('#');
        if (startFrom != -1) {
            int endAt = stackTraceLine.indexOf(' ');
            String stringNumCouche = stackTraceLine.substring(startFrom + 1, endAt);
            return Integer.parseInt(stringNumCouche);
        }
        return -1;
    }
        
    private int getStacktraceLine(String stackTraceLine) {
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
    
    public String getLibraryName(String stackTraceLine) {
		int position;
		if ((position = stackTraceLine.indexOf("from")) != -1) {
			String libraryName = stackTraceLine.substring(position + 4);
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
    
    public String getMethodeName(String stackTraceLine){
        int position;
        if ((position = stackTraceLine.indexOf("in")) != -1)
        {
            String methodName = stackTraceLine.substring(position +2);
            int endAt = methodName.indexOf("(");
            
            
    }
        return null;
}
}
