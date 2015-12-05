package necat.base;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.slf4j.LoggerFactory;

public class BaseTest {
    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseTest.class);

    public BaseTest() {
        PatternLayout layout = new PatternLayout();
        String conversionPattern = "%-5p[%d][%t]%C(%L):%m%n";
        layout.setConversionPattern(conversionPattern);

        // creates console appender
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setLayout(layout);
        consoleAppender.setTarget("System.out");
        consoleAppender.setEncoding("UTF-8");
        consoleAppender.activateOptions();

        // configures the root logger
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.DEBUG);
        // configures the root logger
        rootLogger.removeAllAppenders();
        rootLogger.addAppender(consoleAppender);

        Logger logger = Logger.getLogger("io.netty.util.internal.logging");
        logger.setLevel(Level.WARN);

        InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
    }
}
