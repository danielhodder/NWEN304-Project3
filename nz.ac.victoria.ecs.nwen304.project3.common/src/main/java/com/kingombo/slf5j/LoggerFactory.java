
package com.kingombo.slf5j;

public class LoggerFactory {

    public static Logger getLogger(String name) {
        return getLoggerImpl(name);
    }

    public static Logger getLogger(Class<?> clazz) {
        return getLoggerImpl(clazz.getName());
    }

    public static Logger getLogger() {

        String name = new Exception().getStackTrace()[1].getClassName();
        return getLoggerImpl(name);
    }

    private static Logger getLoggerImpl(String name) {

        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(name);
        return new Logger(logger);

    }

}
