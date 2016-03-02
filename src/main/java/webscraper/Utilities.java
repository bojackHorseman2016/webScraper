package webscraper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Utilities {
	
	private static PrintStream out = System.out;
	
	public static void outputOff() {
		System.setErr(getNullPrintStream());
		System.setOut(getNullPrintStream());
	}
	
	public static void outputOn() {
		System.setErr(out);
		System.setOut(out);
	}
	
	public static PrintStream getNullPrintStream() {

		return new PrintStream(new OutputStream() {
			@Override
			public void write(int arg0) throws IOException {
			}
		}) {
		};
	}
}
