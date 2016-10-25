package fr.univlille1.m2iagl.crashbucket.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ParsingHelper {
        public static void listFilesForFolder(final File folder) {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } 
                else {
                    //System.out.println(fileEntry.getParent());
                    System.out.println(fileEntry.getParentFile().getParentFile().getName());
                    //System.out.println(fileEntry.getName());
                }
            }
        }   
        
        public static List<String> listTrainingFolder(final File folder) {
            List<String> res = new ArrayList();
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    res.addAll(listTrainingFolder(fileEntry));
                } 
                else {
                    //System.out.println(fileEntry.getParent());
                    res.add(fileEntry.getParentFile().getParentFile().getName());
                    //System.out.println(fileEntry.getName());
                }
            }
          return res;
        }
        
        public static List<String> listTestingFolder(final File folder) {
            List<String> res = new ArrayList();
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    //listTestingFolder(fileEntry);
                    res.addAll(listTestingFolder(fileEntry));
                } 
                else {
                    //System.out.println(fileEntry.getParent());
                    res.add(fileEntry.getName().substring(0,fileEntry.getName().indexOf(".txt")));
                    //System.out.println(fileEntry.getName());
                }
            }
          return res;
        }
}
