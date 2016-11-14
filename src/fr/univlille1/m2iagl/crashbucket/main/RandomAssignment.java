package fr.univlille1.m2iagl.crashbucket.main;

import fr.univlille1.m2iagl.crashbucket.constant.Constants;
import static fr.univlille1.m2iagl.crashbucket.helpers.FilesLoader.listTestingFolder;
import static fr.univlille1.m2iagl.crashbucket.helpers.FilesLoader.listTrainingFolder;
import static fr.univlille1.m2iagl.crashbucket.helpers.OutputWriter.generateOutputResultFile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAssignment {
    
    public static List<File> trainingRessources = new ArrayList<>();
    public static List<File> testingRessources = new ArrayList<>();
        
    public static void main(String[] args) {
        final long startTime = System.nanoTime();
        final File trainingFolder = new File(Constants.NAUTILUS_TRAINING_PATH);
        final File testingFolder = new File(Constants.NAUTILUS_TESTING_PATH);
        final String outputFileName = Constants.RANDOM_ASSIGNEMENT_OUTPUT_FILE;
        final String nautilusFolderPath = Constants.NAUTILUS_PATH;
        
        List<String> bucketChoice = listTrainingFolder(trainingFolder);
        List<String> stackTraces = listTestingFolder(testingFolder);
        
        Random randomGenerator = new Random();
        
        File file = new File(nautilusFolderPath+File.separator+outputFileName+".txt");
        if (file.exists()) {
            file.delete();
        }
        
        System.out.println("------------------------------------------------------------------------"+"\n");
        System.out.println("Random Stacktrace Analysis started...");
        for(String stackTrace : stackTraces){
            int index = randomGenerator.nextInt(bucketChoice.size());
            String item = bucketChoice.get(index);
            generateOutputResultFile(stackTrace,item,outputFileName,nautilusFolderPath);
        }
        long endTime = System.nanoTime();
        final long duration = endTime - startTime;
        final double seconds = ((double)duration / 1000000000);
        System.out.println("Random Analysis successfully done in "+seconds+" Seconds. See result in "+nautilusFolderPath+"\\"+outputFileName+".txt"+"\n");
        System.out.println("------------------------------------------------------------------------");
    }  
}
