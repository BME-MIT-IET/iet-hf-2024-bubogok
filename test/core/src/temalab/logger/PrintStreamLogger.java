package temalab.logger;

import java.io.PrintStream;

public class PrintStreamLogger extends LoggerBase {
	protected final PrintStream printStream;

	public PrintStreamLogger(PrintStream printStream) {
		this.printStream = printStream;
	}

	protected void printLines(String label, String message) {
		for (String line : message.split("\n")) {
			printStream.println(String.format("\t%s - %s", label, line));
		}
	}

	@Override
	public void debug(String label, String message) {
		printStream.println(getInfos("DEBUG"));
		printLines(label, message);
	}

	@Override
	public void info(String label, String message) {
		printStream.print(getInfos("INFO"));
		printLines(label, message);
	}

	@Override
	public void warning(String label, String message) {
		printStream.print(getInfos("WARNING"));
		printLines(label, message);
	}

	@Override
	public void error(String label, String message) {
		printStream.print(getInfos("ERROR"));
		printLines(label, message);
	}
}
