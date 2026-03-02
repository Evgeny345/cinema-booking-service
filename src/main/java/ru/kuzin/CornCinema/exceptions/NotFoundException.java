package ru.kuzin.CornCinema.exceptions;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6313269609305449082L;

	public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<NotFoundException> notFoundException(String message, Object... args) {
        return () -> new NotFoundException(message, args);
    }

    public static Supplier<NotFoundException> notFoundException(String message) {
        return () -> new NotFoundException(message);
    }

}