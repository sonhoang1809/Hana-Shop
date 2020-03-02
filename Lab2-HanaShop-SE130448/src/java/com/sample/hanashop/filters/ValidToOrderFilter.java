/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.filters;

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
public class ValidToOrderFilter implements Filter {

//    private final String GOTOCHECKSHOPPINGCART = "LoadShoppingCartController";
    private final String ERRORADDRESS = "payment.jsp";
    private static final Logger log = Logger.getLogger(ValidToOrderFilter.class.getName());
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public ValidToOrderFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("ValidToOrderFilter:DoBeforeProcessing");
        }

    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("ValidToOrderFilter:DoAfterProcessing");
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
            String firstname = req.getParameter("firstname");
            if (firstname == null || firstname.equals("")) {
                req.setAttribute("ErrorAddress", "Received user's first name not allow null!!");
                req.getRequestDispatcher(ERRORADDRESS).forward(req, res);
                return;
            }
            String lastname = req.getParameter("lastname");
            if (lastname == null || lastname.equals("")) {
                req.setAttribute("ErrorAddress", "Received user's last name not allow null!!");
                req.getRequestDispatcher(ERRORADDRESS).forward(req, res);
                return;
            }
            String address = req.getParameter("address");
            if (address == null || address.equals("")) {
                req.setAttribute("ErrorAddress", "Received user's address not allow null!!");
                req.getRequestDispatcher(ERRORADDRESS).forward(req, res);
                return;
            }
            String street = req.getParameter("street");
            if (street == null || street.equals("")) {
                req.setAttribute("ErrorAddress", "Received user's street not allow null!!");
                req.getRequestDispatcher(ERRORADDRESS).forward(req, res);
                return;
            }
            String city = req.getParameter("city");
            if (city == null || city.equals("")) {
                req.setAttribute("ErrorAddress", "Received user's city not allow null!!");
                req.getRequestDispatcher(ERRORADDRESS).forward(req, res);
                return;
            }
            chain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("Error at ValidToOrderFilter: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at ValidToOrderFilter: " + ex.toString(), "Error!!");
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
                log("ValidToOrderFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("ValidToOrderFilter()");
        }
        StringBuffer sb = new StringBuffer("ValidToOrderFilter(");
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
