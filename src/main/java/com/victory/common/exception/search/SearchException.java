package com.victory.common.exception.search;

import org.springframework.core.NestedRuntimeException;

/**
 * Created by ajkx
 * Date: 2017/7/19.
 * Time:15:06
 */
public class SearchException extends NestedRuntimeException{
    public SearchException(String msg) {
        super(msg);
    }

    public SearchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
