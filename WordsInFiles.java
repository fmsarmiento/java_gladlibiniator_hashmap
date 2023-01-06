
/**
 * Write a description of WordsInFiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
import java.io.*;

public class WordsInFiles {
    HashMap<String, ArrayList<String>> wordfiles;
    
    public WordsInFiles() {
        wordfiles = new HashMap<String, ArrayList<String>>();
    }
    
    private void addWordsFromFile(File f) {
        String fname = f.getName();
        System.out.println("Adding words from file "+fname);
        FileResource fr = new FileResource(f);
        
        for (String elt : fr.words()) {
            if(wordfiles.containsKey(elt)) {
                if (! wordfiles.get(elt).contains(fname)) {
                    wordfiles.get(elt).add(fname);
                }
            }
            else {
                    // Note: this creates a new temp every time. I made a mistake of initializing it
                    // before the for loop, so it keeps the current pointer. So if I clear/add, it
                    // clears and also adds to other arraylists that all the temps point to. This way,
                    // we make sure that temp is destroyed after the call. 
                    ArrayList<String> temp = new ArrayList<String>();;
                    temp.add(fname);
                    wordfiles.put(elt,temp);
                }
        }
        
    }
    
    private void buildWordFileMap() {
        wordfiles.clear();
        DirectoryResource dir = new DirectoryResource();
        for (File f : dir.selectedFiles()) {
            addWordsFromFile(f);
        }
    }
    
    private int maxNumber() {
    int max = 0;
    for (String elt : wordfiles.keySet()) {
        int lenset = wordfiles.get(elt).size();
        if (max < lenset) {
            max = lenset;
        }
    }
    return max;
    }
    
    private ArrayList<String> wordsInNumFiles(int number) {
        ArrayList<String> result = new ArrayList<String>();
        for (String elt : wordfiles.keySet()) {
            int lenset = wordfiles.get(elt).size();
            if (lenset == number) {
            result.add(elt);
            }
        }
        return result;
    }
    
    private void printFilesIn(String word) {
        ArrayList<String> result = wordfiles.get(word);
        for (String elt : result) {
            System.out.println(word+" occurs in "+elt);
        }
    }
    
    public void tester() {
        buildWordFileMap();
        System.out.println(maxNumber());
        for (String elts : wordfiles.keySet()) {
            System.out.println("Key: "+elts+"\t Value: "+wordfiles.get(elts));
        }
        System.out.println(wordsInNumFiles(4).size());
        printFilesIn("tree");
    }
}
