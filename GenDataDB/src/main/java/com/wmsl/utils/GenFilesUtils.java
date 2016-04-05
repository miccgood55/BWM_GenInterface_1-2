package com.wmsl.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

import com.wmsl.Constants;

@Component
public class GenFilesUtils {
	
//	private final Logger log = LoggerFactory.getLogger(GenFilesUtils.class);

//	public static final String DEFAULT_LINE_SEPARATOR = System.getProperty("line.separator");
//	public static final String ENCODING = "UTF-8";
//	public static final String FILENAME_EXT = ".txt";
	
	public static final int BUFFER_SIZE = 8192;


	public String getPathFile(String path, String filename, String ext) throws IOException{
		return new StringBuffer(path).append(filename).append(ext).toString();
	}
	public void genHeaderFile(String path, String filename, String ext) throws IOException{
		
		String writePositionFilename = getPathFile(path, filename, ext);

		
		try {
		    // อ่านไฟล์
		    FileInputStream fstream = new FileInputStream(writePositionFilename);
		    
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(writePositionFilename), BUFFER_SIZE);
			
			
		    // Get the object of DataInputStream
		    DataInputStream instream = new DataInputStream(fstream);
		    BufferedReader bf = new BufferedReader(new InputStreamReader(instream));
		    String line;
		    
		    // อ่านไฟล์ทีละบรรทัด
//		    int count = 0;
		    while ((line = bf.readLine()) != null) {
		        if(line != null && !line.equals("")){
//		        	count++;
		        }
				bufferedWriter.write(line);
		    }
		    // ปิด input stream
		    instream.close();
		    bufferedWriter.flush();
		    bufferedWriter.close();

		} catch (Exception e) {
		    System.out.println("Error: " + e.getMessage());
		}
//		RandomAccessFile file = new RandomAccessFile(writePositionFilename, "rw");
//		byte[] text = new byte[(int) file.length()];
//	    file.readFully(text);
//	    file.seek(0);
//	    file.writeBytes("Prepend 8");
//	    file.write(text);
//	    file.close();
//	    System.out.println("Done");
	}


	public void writeHeaderFile(BufferedWriter bufferedWriter, String date, long count) throws IOException{
		writeHeaderFile(bufferedWriter, date, String.valueOf(count));
	}
	
	public void writeHeaderFile(BufferedWriter bufferedWriter, String date, int count) throws IOException{
		writeHeaderFile(bufferedWriter, date, String.valueOf(count));
	}
	
	public void writeHeaderFile(BufferedWriter bufferedWriter, String date, String count) throws IOException{
		bufferedWriter.write(date);
		bufferedWriter.write("|");
		bufferedWriter.write(count);
		bufferedWriter.write(Constants.DEFAULT_LINE_SEPARATOR);
	}

	public BufferedWriter getBufferedWriter(String path, String filename, String ext) throws IOException{
		return getBufferedWriter(path, filename, ext, BUFFER_SIZE);
	}
	public BufferedWriter getBufferedWriter(String path, String filename, String ext, int bufferSize) throws IOException{

		File positionPath = new File(path);
		if (!positionPath.exists()) {
			positionPath.mkdirs();
		}

		String writePositionFilename = path + filename + ext;
		FileWriter writerPosition = new FileWriter(writePositionFilename);
		return new BufferedWriter(writerPosition, (bufferSize == 0 ? BUFFER_SIZE : bufferSize) * 10);
	}
	
}
