import java.io.*;
import java.util.*;

public class Preprocessor {

    private ArrayList<String[]> data;

    public Preprocessor(String filename){
        data = new ArrayList<>();
        parseCSV(filename);
    }

    public void parseCSV(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while( (line = reader.readLine()) != null ){
                String row[] = line.split(",");
                data.add(row);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public String printRow(String row[]){
        String new_row = Arrays.toString(row);
        return new_row.substring(1, new_row.length() - 1);
    }

    public void discretization(int col, String outputfile){
        try {
            FileWriter writer = new FileWriter(outputfile);
            for(String row[] : data ){
                Float value = Float.parseFloat(row[col]);
                writer.write(printRow(row)+",");
                String discreteValue = "";
                if(value < 200)
                    discreteValue = "A";
                else if(value >= 200 && value < 400)
                    discreteValue = "B";
                else if(value >= 400 && value < 600)
                    discreteValue = "C";
                else if(value >= 600 && value < 800)
                    discreteValue = "D";
                else if(value >= 800 && value < 1000)
                    discreteValue = "E";
                else
                    discreteValue = "F";

                writer.write(discreteValue+"\n");
            }
            writer.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String[]> stratifiedSample(int sampsize){
         HashMap<String, ArrayList<String[]>> genderMap = new HashMap<>();
         for(String row[] : data ){
            ArrayList<String[]> results = genderMap.get(row[4]);
            if (results != null){
                results.add(row);
            }
            else {
                 results = new ArrayList<>();
                 results.add(row);
            }
            genderMap.put(row[4],results);
        }

        ArrayList<String[]> stratifiedSample = new ArrayList<>();

        for(String gender: genderMap.keySet()){
            ArrayList<String[]> items = genderMap.get(gender);
            int class_size = items.size();
            System.out.println("Gender: " + gender + " Class size: " + class_size+"/"+data.size());
            int class_samp_size = (int)Math.rint( (sampsize / (float)data.size()) * class_size );
            System.out.println("Gender: " + gender + "Class sample size: " + class_samp_size);
            Collections.shuffle(items);
            for(int i = 0 ; i < class_samp_size; i++)
                stratifiedSample.add(items.get(i));
        }
        return stratifiedSample;
    }

    public void aggregation(int group_col, int value_col) {
        HashMap<String, Float[]> genderAgg = new HashMap<>();
        for(String row[] : data ){
            if(genderAgg.containsKey(row[group_col])){
                Float minmax[] = genderAgg.get(row[group_col]);
                Float value = Float.parseFloat(row[value_col]);
                if( value < minmax[0])
                    minmax[0] = value;
                if( value > minmax[1])
                    minmax[1] = value;

                genderAgg.put(row[group_col], minmax);
            }
            else {
                Float value = Float.parseFloat(row[value_col]);
                genderAgg.put(row[group_col], new Float[]{value, value});
            }
        }

        for (String key : genderAgg.keySet()){
             Float[] value = genderAgg.get(key);
             System.out.println("Gender: "+key+" Min: "+value[0] + " Max: " + value[1]);
        }

    }

    public static void main(String args[]){
        if(args.length != 1){
            System.out.println("Usage: java Preprocessor <input.csv>");
            return;
        }

        Preprocessor dataProcessor = new Preprocessor(args[0]);
        dataProcessor.aggregation(4, 2);
        dataProcessor.discretization(5, "output.csv");
        ArrayList<String[]> sample = dataProcessor.stratifiedSample(4);
        Collections.shuffle(sample);
        for(String[] row : sample) {
             System.out.println(dataProcessor.printRow(row));
        }
    }
}