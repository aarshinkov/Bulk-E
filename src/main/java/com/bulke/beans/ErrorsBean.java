package com.bulke.beans;

import com.bulke.base.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import javax.servlet.http.HttpServletRequest;

public class ErrorsBean extends Base {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private HttpServletRequest request;

    public void clearLastException() {
        try {
            request.getSession().removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        } catch (Exception ignored) {
        }
    }

    public boolean isDBError() {
        try {
            Throwable ex = (Throwable) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            return isDBConnectError(ex);
        } catch (Exception ignored) {
        }

        return false;
    }

    public boolean isLoginError() {
        try {
            Object ex = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

            if (ex != null) {
                if (ex instanceof DisabledException || ex instanceof BadCredentialsException) {
                    return true;
                }
            }
        } catch (Exception e) {
            //log.error("Error in isLoginError()!");
        }

        return false;
    }

    public boolean isLockedOut() {
        return request.getQueryString() != null && request.getQueryString().contains("lockedout");
    }

    public boolean isBadCredentials() {
        try {
            Object ex = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

            if (ex != null) {
                if (ex instanceof BadCredentialsException) {
                    return true;
                }
            }
        } catch (Exception e) {
            //log.error("Error in isBadCredentials()!");
        }

        return false;
    }

    public boolean isUserLocked() {
        try {
            Object ex = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

            if (ex != null) {
                if (ex instanceof LockedException) {
                    return true;
                }
            }
        } catch (Exception e) {
            //log.error("Error in isUserLocked()!");
        }

        return false;
    }

    public boolean isCredentialsExpired() {
        //Za deceased user se izpolzva
        try {
            Object ex = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

            if (ex != null) {
                if (ex instanceof CredentialsExpiredException) {
                    return true;
                }
            }
        } catch (Exception e) {
            //log.error("Error in isCredentialsExpired()!");
        }

        return false;
    }

    public boolean isUserDisabled() {
        try {
            Object ex = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

            if (ex != null) {
                if (ex instanceof DisabledException) {
                    return true;
                }
            }
        } catch (Exception e) {
            //log.error("Error in isUserDisabled()!");
        }

        return false;
    }
}
