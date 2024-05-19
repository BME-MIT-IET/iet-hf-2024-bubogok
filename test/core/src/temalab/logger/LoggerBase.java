package temalab.logger;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class LoggerBase implements Logger {
    private PrintStream printStream = null;
    protected PrintStream getPrintStream() {return printStream;}

    public void setPrintStream(PrintStream p){
        printStream.close(); //TODO Cleaner
        printStream = p;
    }

    public LoggerBase(){
        printStream = System.err;
    }

    protected String getInfos(String level) {
        String res = "";

        String timeOfLog = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String header = String.format("[%s - %s]", timeOfLog, level);
        String className = getCallerClass();
        String function = String.format("%s()", getCallerMethod());
        
        res += String.format("%-35s\t%-10s\t%-10s", header, className, function);
        return res;
    }

    //TODO this should be overriden in each implementation
    public String getCallerClass(){
        //0 - getStackTrace()
        //1 - getCallerClass()
        //2 - getInfos()
        //3 - the logger class
        //4 - Log class
        //5 - the actual caller
        return Thread.currentThread().getStackTrace()[5].getClassName();
    }

    public String getCallerMethod(){
        return Thread.currentThread().getStackTrace()[5].getMethodName();
    }

    //TODO any better way to do it?
    @Override
    public void debug(String label, String message){}
    @Override
    public void info(String label, String message){}
    @Override
    public void warning(String label, String message){}
    @Override
    public void error(String label, String message){}
    
}
