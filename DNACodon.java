
/**
 * Write a description of DNACodon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;

public class DNACodon {
    private HashMap<String,Integer> codon;
    
    public DNACodon() {
        codon = new HashMap<String, Integer>();
    }
    
    public void buildCodonMap(int start, String dna) {
        codon.clear();
        dna = dna.substring(start);
        String[] codon1 = dna.split("(?<=\\G.{3})");
        for (String elt : codon1) {
            if (elt.length() == 3) {
                if(codon.keySet().contains(elt)) {
                    codon.put(elt,codon.get(elt) + 1);
                }
                else {
                    codon.put(elt, 1);
                }
            }
    }
    }
    
    private String getMostCommonCodon() {
    String result = "UNKNOWN";
    int max_count=0;
    for (String elt : codon.keySet()) {
        if(codon.get(elt) > max_count) {
            max_count = codon.get(elt);
            result = elt;
        }
    }
    return "Codon "+result+" found "+max_count+" times.";
    }
    
    private void printCodonCounts(int start, int end) {
        System.out.println("Counts of codons between "+start+" and "+end+" are:");
        for (String elt : codon.keySet()) {
            if((codon.get(elt) >= start) && (codon.get(elt) <= end)) {
                System.out.println(elt+"\t"+codon.get(elt));
            }
        }
    }
    
    public void testCodon() {
        FileResource resource = new FileResource();
        String dna = resource.asString().trim();
        System.out.println("DNA: "+dna);
        for(int k=0;k<3;k++) {
            System.out.println("Building codon for index "+k);
            buildCodonMap(k,dna);
            System.out.println(getMostCommonCodon());
            printCodonCounts(7,7);
            System.out.println("Unique codons: "+codon.size());
            System.out.println("\n");
        }
    }
}
