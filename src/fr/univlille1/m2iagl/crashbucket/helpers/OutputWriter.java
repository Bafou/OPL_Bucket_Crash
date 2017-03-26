package fr.univlille1.m2iagl.crashbucket.helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The writter which will write the result in the dedicated file
 * 
 * @author Antoine PETIT
 *
 */
public class OutputWriter {
    
	/**
	 * Write the result in the file located in the filePath
	 * @param stackTrace the id of the stacktrace
	 * @param bucketId the id of the bucket
	 * @param fileName the file name
	 * @param filePath the file path
	 */
    public static void generateOutputResultFile(final String stackTrace, final String bucketId, final String fileName, final String filePath){
        try {

       final String content = stackTrace+"  -> " + bucketId+"\n";

        final File file = new File(filePath+File.separator+fileName+".txt");

        if (!file.exists()) {
				file.createNewFile();
        }

        final FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(content);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}


