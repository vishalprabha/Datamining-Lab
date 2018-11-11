import java.io.*;
import java.util.*;
import java.text.*;

public class StrongRule {

    String[] freqItemSets;
    float support, confidence;
    String[] items;
    ArrayList<String> strongRules;
    static DecimalFormat df = new DecimalFormat("#.##");  // to print only 2 decimal place for support and confidence

    public StrongRule(String i, float sup, float conf){
        support = sup;
        confidence = conf;
        freqItemSets = new String[5];
        items = i.split(",");
        freqItemSets[4] = i.replace(",", "");
        strongRules = new ArrayList<>();
    }

    public static void main(String args[]) {
        String line;
        float sup, conf;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the comma separted 4-frequent itemset elements:");
        line = in.nextLine();
        System.out.println("Enter the Support Threshold & Confidence Threshold");
        sup = in.nextFloat();
        conf = in.nextFloat();
        StrongRule sr = new StrongRule(line, sup, conf);

        System.out.println("Frequent Itemsets:");
        sr.ItemSets();

        System.out.println("Candidate Rules:");
        sr.getRules();

        System.out.println("Strong Rules:");
        for(String rule : sr.strongRules)
            System.out.println(rule);
    }

    public void ItemSets() {
        // Generate k-1 item sets and multiply - using fk_1 * fk_1 strategy
        for (int i = 1; i <= 4; i++){
            if( i != 4)
                genkitems(i);
            String[] temp = freqItemSets[i].split(",");
            System.out.print(Arrays.toString(temp));
            System.out.println();
        }
    }

    public void genkitems(int k){
        // To generate kth item set, cross multiply k-1 * k-1 item set
        if (k == 1) {
            freqItemSets[1] = String.join("," , items);
        }
        else {
            HashSet<String> kitems = new HashSet();
            String[] fk_1 = freqItemSets[k-1].split(",");
            for (int i = 0; i < fk_1.length ; i++){
                for (int j = i + 1; j < fk_1.length; j++){

                    if(k == 2){
                        kitems.add( fk_1[i] + fk_1[j]); // A + B = AB
                    }

                    if(k == 3) {
                        if(fk_1[i].charAt(0) == fk_1[j].charAt(0))
                            kitems.add( fk_1[i] + fk_1[j].charAt(1)); // AB, AC : A = A thus AB + C
                    }
                }
            }
            // convert the string of the form "[a, b, c]" to "a,b,c"
            freqItemSets[k] = kitems.toString().replaceAll(" ", "");
            freqItemSets[k] = freqItemSets[k].substring(1,freqItemSets[k].length()-1);
        }
    }

    public void getRules() {
        ArrayList<String> rules = new ArrayList<>();
        for (int i = 4; i >= 2; i--){
            String[] f_k = freqItemSets[i].split(",");
            for(String item : f_k){
                ArrayList<Character> list = copyString(item);

                // To generate 1 - n rules
                for(Character c : list){
                    ArrayList<Character> temp = (ArrayList<Character>)list.clone();
                    temp.remove(c);
                    String antecedent = "" + c;
                    String consequent = getConsequent(temp);
                    rules.add(antecedent + " -> " + consequent);
                    if(list.size() != 2) // to prevent duplicates A -> B
                        rules.add(consequent + " -> " + antecedent);
                }

                // To generate 2 - 2 rules Eg. AB -> CD
                if ( item.length() == 4 ){
                    for(int c = 1; c <= 3; c++) {
                        ArrayList<Character> temp = (ArrayList<Character>)list.clone();
                        temp.remove(0);
                        temp.remove(c - 1);
                        String antecedent = "" + list.get(0) + list.get(c);
                        String consequent = getConsequent(temp);
                        rules.add(antecedent + " -> " + consequent);
                        rules.add(consequent + " -> " + antecedent);
                    }
                }
            }
        }

        // Print candidate rules with sup and conf
        for(String rule : rules) {
            Random r = new Random();
            double sup = r.nextFloat();
            double conf = r.nextFloat();
            System.out.println(rule + "\tSupport: " + df.format(sup) + " Confidence: " + df.format(conf));
            if (sup >= support && conf >= confidence){
                strongRules.add(rule);
            }
        }
    }

    public ArrayList<Character> copyString(String item){
        ArrayList<Character> list = new ArrayList<>();
        for(char c : item.toCharArray())
            list.add(c);
        return list;
    }

    public String getConsequent(ArrayList<Character> temp){
        String con = "";
        for(Character c : temp)
            con += c;
        return con;
    }
}
