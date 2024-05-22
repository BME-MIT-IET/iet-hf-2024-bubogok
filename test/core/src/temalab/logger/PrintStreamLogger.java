package temalab.logger;

import java.io.PrintStream;

public class PrintStreamLogger extends LoggerBase {
	protected final PrintStream printStream;

	public PrintStreamLogger(PrintStream printStream) {
		this.printStream = printStream;
	}

	protected void printLines(Label label, String message) {
		for (String line : message.split("\n")) {
			printStream.println(String.format("\t%s - %s", label.label, line.stripTrailing()));
		}
	}

	@Override
	public void debug(Label label, String message) {
		printStream.println(getInfos("DEBUG"));
		printLines(label, message);
	}

	@Override
	public void info(Label label, String message) {
		printStream.print(getInfos("INFO"));
		printLines(label, message);
	}

	@Override
	public void warning(Label label, String message) {
		printStream.print(getInfos("WARNING"));
		printLines(label, message);
	}

	@Override
	public void error(Label label, String message) {
		printStream.print(getInfos("ERROR"));
		printLines(label, message);
	}
}
