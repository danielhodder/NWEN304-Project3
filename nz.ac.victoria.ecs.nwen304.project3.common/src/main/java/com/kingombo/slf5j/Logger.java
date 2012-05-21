
package com.kingombo.slf5j;

import java.util.Formatter;

import org.slf4j.Marker;

public class Logger {

    private static ThreadLocal<Formatter> formatterCache = new FormatterCache();

    final private org.slf4j.Logger logger;

    public Logger(final org.slf4j.Logger logger) {
        this.logger = logger;
    }

    public void debug(final Marker marker, final String format, final Object... args) {
        this.logger.debug(marker, sprintf(format, args));
    }

    public void debug(final Marker marker, final String msg, final Throwable throwable) {
        this.logger.debug(marker, msg, throwable);
    }

    public void debug(final Marker marker, final String msg) {
        this.logger.debug(marker, msg);
    }


    public void debug(final String msg, final Throwable throwable) {
        this.logger.debug(msg, throwable);
    }

    public void debug(final String msg) {
        this.logger.debug(msg);
    }


    public void error(final Marker marker, final String format, final Object... args) {
        this.logger.error(marker, sprintf(format, args));
    }

    public void error(final Marker marker, final String msg, final Throwable throwable) {
        this.logger.error(marker, msg, throwable);
    }

    public void error(final Marker marker, final String msg) {
        this.logger.error(marker, msg);
    }

    public void error(final String msg, final Throwable throwable) {
        this.logger.error(msg, throwable);
    }

    public void error(final String msg) {
        this.logger.error(msg);
    }

    public String getName() {
        return this.logger.getName();
    }

    public void info(final Marker marker, final String format, final Object... args) {
        this.logger.info(marker, sprintf(format, args));
    }

    public void info(final Marker marker, final String msg, final Throwable throwable) {
        this.logger.info(marker, msg, throwable);
    }

    public void info(final Marker marker, final String msg) {
        this.logger.info(marker, msg);
    }

    public void info(final String s, final Throwable throwable) {
        this.logger.info(s, throwable);
    }

    public void info(final String msg) {
        this.logger.info(msg);
    }

    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }

    public boolean isDebugEnabled(final Marker marker) {
        return this.logger.isDebugEnabled(marker);
    }

    public boolean isErrorEnabled() {
        return this.logger.isErrorEnabled();
    }

    public boolean isErrorEnabled(final Marker marker) {
        return this.logger.isErrorEnabled(marker);
    }

    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }

    public boolean isInfoEnabled(final Marker marker) {
        return this.logger.isInfoEnabled(marker);
    }

    public boolean isTraceEnabled() {
        return this.logger.isTraceEnabled();
    }

    public boolean isTraceEnabled(final Marker marker) {
        return this.logger.isTraceEnabled(marker);
    }

    public boolean isWarnEnabled() {
        return this.logger.isWarnEnabled();
    }

    public boolean isWarnEnabled(final Marker marker) {
        return this.logger.isWarnEnabled(marker);
    }

    public void trace(final Marker marker, final String format, final Object... args) {
        this.logger.trace(marker, sprintf(format, args));
    }

    public void trace(final Marker marker, final String msg, final Throwable throwable) {
        this.logger.trace(marker, msg, throwable);
    }

    public void trace(final Marker marker, final String msg) {
        this.logger.trace(marker, msg);
    }


    public void trace(final String msg, final Throwable throwable) {
        this.logger.trace(msg, throwable);
    }

    public void trace(final String msg) {
        this.logger.trace(msg);
    }


    public void warn(final Marker marker, final String format, final Object... args) {
        this.logger.warn(marker, sprintf(format, args));
    }

    public void warn(final Marker marker, final String msg, final Throwable throwable) {
        this.logger.warn(marker, msg, throwable);
    }

    public void warn(final Marker marker, final String msg) {
        this.logger.warn(marker, msg);
    }

    public void warn(final String msg, final Throwable throwable) {
        this.logger.warn(msg, throwable);
    }

    public void warn(final String msg) {
        this.logger.warn(msg);
    }

    public void trace(final String format, final Object... args) {

        if (!this.logger.isTraceEnabled()) {
            return;
        }

        this.logger.trace(sprintf(format, args));
    }

    public void trace(final String format, final Throwable t, final Object... args) {

        if (!this.logger.isTraceEnabled())
            return;

        this.logger.trace(sprintf(format, args), t);
    }

    public void debug(final String format, final Object... args) {

        if (!this.logger.isDebugEnabled()) {
            return;
        }

        this.logger.debug(sprintf(format, args));
    }

    public void debug(final String format, final Throwable t, final Object... args) {

        if (!this.logger.isDebugEnabled())
            return;

        this.logger.debug(sprintf(format, args), t);
    }

    public void info(final String format, final Object... args) {

        if (!this.logger.isInfoEnabled()) {
            return;
        }

        this.logger.info(sprintf(format, args));
    }

    public void info(final String format, final Throwable t, final Object... args) {

        if (!this.logger.isInfoEnabled())
            return;

        this.logger.info(sprintf(format, args), t);
    }

    public void warn(final String format, final Object... args) {

        if (!this.logger.isWarnEnabled()) {
            return;
        }

        this.logger.warn(sprintf(format, args));
    }

    public void warn(final String format, final Throwable t, final Object... args) {

        if (!this.logger.isWarnEnabled())
            return;

        this.logger.warn(sprintf(format, args), t);
    }

    public void error(final String format, final Object... args) {

        if (!this.logger.isErrorEnabled()) {
            return;
        }

        this.logger.error(sprintf(format, args));
    }

    public void error(final String format, final Throwable t, final Object... args) {

        if (!this.logger.isErrorEnabled())
            return;

        this.logger.error(sprintf(format, args), t);
    }

    private static String sprintf(final String format, final Object[] args) {

        final Formatter formatter = getFormatter();
        formatter.format(format, args);

        final StringBuilder sb = (StringBuilder) formatter.out();
        final String message = sb.toString();
        sb.setLength(0);

        return message;
    }

    private static Formatter getFormatter() {
        return formatterCache.get();
    }

    public void traceMethodStart(final Object... args) {
        if (!this.logger.isTraceEnabled())
            return;
        final String method = Thread.currentThread().getStackTrace()[2].getMethodName();
        final StringBuilder sb = new StringBuilder();
        sb.append(method);
        sb.append("(");
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if(i != args.length-1)
            sb.append(",");
        }
        sb.append(") is called");
        this.logger.trace(sb.toString());
    }

    public void traceMethodEnd() {
        if (!this.logger.isTraceEnabled())
            return;
        final String method = Thread.currentThread().getStackTrace()[2].getMethodName();
        final StringBuilder sb = new StringBuilder();
        sb.append(method);
        sb.append(" is returned");
        this.logger.trace(sb.toString());
    }
}

class FormatterCache extends ThreadLocal<Formatter> {

    @Override
	protected synchronized Formatter initialValue() {
        return new Formatter();
    }

}


