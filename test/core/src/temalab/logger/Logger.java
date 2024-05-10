package temalab.logger;

public interface Logger {
    /**
     * Debug log.
     * @param area - Area of log. (e.g. "Button" if you want to log a button's state)
     * @param message - The message you want to log.
     */

    static void d(String area, String message){}
    static void i(String area, String message){}
    static void w(String area, String message){}
    static void e(String area, String message){}
}
