package ru.jskills.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by safin.v on 20.10.2016.
 */
public class SpringLoggingHelper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public void helpMethod() {
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
    }
}
