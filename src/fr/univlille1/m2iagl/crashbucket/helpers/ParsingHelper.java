package fr.univlille1.m2iagl.crashbucket.helpers;

import java.io.File;

public class ParsingHelper {
        public static void listFilesForFolder(final File folder) {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } 
                else {
                    //TODO
                    System.out.println(fileEntry.getName());
                }
            }
        }   
}
