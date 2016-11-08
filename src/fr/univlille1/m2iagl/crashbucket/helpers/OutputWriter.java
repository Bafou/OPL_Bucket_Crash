package fr.univlille1.m2iagl.crashbucket.helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputWriter {
    
    public static void generateOutputResultFile(String stackTrace, String bucketId, String fileName, String filePath){
        try {

        String content = stackTrace+"  -> " + bucketId+"\n";

        File file = new File(filePath+File.separator+fileName+".txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


