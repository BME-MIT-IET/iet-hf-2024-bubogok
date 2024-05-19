package temalab.logger;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerBase implements Logger {
    private PrintStream printStream = null;

    public void setPrintStream(PrintStream p){
        printStream.close(); //TODO
        printStream = p;
    }

    protected void getInfos(String level, String label, String message) {
        String timeOfLog = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String header = String.format("[%s - %s]", timeOfLog, level);
        String className = Thread.currentThread().getStackTrace()[1].getClass().getName(); //caller.getClass().getPackage().getName();
        String function = Thread.currentThread().getStackTrace()[1].getMethodName()  + "()";
        
        this.printStream.print(String.format("%-35s\t%-10s\t%-10s", header, className, function));
        this.printStream.println(String.format("%s - %s", label, message));
    }


    @Override
    public void debug(String label, String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'debug'");
    }

    @Override
    public void info(String label, String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'info'");
    }

    @Override
    public void warning(String label, String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'warning'");
    }

    @Override
    public void error(String label, String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'error'");
    }
    
}
