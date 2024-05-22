package temalab.logger;

public interface Logger {
	public void debug(String label, String message);

	public void info(String label, String message);

	public void warning(String label, String message);

	public void error(String label, String message);
}
