package touchstoneconverter;
import java.io.*;
import java.util.*;
/*
 * @author David B1QUAD
 */
public class converter {
	private String fPath, writePath, tempStr, header, conversionF; // conversionF G/g - to Ghz, M/m - to Mhz, K/k to Khz
	private boolean type; // For 'type' false is s1p, else is s2p
	private int endBuffer; 
	private double tempDouble;
	
	private ArrayList<String> list = new ArrayList<>();
	
	public converter(String filePath){
		fPath = filePath.replaceAll("[/|//|\\|\\\\]", "\\\\\\\\");
		writePath = fPath.replaceAll(fPath.substring(fPath.length() - 3), "csv");
	}
	
	public converter(String filePath, String conversionFactor){
		fPath = filePath.replaceAll("[/|//|\\|\\\\]", "\\\\\\\\");
		conversionF = conversionFactor.toUpperCase();
		writePath = fPath.replaceAll(fPath.substring(fPath.length() - 3), "csv");
	}
	
	private void checkType() { // works
		if (fPath.contains(".s1p")) {
			type = false;
			endBuffer = 0;
			header = "Freq, s11";
			System.out.println("s1p");
		} else if (fPath.contains(".s2p")) {
			type = true;
			endBuffer = 4;
			header = "Freq, s11, s21";
			System.out.println("s2p");
		} else {
			throw new java.lang.Error("Incorrect file type, supported types are \".s1p\" and \".s2p\"");
		}
	}
	
	public void readFile()throws IOException{
		Scanner file = new Scanner(new File(fPath));
		file.nextLine(); // reads the header line
		while(file.hasNextLine() && (file.hasNextInt() || file.hasNextDouble())){
			try {
				if (!type)
					tempStr = (convertFreq(file.nextDouble()) + ", " + Math.hypot(file.nextDouble(), file.nextDouble())); // Parse for .s1p
				else if (type) {
						tempStr = (convertFreq(file.nextDouble()) + ", " + Math.hypot(file.nextDouble(), file.nextDouble()) + ", " + Math.hypot(file.nextDouble(), file.nextDouble())); // Parse for .s2p
						if (file.hasNextInt()) {
							for(int i = 0; i < endBuffer; i++) {
								file.nextInt();
							}
						}
				}
			}
			catch(Exception E){
				throw new java.lang.Error("read file failed");
			}
			list.add(tempStr);
		}
	}
	public double convertFreq(double in){
		switch (conversionF) {
			case "G":
				return in / 1000000000;
			case "M":
				return in / 1000000;
			case "K":
				return in / 1000;
			default:
				return in;
		}

	}
	public void convertFile()throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(writePath));
		writer.write(header + "\n");
		for(String tmp : list){
			writer.write(tmp + "\n");
		}
		writer.close();
	}
	
	public void simpleConverter(String Path){
		fPath = Path;
		checkType();
		try{
			readFile();
		}
		catch(Exception E){
			throw new java.lang.Error("read file failed");
		}
		try{
			convertFile();
		}
		catch(Exception E){
			throw new java.lang.Error("write file failed");
		}
	}
	public static void main(String[] args) {
		converter test = new converter("C:\\Users\\David\\Desktop\\test.s2p");
		test.simpleConverter("C:\\Users\\David\\Desktop\\test.s2p");
	}
}
