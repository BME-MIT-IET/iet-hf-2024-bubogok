package temalab.logger;

public final class Log {
	private static Logger logger = new ConsoleLogger();

	/**
	 * The default logger is a {@link ConsoleLogger}.
	 * Use this method to change it.
	 */
	public static synchronized void setLogger(Logger newLogger) {
		logger = newLogger;
	}

	public static synchronized void d(String label, String message) {
		logger.debug(
				new Label(label, Label.Color.NONE, Label.Color.NONE),
				message);
	}

	public static synchronized void i(String label, String message) {
		logger.info(
				new Label(label, Label.Color.NONE, Label.Color.NONE),
				message);
	}

	public static synchronized void w(String label, String message) {
		logger.warning(
				new Label(label, Label.Color.NONE, Label.Color.NONE),
				message);
	}

	public static synchronized void e(String label, String message) {
		logger.error(
				new Label(label, Label.Color.NONE, Label.Color.NONE),
				message);
	}

	public static synchronized void d(Label label, String message) {
		logger.debug(label, message);
	}

	public static synchronized void i(Label label, String message) {
		logger.info(label, message);
	}

	public static synchronized void w(Label label, String message) {
		logger.warning(label, message);
	}

	public static synchronized void e(Label label, String message) {
		logger.error(label, message);
	}
}
