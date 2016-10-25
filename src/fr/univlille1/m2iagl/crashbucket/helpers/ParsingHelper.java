package fr.univlille1.m2iagl.crashbucket.helpers;

import java.io.File;

public class ParsingHelper {
        public static void listFilesForFolder(final File folder) {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } 
                else {
                    System.out.println(fileEntry.getParent());
                    System.out.println(fileEntry.getParentFile().getParentFile().getName());
                    System.out.println(fileEntry.getName());
                }
            }
        }   
}
