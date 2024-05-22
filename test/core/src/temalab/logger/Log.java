package temalab.logger;

public final class Log {
	private static Logger logger = new ConsoleLogger();

	/**
	 * The default logger is a {@link ConsoleLogger}.
	 * Use this method to change it.
	 */
	public void setLogger(Logger newLogger) {
		logger = newLogger;
	}

	public static void d(String label, String message) {
		logger.debug(
				new Label(label, Label.Color.None, Label.Color.None),
				message);
	}

	public static void i(String label, String message) {
		logger.info(
				new Label(label, Label.Color.None, Label.Color.None),
				message);
	}

	public static void w(String label, String message) {
		logger.warning(
				new Label(label, Label.Color.None, Label.Color.None),
				message);
	}

	public static void e(String label, String message) {
		logger.error(
				new Label(label, Label.Color.None, Label.Color.None),
				message);
	}

	public static void d(Label label, String message) {
		logger.debug(label, message);
	}

	public static void i(Label label, String message) {
		logger.info(label, message);
	}

	public static void w(Label label, String message) {
		logger.warning(label, message);
	}

	public static void e(Label label, String message) {
		logger.error(label, message);
	}
}
