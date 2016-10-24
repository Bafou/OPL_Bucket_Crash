package fr.univlille1.m2iagl.crashbucket.main;

import static fr.univlille1.m2iagl.crashbucket.helpers.ParsingHelper.listFilesForFolder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String NAUTILUS_TRAINING_PATH = "D:\\IAGL\\OPL\\nautilus\\nautilus-training";
    public static final String NAUTILUS_TESTING_PATH = "D:\\IAGL\\OPL\\nautilus\\nautilus-testing";
    
    public static List<File> trainingRessources = new ArrayList<File>();
    public static List<File> testingRessources = new ArrayList<File>();
        
    public static void main(String[] args) {
        final File folder = new File(NAUTILUS_TRAINING_PATH);
        listFilesForFolder(folder);
        
        //TODO
    }
    
}
