package br.com.sidroniolima.admin.domain.exceptions;

import br.com.sidroniolima.admin.domain.validation.Error;

import java.util.List;

public class InternalErrorException extends NoStackTraceException {

    protected InternalErrorException(final String aMessage, final Throwable t) {
        super(aMessage, t);
    }

    public static InternalErrorException with(final String message, final Throwable t) {
        return new InternalErrorException(message, t);
    }
}
