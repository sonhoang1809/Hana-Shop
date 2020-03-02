/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.filters;

import com.sample.hanashop.daos.FoodDAO;
import com.sample.hanashop.daos.FoodInBillDAO;
import com.sample.hanashop.dtos.BillDTO;
import com.sample.hanashop.dtos.FoodDTO;
import com.sample.hanashop.dtos.FoodsInBillDTO;
import com.sample.hanashop.mails.SendMailSSL;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author sonho
 */
public class CheckAvailableFoodInBillFilter implements Filter {

    private static final boolean debug = true;
    private final String GOTOCHECKSHOPPINGCART = "LoadShoppingCartController";
    private final String UPDATECART = "UpdateCartController";
    private final String REFUNDMONEY = "RefundMoneyController";
    private final String FINISHORDER = "FinishOrderController";
    private final String ERROR = "LoadShoppingCartController";
    private static final Logger log = Logger.getLogger(CheckAvailableFoodInBillFilter.class.getName());
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public CheckAvailableFoodInBillFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("CheckAvailableFoodInBillFilter2:DoBeforeProcessing");
        }

    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("CheckAvailableFoodInBillFilter2:DoAfterProcessing");
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
        doBeforeProcessing(request, response);
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String uri = req.getRequestURI();
            String resource = null;
            int index = uri.lastIndexOf("/");
            resource = uri.substring(index + 1);
            if (resource.equalsIgnoreCase(GOTOCHECKSHOPPINGCART)
                    && (req.getAttribute("QuantityError") != null
                    || req.getAttribute("MessageAdminChangePrice") != null
                    || req.getAttribute("ERRORNULLLISTFOODINBILL") != null
                    || req.getAttribute("ErrorFood") != null)) {
                chain.doFilter(request, response);
                return;
            }
            HttpSession session = req.getSession();
            BillDTO billDTO = (BillDTO) session.getAttribute("BILLDTO");
            if (billDTO != null) {
                List<FoodsInBillDTO> listFoodsInBill = FoodInBillDAO.getListFoodInBill(billDTO.getIdBill());
                if (listFoodsInBill != null) {
                    boolean valid = true;
                    for (FoodsInBillDTO x : listFoodsInBill) {
                        FoodDTO foodDTO = FoodDAO.getFoodDTO(x.getIdFood());
                        if (foodDTO.getStatusCode() == 0) {
                            valid = false;
                            request.setAttribute("ErrorFood", x.getIdFood() + "-" + x.getNameFood()
                                    + " is out of stock!!");
                            break;
                        }
                        if (foodDTO.getStatusCode() == 2) {
                            valid = false;
                            request.setAttribute("ErrorFood", x.getIdFood() + "-" + x.getNameFood()
                                    + " is stop selling!!");
                            break;
                        }
                        if (foodDTO.getPrice() != x.getPrice()) {
                            valid = false;
                            request.setAttribute("MessageAdminChangePrice", x.getIdFood() + "-" + x.getNameFood() + "'s Price "
                                    + "is changed !!!!!! Please check again your bill again !!!!!!!!!"
                                    + "\n-Old price: " + x.getPrice() + "| New Price: " + foodDTO.getPrice());
                            break;
                        }
                        if (foodDTO.getStatusCode() != 0 && foodDTO.getQuantity() < x.getQuantity()) {
                            valid = false;
                            request.setAttribute("QuantityError", foodDTO.getIdFood() + "-"
                                    + foodDTO.getNameFood() + " is out of stock!!  Only "
                                    + foodDTO.getQuantity() + " left!!");
                            break;
                        }
                        if (foodDTO.getStatusCode() != 0 && foodDTO.getStatusCode() != 2 && foodDTO.getQuantity() < x.getQuantity()) {
                            if (request.getAttribute("QuantityError") != null) {
                                request.removeAttribute("QuantityError");
                            }
                            if (request.getAttribute("ErrorFood") != null) {
                                request.removeAttribute("ErrorFood");
                            }
                        }
                    }
                    if (!valid) {
                        if (resource.equalsIgnoreCase(FINISHORDER)) {
                            req.getRequestDispatcher(REFUNDMONEY).forward(req, res);
                            return;
                        } else if (resource.equalsIgnoreCase(GOTOCHECKSHOPPINGCART) || resource.equalsIgnoreCase(UPDATECART)) {
                            chain.doFilter(request, response);
                            return;
                        } else {
                            req.getRequestDispatcher(GOTOCHECKSHOPPINGCART).forward(req, res);
                            return;
                        }
                    } else {
                        chain.doFilter(request, response);
                        return;
                    }
                } else if (!resource.equalsIgnoreCase(GOTOCHECKSHOPPINGCART)) {
                    req.setAttribute("ERRORNULLLISTFOODINBILL", "Your list food in bill is null!!");
                    req.getRequestDispatcher(ERROR).forward(req, res);
                    return;
                } else if (resource.equalsIgnoreCase(GOTOCHECKSHOPPINGCART)) {
                    chain.doFilter(request, response);
                    return;
                }
            } else if (resource.equalsIgnoreCase(GOTOCHECKSHOPPINGCART)) {
                chain.doFilter(request, response);
                return;
            } else {
                req.setAttribute("ERRORNULLLISTFOODINBILL", "Your list food in bill is null!!");
                req.getRequestDispatcher(GOTOCHECKSHOPPINGCART).forward(req, res);
                return;
            }
        } catch (Exception ex) {
            log.error("Error at CheckAvailableFoodInBillFilter: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at CheckAvailableFoodInBillFilter: " + ex.toString(), "Error!!");
        }
        doAfterProcessing(request, response);
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
                log("CheckAvailableFoodInBillFilter2:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("CheckAvailableFoodInBillFilter2()");
        }
        StringBuffer sb = new StringBuffer("CheckAvailableFoodInBillFilter2(");
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
