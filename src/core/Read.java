package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Read {
	
	private char[][] levelData;
	
	private int lineCtr;
	
	private String fileName;
	
	private int fileSize;
	
	public void initFile(String fileName) {
		this.clean();
		
		this.fileName = fileName;
		
		this.fileSize = getFileSize();
		
		levelData = new char[fileSize][fileSize];
	}
	
	private void clean() {
		levelData = null;
	}
	
	/**
	 * By invoking this method the reader will start reading the file.
	 */
	public void startReading() {
		readFile();
	}
	
	/**
	 * This method is used to get a specified level data out of the refered file.
	 * There are two parameters required getting the level data out of the two-
	 * dimensional data matrix.
	 * @param x is the horizontal index of the marix.
	 * @param y is the vertical index of the matrix.
	 * @return an char which represents the level data at the xy-coordinate.
	 */
	public char getChar(int x, int y) {
		return levelData[x][y];
	}
	
	/**
	 * This method will return the length of the level data array. This is used
	 * to analyze the level data array because it is required to know the size 
	 * of the matrix.
	 * @return the size of the array as an Integer.
	 */
	public int getArrayLength() {
		return this.fileSize;
	}
	
	private int getFileSize() {
		int fileSize = 0;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader("C:/Users/Stefan/workspace/Desert survival/res/"+fileName+".level"));
			
			while(in.readLine() != null) {
				fileSize++;
			}
			
			in.close();
		} catch (IOException e) {e.printStackTrace();}
		
		return fileSize;
	}
	
	private void readFile() {
		try {
			// Erzeugt neuen Reader mit File
			BufferedReader in = new BufferedReader(new FileReader("C:/Users/Stefan/workspace/Desert survival/res/"+fileName+".level"));
			// Dieser String enth‰lt eine Zeile der Datei
			String line = null;
			
			// Solange es eine weitere Zeile in der Datei gibt
			while((line = in.readLine()) != null) {
				// Zeile wird ausgwertet
				handleLine(line);
				
				lineCtr++;
			}
			
			in.close();
		} catch(IOException e) {e.printStackTrace();}
	}
	
	private void handleLine(String line) {
		// In diesem Array werden alle Zeichen, die durch ein Komma getrennt sind,
		// aufgelistet
		String[] stringArray = line.split(",");
		
		// In diesem Array werden dann die Strings aus stringArray umgewandelt in char
		// und anschlieﬂend abgelegt
		char[] charArray = new char[stringArray.length];
		
		// Hier werden aus dem stringArray die Strings ausgelesen, in char umgewandelt
		// und in charArray geschrieben
		for(int i = 0; i < stringArray.length; i++) {
			charArray[i] = stringArray[i].charAt(0);
		}
		
		// Mit dieser Schleife werden nun alle umgewandelten chars in das globale 
		// charArray geschrieben, mit dem Index der aktuellen Zeile (lineCtr)
		// und dem aktuellen Zeichen aus charArray (charArray[i])
		for(int i = 0; i < charArray.length; i++) {
			levelData[i][lineCtr] = charArray[i];
		}
	}
}
 
