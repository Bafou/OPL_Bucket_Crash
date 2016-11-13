package fr.univlille1.m2iagl.crashbucket.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilesLoader {
        
    public static List<String> listTrainingFolder(final File folder) {
        List<String> res = new ArrayList();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                res.addAll(listTrainingFolder(fileEntry));
            } 
            else {
                res.add(fileEntry.getParentFile().getParentFile().getName());
            }
        }
        Collections.sort(res);
        return res;
    }
        
    public static List<String> listTestingFolder(final File folder) {
        List<String> res = new ArrayList();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                res.addAll(listTestingFolder(fileEntry));
            } 
            else {
                res.add(fileEntry.getName().substring(0,fileEntry.getName().indexOf(".txt")));
            }
        }
        Collections.sort(res);
        return res;
    }
    
    	public static void loadAllFiles(File folder, List<File> fileList) {
		for (final File fileEntry : folder.listFiles())
			if (fileEntry.isDirectory())
				loadAllFiles(fileEntry, fileList);
			else if (!fileList.contains(fileEntry))
				fileList.add(fileEntry);
	}
}