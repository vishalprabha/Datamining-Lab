import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class GiniIndexHandler {
	
	public ArrayList<String[]> csvLines;
	
	public void readCSV(String filename, String delimeter) {
		try {
			csvLines = new ArrayList<>();
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String line;
			while((line = br.readLine())!=null) {
				String[] currentLine = line.split(delimeter);
				csvLines.add(currentLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void printCSV(){
		for(String[] currentLine : csvLines) {
			for(String s : currentLine) {
				System.out.print(s + ", ");
			}
			System.out.println();
		}
	}	
	private double calculateGiniIndex(int col) {
		double giniIndexValue = 0;
		int numberOfColumns = csvLines.get(0).length;
		HashMap<String, ArrayList<String[]>> hm = new HashMap<>();
		
		for(String[] currentLine : csvLines) {
			String key = currentLine[col];
			if(hm.containsKey(key)) {
				ArrayList<String[]> arr = hm.get(key);
				arr.add(currentLine);
				hm.put(key, arr);
			}
			else {
				ArrayList<String[]> arr = new ArrayList<>();
				arr.add(currentLine);
				hm.put(key, arr);
			}
		}
		
		ArrayList<Double> nodesGiniIndexValues = new ArrayList<>();
		for(Map.Entry<String, ArrayList<String[]>> m : hm.entrySet()) {
			ArrayList<String[]> currentNode = m.getValue();
			int size = currentNode.size();
			
			double numberOfYes=0, numberOfNo=0;
			for(String[] currentLine : currentNode) {
				if(currentLine[numberOfColumns-1].equals("Yes")) {
					numberOfYes++;
				}
				else {
					numberOfNo++;
				}
			}
			
			double currentNodeGiniValue; 
			currentNodeGiniValue = 1 - (numberOfYes*numberOfYes)/(size*size) - (numberOfNo*numberOfNo)/(size*size);
			currentNodeGiniValue  = currentNodeGiniValue*currentNode.size();
			nodesGiniIndexValues.add(currentNodeGiniValue);
		}
		
		for(Double d : nodesGiniIndexValues) {
		
			giniIndexValue += d/csvLines.size();
		}
		
		return giniIndexValue;
	}
	
	
	
	public void findBestSplit() {
		int numberOfColumns = csvLines.get(0).length;
		int columnToSplit = -1;
		double minGiniIndexValue = 999;
		for(int i=0; i<numberOfColumns-1; i++) {
			double giniValue = calculateGiniIndex(i);
			System.out.println("Column " + i + ": " + giniValue);
			if(giniValue < minGiniIndexValue) {
				minGiniIndexValue = giniValue;
				columnToSplit = i;
			}
		}
		
		System.out.println("Best column to split: " + columnToSplit);
	}
	public static void main(String[] args) {
		String filename = "/Users/vishalprabhachandar/Documents/Programming/DataminingLab/Java-Programs/B4/src/input.csv";
		String delimiter = ",";
		GiniIndexHandler gh = new GiniIndexHandler();
		gh.readCSV(filename,delimiter);
		gh.printCSV();
		gh.findBestSplit();
	}
	
}