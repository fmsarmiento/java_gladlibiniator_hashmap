import edu.duke.*;
import java.util.*;

public class GladLib {
    private HashMap<String, ArrayList<String>> myMap;
    private ArrayList<String> usedList;
    private ArrayList<String> wordconsiderations;
    private int usedCtr;
    private Random myRandom;
    private int totalwords;
    private int totalwordsconsidered;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
    
    public GladLib(){
        usedList = new ArrayList<String>();
        wordconsiderations = new ArrayList<String>();
        usedList.clear();
        usedCtr = 0;
        myMap = new HashMap<String, ArrayList<String>>();
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
        totalwords = 0;
        totalwordsconsidered = 0;
    }
    
    public GladLib(String source){
        initializeFromSource(source);
        myRandom = new Random();
    }
    
    private void initializeFromSource(String source) {
        String[] labels = {"country","noun","animal","adjective",
                            "name","color","timeframe","verb", "fruit"};
        for(String s : labels) {
            ArrayList<String> list = readIt(source+"/"+s+".txt");
            myMap.put(s,list);
        }
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {
        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }
        if (!wordconsiderations.contains(label)) {
            wordconsiderations.add(label);
        }
        return randomFrom(myMap.get(label));
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub;
        do {
        sub = getSubstitute(w.substring(first+1,last));
        } while(usedList.contains(sub));
        usedList.add(sub);
        usedCtr++;
        return prefix+sub+suffix;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    
    private void totalWordsInMap() {
        for (String key : myMap.keySet()) {
            totalwords = totalwords + myMap.get(key).size();
        }
        System.out.println("Total words in map: "+totalwords);
    }
    
    private void totalWordsConsidered() {
        for (String elt : wordconsiderations) {
            totalwordsconsidered = totalwordsconsidered + myMap.get(elt).size();
        }
        System.out.println("Total words used in the template: "+totalwordsconsidered);
    }
    
    public void makeStory(){
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate2.txt");
        printOut(story, 60);
        System.out.println("\n\nTotal number of words replaced: "+usedCtr);
        totalWordsInMap();
        totalWordsConsidered();
    }
    


}
