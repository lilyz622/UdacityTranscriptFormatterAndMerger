import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Converts transcripts from Udacity courses into a formatted chosen format file.
 * Date: June 2016
 * @author lilyz622
 *
 */

public class UdacityTranscriptConverter {
	public static File folder = null;;
	public static void main(String[] args) throws FileNotFoundException{
		
		Logger.getGlobal().setLevel(Level.INFO);
		//Logger.getGlobal().setLevel(Level.OFF);
		
		//Get all files		
		folder = new File("\\C:\\Users\\yzhan265\\Documents\\CUS\\Android Udacity\\Lesson 2- Connect Sunshine to the Cloud Subtitles");
		Logger.getGlobal().info(folder.getName());
		
		File[] listOfInputFiles = folder.listFiles();
		
		/*File[] txtFiles = srtToTxt(listOfInputFiles);
		Logger.getGlobal().info(txtFiles.length+"");
		
		for (int i = 0; i < txtFiles.length; i++){
			formatTxtFile(txtFiles[i]);
		}*/
		
		File[] newExtensionFiles = toFileType(listOfInputFiles, "srt","doc");
		
		/*for (int i = 0; i < newExtensionFiles.length; i++){
			formatTxtFile(newExtensionFiles[i]);
		}*/
		
		File[] formattedFiles = formatFiles(newExtensionFiles);
		
		File mergedTranscript = mergeTranscript(formattedFiles);
		
		
	}
	
	/*//Converts files into text files.
	public static File[] srtToTxt(File[] srtFiles){
		File[] txtFiles = new File[srtFiles.length];
		
		for (int i = 0; i<srtFiles.length; i++){
			Logger.getGlobal().info("File name:"+srtFiles[i].getName());
			if (srtFiles[i].getName().endsWith(".srt")){
				//Create name with txt ending
				String newName = srtFiles[i].getName().substring(0, srtFiles[i].getName().length()-3) + "txt";
				
				//renames srt file into txt file
				txtFiles[i] = new File(folder.getAbsolutePath()+File.separator+newName);
				Logger.getGlobal().info(txtFiles[i].getAbsolutePath());
				Logger.getGlobal().info("new name: "+txtFiles[i].getName());
				srtFiles[i].renameTo(txtFiles[i]);
			}
			else {
				Logger.getGlobal().info("ALREADY TXT: "+srtFiles[i].getName());
				txtFiles[i] = srtFiles[i];
			}
		}
		return txtFiles; 
	}*/
	
	/**
	 * merges formatted transcripts into one doc file
	 * @param formattedFiles
	 * @return
	 * @throws FileNotFoundException
	 */
	public static File mergeTranscript(File[] formattedFiles) throws FileNotFoundException {
		String mergedTranscriptName = "Merged Transcript.doc";
		File mergedFile = new File(folder.getAbsolutePath() + File.separator + mergedTranscriptName);
		PrintWriter out = new PrintWriter(mergedFile);
		for (int i = 0; i<formattedFiles.length; i++){
			Scanner in = new Scanner(formattedFiles[i]);
			out.println(formattedFiles[i].getName());
			while (in.hasNextLine()){
				out.print(in.nextLine());
			}
			out.println();
			out.println();
			in.close();
		}
		out.close();
		return null;
	}

	/**
	 * Parses text file into desired format.
	 * @param unformattedFiles
	 * @throws FileNotFoundException 
	 */
	public static File[] formatFiles(File[] unformattedFiles) throws FileNotFoundException{
		File[] formattedFiles = new File[unformattedFiles.length];
		for (int i = 0; i < unformattedFiles.length; i++) {
			Scanner in = new Scanner(unformattedFiles[i]);
			String newName = "FORMATTED-" + unformattedFiles[i].getName().substring(0, unformattedFiles[i].getName().length());
			Logger.getGlobal().info(newName);
			File formattedFile = new File(folder.getAbsolutePath() + File.separator + newName);
			formattedFiles[i] = formattedFile;
			PrintWriter out = new PrintWriter(formattedFile);
			int lineCounter = 1;
			while (in.hasNextLine()) {
				if (lineCounter % 3 == 1) {
					String time = in.nextLine();
				} else if (lineCounter % 3 == 2) {
					String words = in.nextLine();
					out.print(words + " ");
				} else {
					String blank = in.nextLine();
				}
				lineCounter++;
			}
			in.close();
			out.close();
		}
		Logger.getGlobal().info(Arrays.toString(formattedFiles));
		return formattedFiles;
	}
	
	/**
	 * Converts files into desired type of file.
	 * @param originalFiles
	 * @param oldExtension
	 * @param newExtension
	 * @return
	 */
	public static File[] toFileType(File[] originalFiles, String oldExtension, String newExtension){
		File[] newExtensionFiles = new File[originalFiles.length];
		int oldExtensionLength = oldExtension.length();
		
		for (int i = 0; i<originalFiles.length; i++){
			Logger.getGlobal().info("File name:"+originalFiles[i].getName());
			if (originalFiles[i].getName().endsWith(oldExtension)){
				//Create name with new extension ending
				String newName = originalFiles[i].getName().substring(0, originalFiles[i].getName().length()-oldExtensionLength) + newExtension;
				
				//renames original file into file with new extension
				newExtensionFiles[i] = new File(folder.getAbsolutePath()+File.separator+newName);
				Logger.getGlobal().info(newExtensionFiles[i].getAbsolutePath());
				Logger.getGlobal().info("new name: "+newExtensionFiles[i].getName());
				originalFiles[i].renameTo(newExtensionFiles[i]);
			}
			else {
				Logger.getGlobal().info("NOT A "+oldExtension.toUpperCase()+" FILE:"+originalFiles[i].getName());
				newExtensionFiles[i] = originalFiles[i];
			}
		}
		return newExtensionFiles; 
	}
}
