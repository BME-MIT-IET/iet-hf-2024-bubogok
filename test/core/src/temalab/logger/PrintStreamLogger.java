package temalab.logger;

import java.io.PrintStream;

public class PrintStreamLogger extends LoggerBase {
    protected PrintStream printStream = null;

    public void setPrintStream(PrintStream p){
        printStream.close(); //TODO Cleaner
        printStream = p;
    }

    public PrintStreamLogger(){
        printStream = System.err;
    }

    @Override
    public void debug(String label, String message){
        printStream.println(getInfos("DEBUG"));
    }

    @Override
    public void info(String label, String message){
        printStream.print(getInfos("INFO"));
    }

    @Override
    public void warning(String label, String message){
        printStream.print(getInfos("WARNING"));
    }

    @Override
    public void error(String label, String message){
        printStream.print(getInfos("ERROR"));     
    }
}
