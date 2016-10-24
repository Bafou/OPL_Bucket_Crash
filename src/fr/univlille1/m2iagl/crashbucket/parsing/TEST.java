package fr.univlille1.m2iagl.crashbucket.parsing;

import java.util.*; 
import java.util.Date; 
//import java.util.StringTokenizer; 
import java.text.DateFormat; 
import java.text.SimpleDateFormat; 
//import java.text.DateFormat; 
//import java.lang.*; 
import java.io.*; 
import java.util.regex.Pattern; 
import java.util.regex.PatternSyntaxException; 
import java.util.regex.Matcher; 

public class TEST { 

/** 
* @param args 
*/ 
public static void main(String[] args) { 
    try 
    { 
        String path = args[0]; 
        String filtre = args[1]; 
        args[0] = "D:\\IAGL\\OPL\\test";
        args[1] = "(\\w*_\\d*)(.txt)";
        Pattern p = Pattern.compile(filtre); 
        String [] s = new File(path).list(); 
        List<String> listeFichiers = new ArrayList<String>(); 
        for (int i=0; i<s.length;i++) 
        { 
            Matcher m = p.matcher(s[i]); 
            if ( m.matches()) 
            { 
                System.out.println(m.group(1)); 
                listeFichiers.add(s[i]); 
            } 
        } 
    } 
    catch (PatternSyntaxException pse) { 
        pse.printStackTrace(); 
    } 
} 
} 