package temalab.logger;

public interface Logger {
	public void debug(Label label, String message);

	public void info(Label label, String message);

	public void warning(Label label, String message);

	public void error(Label label, String message);
}
