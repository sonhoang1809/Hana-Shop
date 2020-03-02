/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.filters;

import com.sample.hanashop.daos.UserDAO;
import com.sample.hanashop.errors.SignupError;
import com.sample.hanashop.mails.SendMailSSL;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author sonho
 */
public class ValidAccountFilter implements Filter {

    private final String ERROR = "SearchController";
    private static final boolean debug = true;
    private static final Logger log = Logger.getLogger(ValidAccountFilter.class.getName());
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public ValidAccountFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("ValidAccountFilter:DoBeforeProcessing");
        }

    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("ValidAccountFilter:DoAfterProcessing");
        }

    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String userID = req.getParameter("userID");
            String email = req.getParameter("email");
            String userName = req.getParameter("userName");
            String password = req.getParameter("password");
            String confirm = req.getParameter("confirm");
            SignupError suError = new SignupError();
            boolean valid = true;
            if (UserDAO.checkExistUserID(userID)) {
                suError.setErrorUserID("This userID is already exist!!");
                valid = false;
            }
            if (UserDAO.checkExistEmail(email)) {
                suError.setErrorEmail("This email is already exist!!");
                valid = false;
            }
            if (userName.trim().length() == 0) {
                suError.setErrorName("Name must be required!!");
                valid = false;
            }
            if (password.trim().length() == 0) {
                suError.setErrorPassword("Password must be required!!");
                valid = false;
            }
            if (confirm.trim().length() == 0) {
                suError.setErrorPassword("Confirm must be required!!");
                valid = false;
            }
            if (!password.equalsIgnoreCase(confirm)) {
                suError.setErrorPassword("Password and confirm is not the same!!");
                valid = false;
            }
            if (valid) {
                chain.doFilter(request, response);
            } else {
                req.setAttribute("SIGNUPERROR", suError);
                req.getRequestDispatcher(ERROR).forward(req, res);
            }
        } catch (Exception ex) {
            log.error("Error at ValidAccountFilter: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at ValidAccountFilter: " + ex.toString(), "Error!!");
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("ValidAccountFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("ValidAccountFilter()");
        }
        StringBuffer sb = new StringBuffer("ValidAccountFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
