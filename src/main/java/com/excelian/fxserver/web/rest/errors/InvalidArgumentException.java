package com.excelian.fxserver.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Created by dtsimbal on 8/1/18.
 */
public class InvalidArgumentException extends AbstractThrowableProblem {

    public InvalidArgumentException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.BAD_REQUEST);
    }

}
