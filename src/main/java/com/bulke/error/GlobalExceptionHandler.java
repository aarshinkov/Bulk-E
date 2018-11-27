package com.bulke.error;

import com.bulke.base.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends Base {

    private Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleException(Exception ex, HttpServletRequest request) {
        log.error("Exception occured!", ex);

        String errorMsg;

        if (isDBConnectError(ex)) {
            log.debug("Database exception!");
            errorMsg = getMessage("error.dbconnect");
        } else {
            log.debug("Other exception!");
            errorMsg = getMessage("error.system");
        }

        ModelAndView mv = new ModelAndView("error/error");
        mv.addObject("msgError", errorMsg);
        return mv;
    }
}
