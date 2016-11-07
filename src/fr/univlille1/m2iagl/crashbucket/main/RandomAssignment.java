package fr.univlille1.m2iagl.crashbucket.main;

import fr.univlille1.m2iagl.crashbucket.constant.Paths;
import static fr.univlille1.m2iagl.crashbucket.helpers.OutputWriter.generateOutputFile;
import static fr.univlille1.m2iagl.crashbucket.helpers.ParsingHelper.listTestingFolder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static fr.univlille1.m2iagl.crashbucket.helpers.ParsingHelper.listTrainingFolder;
import java.util.Random;

public class RandomAssignment {
    
    public static List<File> trainingRessources = new ArrayList<>();
    public static List<File> testingRessources = new ArrayList<>();
        
    public static void main(String[] args) {
        final File trainingFolder = new File(Paths.NAUTILUS_TRAINING);
        final File testingFolder = new File(Paths.NAUTILUS_TESTING);
        
        List<String> bucketChoice = listTrainingFolder(trainingFolder);
        List<String> stackTraces = listTestingFolder(testingFolder);
        
        Random randomGenerator = new Random();
        
        for(String stackTrace : stackTraces){
            int index = randomGenerator.nextInt(bucketChoice.size());
            String item = bucketChoice.get(index);
            generateOutputFile(stackTrace,item,"RandomAssignementOutput",Paths.NAUTILUS);
        }
    }
    
}
