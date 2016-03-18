package com.wmsl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class FileWritingPerfTest {

	protected static final int ITERATIONS = 5;
	protected static final double MEG = (Math.pow(1024, 2));
	protected static final int RECORD_COUNT = 10000000;
	protected static final String RECORD = "Help I am trapped in a fortune cookie factory\n";
	protected static final int RECSIZE = RECORD.getBytes().length;

	public static void main(String[] args) throws Exception {
		
		
		long t1 = System.currentTimeMillis();

		// ... Append to StringBuffer.
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 100000; i++) {
		    buffer.append(i).append(' ');
		}

		long t2 = System.currentTimeMillis();

		// ... Append to StringBuilder.
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 100000; i++) {
		    builder.append(i).append(' ');
		}

		long t3 = System.currentTimeMillis();

		// ... Times.
		System.out.println(t2 - t1);
		System.out.println(t3 - t2);
		
		
//		List<String> records = new ArrayList<String>(RECORD_COUNT);
//		int size = 0;
//		for (int i = 0; i < RECORD_COUNT; i++) {
//			records.add(RECORD);
//			size += RECSIZE;
//		}
//		System.out.println(records.size() + " 'records'");
//		System.out.println(size / MEG + " MB");
//
//		for (int i = 0; i < ITERATIONS; i++) {
//			System.out.println("\nIteration " + i);
//
//			writeRaw(records);
//			writeBuffered(records, 8192);
//			writeBuffered(records, (int) MEG);
//			writeBuffered(records, 4 * (int) MEG);
//		}
	}

	protected static void writeRaw(List<String> records) throws IOException {
		File file = File.createTempFile("foo", ".txt");
		try {
			FileWriter writer = new FileWriter(file);
			System.out.print("Writing raw... ");
			write(records, writer);
		} finally {
			// comment this out if you want to inspect the files afterward
			file.delete();
		}
	}

	protected static void writeBuffered(List<String> records, int bufSize) throws IOException {
		File file = File.createTempFile("foo", ".txt");
		try {
			FileWriter writer = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(writer, bufSize);

			System.out.print("Writing buffered (buffer size: " + bufSize + ")... ");
			write(records, bufferedWriter);
		} finally {
			// comment this out if you want to inspect the files afterward
			file.delete();
		}
	}

	protected static void write(List<String> records, Writer writer) throws IOException {
		long start = System.currentTimeMillis();
		for (String record : records) {
			writer.write(record);
		}
		writer.flush();
		writer.close();
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000f + " seconds");
	}
}