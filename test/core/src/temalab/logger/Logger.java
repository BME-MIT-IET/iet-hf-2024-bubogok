package temalab.logger;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private PrintStream printStream;
    private Class<?> caller; //needed for retrieving the package's name from where the log in called

    /**
     * By default the Logger writes to the standard error stream.
     */
    public Logger(Class<?> caller) {
        this(caller, System.err);
    }

    public Logger(Class<?> caller, PrintStream p) {
        this.printStream = p;
        this.caller = caller;
    }

    public void setPrintStream(PrintStream p) {
        this.printStream.close(); // for files and such
        this.printStream = p;
    }

    private void log(String level, String label, String message) {
        String timeOfLog = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String header = String.format("[%s - %s]", timeOfLog, level);
        String pkg = caller.getClass().getPackage().getName();
        String function = Thread.currentThread().getStackTrace()[1].getMethodName()  + "()";
        
        this.printStream.print(String.format("%-35s\t%-10s\t%-10s", header, pkg, function));
        this.printStream.println(String.format("%s - %s", label, message));
    }

    public void debug(String label, String message){ log("DEBUG", label, message); }
    public void info(String label, String message){ log("INFO", label, message); }
    public void warning(String label, String message){ log("WARNING", label, message); }
    public void error(String label, String message){ log("ERROR", label, message); }
}
