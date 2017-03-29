package fr.univlille1.m2iagl.crashbucket.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class load all the file with the specific hierarchie of the stacktrace
 * and the bucket
 * 
 * @author Antoine PETIT
 *
 */
public class FilesLoader {

	/**
	 * Give all the name of the buckets
	 * 
	 * @param folder
	 *            the folder that contain existing bucket
	 * @return the list of the names of the buckets
	 */
	public static List<String> listTrainingFolder(final File folder) {
		List<String> res = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				res.addAll(listTrainingFolder(fileEntry));
			} else {
				res.add(fileEntry.getParentFile().getParentFile().getName());
			}
		}
		Collections.sort(res);
		return res;
	}

	/**
	 * Give all the name of the stacktrace
	 * 
	 * @param folder
	 *            the folder that contain the stacktrace that will be
	 *            categorised
	 * @return the list of the names of the stacktraces
	 */
	public static List<String> listTestingFolder(final File folder) {
		List<String> res = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				res.addAll(listTestingFolder(fileEntry));
			} else {
				res.add(fileEntry.getName().substring(0, fileEntry.getName().indexOf(".txt")));
			}
		}
		Collections.sort(res);
		return res;
	}

	/**
	 * Load all files and put it in the file list
	 * @param folder the folder we are reading
	 * @param fileList the fileList that will be filled
	 */
	public static void loadAllFiles(final File folder, final List<File> fileList) {
		for (final File fileEntry : folder.listFiles())
			if (fileEntry.isDirectory())
				loadAllFiles(fileEntry, fileList);
			else if (!fileList.contains(fileEntry))
				fileList.add(fileEntry);
	}
}