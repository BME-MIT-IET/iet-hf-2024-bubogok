package temalab.logger;

/**
 * Logs to the standard output.
 * Additionally, it changes (and resets) the console's color based on the type of the log.
 */
public class ConsoleLogger implements Logger {
    static final String ANSI_RESET = "\u001B[0m",
                        ANSI_RED = "\u001B[31m",
                        ANSI_YELLOW = "\u001B[33m",
                        ANSI_BLUE = "\u001B[34m";
    static void d(String area, String message){
        System.out.println(ANSI_RESET + "DEBUG("+area+"): " + message + ANSI_RESET);
    }
    static void i(String area, String message){
        System.out.println(ANSI_BLUE + "INFO("+area+"): " + message + ANSI_RESET);
    }
    static void w(String area, String message){
        System.out.println(ANSI_YELLOW + "WARNING("+area+"): " + message + ANSI_RESET);
    }
    static void e(String area, String message){
        System.out.println(ANSI_RED + "ERROR("+area+"): " + message + ANSI_RESET);
    }
}
