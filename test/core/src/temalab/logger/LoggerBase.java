package temalab.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class LoggerBase implements Logger {

	protected String getInfos(String level) {
		String timeOfLog = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String header = String.format("[%s - %s]", timeOfLog, level);
		String className = getCallerClass();
		String function = String.format("%s()", getCallerMethod());

		return String.format("%-35s\t%-10s\t%-10s", header, className, function);
	}

	protected String getCallerClass() {
		return getCallerStackTraceElement().getClassName();
	}

	protected String getCallerMethod() {
		return getCallerStackTraceElement().getMethodName();
	}

	protected StackTraceElement getCallerStackTraceElement() {
		var stackTrace = Thread.currentThread().getStackTrace();
		var callIndex = 0;
		var maxCallIndex = stackTrace.length - 2;
		while (
			!stackTrace[callIndex].getClassName().equals(Log.class.getName())
			&& callIndex <= maxCallIndex
		) {
			++callIndex;
		}
		if (callIndex > maxCallIndex) {
			throw new LoggerException("Logger was not called through the Log class");
		}
		var callerIndex = callIndex + 1;
		return stackTrace[callerIndex];
	}
}
