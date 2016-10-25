package fr.univlille1.m2iagl.crashbucket.parser;

import java.util.regex.Pattern;

public class CrashParser {
    
    Pattern stackTraceLineNumberPattern = Pattern.compile("#([0-9]+) ");
    Pattern offsetValuePattern = Pattern.compile("0x([0-9a-z]*) ");
    Pattern methodNamePattern = Pattern.compile("");
    
    Pattern classNamePattern = Pattern.compile("");
    Pattern lineNumberPattern = Pattern.compile("");
    
    Pattern methodArgumentsPattern = Pattern.compile("");
    Pattern environmentVariablePattern = Pattern.compile("");
}
