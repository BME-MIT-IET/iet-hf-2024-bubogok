package temalab.logger;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Log {
    private static Logger logger = new ConsoleLogger();

    /**
     * By default the Logger writes to the standard error stream.
     */
    // public Logger() {
    //     this(System.err);
    // }

    // public Logger(PrintStream p) {
    //     this.printStream = p;
    // }

    public void setLogger(Logger newLogger) {
        // this.printStream.close(); // for files and such
        // this.printStream = p;
        logger = newLogger;
    }

    public void d(String label, String message){ logger.debug(label, message); }
    public void i(String label, String message){ logger.info(label, message); }
    public void w(String label, String message){ logger.warning(label, message); }
    public void e(String label, String message){ logger.error(label, message); }
}
