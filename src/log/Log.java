package log;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

// Internetquelle: Java logging explained in 3 min. https://www.youtube.com/watch?v=4Bpg5i4tUFg&list=PLGNCbZkJtqa7W3LR101bjoYSI0ignYKCl&index=3
            //aufgerufen am 07.10.2021
public class Log {
    private static Log log_instance=null;
    public Logger logger;
    private FileHandler fh;
    private String language;

    private Log(String file_name) throws SecurityException, IOException{
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");

        File f = new File(file_name);
        if(!f.exists()){
            f.createNewFile();
        }

        fh = new FileHandler(file_name, true);
        logger = Logger.getLogger("Log");
        logger.addHandler(fh);
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public static Log getInstance(String file_name) throws IOException {
        if (log_instance == null)
            log_instance = new Log(file_name);

        return log_instance;
    }
}
