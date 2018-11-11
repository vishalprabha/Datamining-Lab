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

    public void handleMissing (int col, String naIndicator, Boolean isNumeric,Boolean isInteger){
        ArrayList<String[]> new_data = new ArrayList<>();
        if (isNumeric){
            float meanValue = 0;
            for(String row[] : data ){
                if(!row[col].equalsIgnoreCase(naIndicator))
                    meanValue += Float.parseFloat(row[col]);
            }
            meanValue /= data.size();
            System.out.println("Replacement for missing in col: "+ (col+1) +" is: "+ meanValue);
   
           for(String row[] : data ){
                if(row[col].equalsIgnoreCase(naIndicator)){
                    if (isInteger)
                        row[col] = Integer.toString((int) meanValue);
                    else
                        row[col] = Float.toString(meanValue);
                }
                new_data.add(row);
            }
        }
        else 
        {
            HashMap<String,Integer> freqMatrix = new HashMap<>();
            for(String row[] : data ){
                if(!row[col].equalsIgnoreCase(naIndicator)){
                    Integer count = freqMatrix.get(row[col]);
                    if(count == null) count = new Integer(0);
                    count++;
                    freqMatrix.put(row[col],count);
                }
            }
            Map.Entry<String,Integer> mostFrequent = null;
            for(Map.Entry<String,Integer> e : freqMatrix.entrySet()){
                if (mostFrequent == null || e.getValue() > mostFrequent.getValue())
                    mostFrequent = e;
            }
            System.out.println("Replacement for missing in col: "+ (col+1) +" is: "+ mostFrequent.getKey());
            for(String row[] : data ){
                if(row[col].equalsIgnoreCase(naIndicator)){
                    row[col] = mostFrequent.getKey();
                }
                new_data.add(row);
            }
        }
        data = new_data;
    }

    public void writeCSV(String filename){
        try{
            FileWriter writer = new FileWriter(filename);
            for (String[] row : data){
                writer.write(printRow(row));
                writer.write("\n");
            }
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String args[]){
        if(args.length != 1){
            System.out.println("Usage: java Preprocessor <input.csv>");
            return;
        }

        Preprocessor processor = new Preprocessor(args[0]);
        processor.handleMissing(1, "NA", true, true);//age
        processor.handleMissing(5, "NA", false, false);//month
        processor.writeCSV("afterMissing.csv");

    }
}