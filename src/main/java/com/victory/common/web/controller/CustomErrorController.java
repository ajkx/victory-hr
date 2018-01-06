package com.victory.common.web.controller;

import com.victory.common.domain.result.ExceptionMsg;
import com.victory.common.domain.result.Response;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by ajkx
 * Date: 2017/8/22.
 * Time:10:13
 */

@RestController
public class CustomErrorController extends AbstractErrorController{

    private static final String ERROR_PATH = "/error";

    public CustomErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleError() {
        return Response.error(ExceptionMsg.NOT_FOUND);
    }


}
