package temalab.logger;

import java.io.PrintStream;

public class ConsoleLogger extends PrintStreamLogger {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";

	public ConsoleLogger() {
		this(System.err);
	}

	public ConsoleLogger(PrintStream printStream) {
		super(printStream);
	}

	@Override
	public void debug(String label, String message) {
		printStream.println(ANSI_RESET);
		super.debug(label, message);
		printStream.print(ANSI_RESET);
	}

	@Override
	public void info(String label, String message) {
		printStream.print(ANSI_BLUE);
		super.info(label, message);
		printStream.print(ANSI_RESET);
	}

	@Override
	public void warning(String label, String message) {
		printStream.print(ANSI_YELLOW);
		super.warning(label, message);
		printStream.print(ANSI_RESET);
	}

	@Override
	public void error(String label, String message) {
		printStream.print(ANSI_RED);
		super.error(label, message);
		printStream.print(ANSI_RESET);
	}
}
