package temalab.logger;
public class ConsoleLogger extends PrintStreamLogger{
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";

    public ConsoleLogger(){
        setPrintStream(System.out);
    }

    //TODO maybe can be placed elsewhere, e.g. Base or Logger interface and then it could be overriden
    private void printLines(String label, String message){
        for (String line : message.split("\n")) {
            printStream.println(String.format("\t%s - %s", label, line));
        }
        printStream.print(ANSI_RESET);
    }

    @Override
    public void debug(String label, String message){
        printStream.println(ANSI_RESET + getInfos("DEBUG"));
        printLines(label, message);
    }

    @Override
    public void info(String label, String message){
        printStream.print(ANSI_BLUE + getInfos("INFO"));
        printLines(label, message);
    }

    @Override
    public void warning(String label, String message){
        printStream.print(ANSI_YELLOW + getInfos("WARNING"));
        printLines(label, message);
    }

    @Override
    public void error(String label, String message){
        printStream.print(ANSI_RED + getInfos("ERROR"));     
        printLines(label, message);   
    }
}
