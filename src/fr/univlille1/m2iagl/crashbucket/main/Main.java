package fr.univlille1.m2iagl.crashbucket.main;

import static fr.univlille1.m2iagl.crashbucket.helpers.ParsingHelper.listFilesForFolder;
import static fr.univlille1.m2iagl.crashbucket.helpers.ParsingHelper.listTestingFolder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static fr.univlille1.m2iagl.crashbucket.helpers.ParsingHelper.listTrainingFolder;
import java.util.Random;

public class Main {

    public static final String NAUTILUS_TRAINING_PATH = "D:\\IAGL\\OPL\\nautilus\\nautilus-training";
    public static final String NAUTILUS_TESTING_PATH = "D:\\IAGL\\OPL\\nautilus\\nautilus-testing";
    
    public static List<File> trainingRessources = new ArrayList<File>();
    public static List<File> testingRessources = new ArrayList<File>();
        
    public static void main(String[] args) {
        final File trainingFolder = new File(NAUTILUS_TRAINING_PATH);
        final File testingFolder = new File(NAUTILUS_TESTING_PATH);
        
        List<String> bucketChoice = listTrainingFolder(trainingFolder);
        List<String> stackTraces = listTestingFolder(testingFolder);
        
        Random randomGenerator = new Random();
        
        for(String stackTrace : stackTraces){
            int index = randomGenerator.nextInt(bucketChoice.size());
            String item = bucketChoice.get(index);
            System.out.println(stackTrace+" -> " + item);
        }
        
        
        
        //TODO
    }
    
}
