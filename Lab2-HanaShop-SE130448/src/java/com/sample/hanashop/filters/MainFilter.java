/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.filters;

import com.sample.hanashop.dtos.UserDTO;
import com.sample.hanashop.mails.SendMailSSL;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author sonho
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class MainFilter implements Filter {

    private final String DEFAULT = "SearchController";
    private final String INDEX = "index.jsp";
    private final String SEARCH = "SearchController";
    private final String SIGNUPUSER = "SignupController";
    private final String SIGNUPBYGOOGLE = "SignupByGoogleController";
    private final String SENDCODEUSER = "SendCodeUserController";
    private final String CONFIRMCODEMAIL = "VerifyEmailController";
    private final String LOGIN = "LoginController";
    private final String LOGINBYGOOGLE = "LoginByGoogleController";
    private final String ADDTOCART = "AddAFoodToCartController";
    private final String GOTOCHECKSHOPPINGCART = "LoadShoppingCartController";
    private final String DELETEAFOODINCART = "DeleteAFoodInCartController";
    private final String LOADBILLUSER = "SearchBillByUserController";
    private final String VIEWDETAILUSER = "user-details.jsp";
    private final String VIEWBILLDETAILS = "bill-details.jsp";
    private final String VIEWSHOPPINGHISTORY = "LoadShoppingHistoryController";
    private final String VIEWDETAILFOOD = "LoadFoodDetailsController";
    private final String UPDATECART = "UpdateCartController";
    private final String VIEWSHOPPINGCART = "shopping-cart.jsp";
    private final String GOTOPAYMENT = "payment.jsp";
    private final String GOTOMETHODPAYMENT = "method-payment.jsp";
    private final String GOTOPAY = "PayController";
    private final String CREATEPAYPALORDER = "CreatePayPalOrderController";
    private final String GOTOPAYPALMETHOD = "PayByPaypalController";
    private final String REFUNDMONEY = "RefundMoneyController";
    private final String FINISHORDER = "FinishOrderController";
    private final String GOTOCREATEFOOD = "create-a-food.jsp";
    private final String COMMENTFOOD = "PostCommentController";
    private final String LOGOUT = "LogoutController";
    private final String GOTOADDFOOD = "add-food.jsp";
    private final String ADDNEWFOOD = "AddNewFoodController";
    private final String UPDATEFOOD = "UpdateFoodController";
    private final String DELETEFOOD = "DeleteFoodController";
    private final String VIEWMANAGEPAGE = "manage-hanashop.jsp";
    private final String GOTOMANAGEFOOD = "manage-food.jsp";
    private final String GOTOMANAGEACCOUNT = "manage-account.jsp";
    private final String LOADUSERDETAILS = "LoadUserDetailsController";
    private final String UPDATEUSER = "UpdateUserController";

    private static final boolean debug = true;

    private static final Logger log = Logger.getLogger(MainFilter.class.getName());

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    private final List<String> LISTUSERCANDO;
    private final List<String> LISTADMINCANDO;
    private final List<String> LISTGUESSCANDO;

    public MainFilter() {
        LISTUSERCANDO = new ArrayList<>();
        LISTUSERCANDO.add("");
        LISTUSERCANDO.add(DEFAULT);
        LISTUSERCANDO.add(SEARCH);
        LISTUSERCANDO.add(LOGOUT);
        LISTUSERCANDO.add(GOTOCHECKSHOPPINGCART);
        LISTUSERCANDO.add(LOADBILLUSER);
        LISTUSERCANDO.add(VIEWDETAILUSER);
        LISTUSERCANDO.add(ADDTOCART);
        LISTUSERCANDO.add(COMMENTFOOD);
        LISTUSERCANDO.add(INDEX);
        LISTUSERCANDO.add(VIEWSHOPPINGCART);
        LISTUSERCANDO.add(UPDATECART);
        LISTUSERCANDO.add(VIEWDETAILFOOD);
        LISTUSERCANDO.add(DELETEAFOODINCART);
        LISTUSERCANDO.add(VIEWSHOPPINGHISTORY);
        LISTUSERCANDO.add(VIEWBILLDETAILS);
        LISTUSERCANDO.add(GOTOPAYMENT);
        LISTUSERCANDO.add(GOTOMETHODPAYMENT);
        LISTUSERCANDO.add(GOTOPAY);
        LISTUSERCANDO.add(GOTOPAYPALMETHOD);
        LISTUSERCANDO.add(CREATEPAYPALORDER);
        LISTUSERCANDO.add(REFUNDMONEY);
        LISTUSERCANDO.add(FINISHORDER);
        LISTUSERCANDO.add(LOADUSERDETAILS);
        LISTUSERCANDO.add(UPDATEUSER);

        LISTADMINCANDO = new ArrayList<>();
        LISTADMINCANDO.add("");
        LISTADMINCANDO.add(DEFAULT);
        LISTADMINCANDO.add(SEARCH);
        LISTADMINCANDO.add(LOGOUT);
        LISTADMINCANDO.add(LOADBILLUSER);
        LISTADMINCANDO.add(VIEWDETAILUSER);
        LISTADMINCANDO.add(VIEWBILLDETAILS);
        LISTADMINCANDO.add(INDEX);
        LISTADMINCANDO.add(GOTOCREATEFOOD);
        LISTADMINCANDO.add(VIEWDETAILFOOD);
        LISTADMINCANDO.add(GOTOADDFOOD);
        LISTADMINCANDO.add(ADDNEWFOOD);
        LISTADMINCANDO.add(UPDATEFOOD);
        LISTADMINCANDO.add(DELETEFOOD);
        LISTADMINCANDO.add(VIEWMANAGEPAGE);
        LISTADMINCANDO.add(GOTOMANAGEFOOD);
        LISTADMINCANDO.add(VIEWSHOPPINGHISTORY);
        LISTADMINCANDO.add(GOTOMANAGEACCOUNT);
        LISTADMINCANDO.add(LOADUSERDETAILS);
        LISTADMINCANDO.add(UPDATEUSER);
        LISTADMINCANDO.add("");

        LISTGUESSCANDO = new ArrayList<>();
        LISTGUESSCANDO.add("");
        LISTGUESSCANDO.add(INDEX);
        LISTGUESSCANDO.add(LOGIN);
        LISTGUESSCANDO.add(LOGINBYGOOGLE);
        LISTGUESSCANDO.add(SEARCH);
        LISTGUESSCANDO.add(DEFAULT);
        LISTGUESSCANDO.add(SIGNUPUSER);
        LISTGUESSCANDO.add(SIGNUPBYGOOGLE);
        LISTGUESSCANDO.add(CONFIRMCODEMAIL);
        LISTGUESSCANDO.add(VIEWDETAILFOOD);
        LISTGUESSCANDO.add(SENDCODEUSER);
        LISTGUESSCANDO.add(SENDCODEUSER);
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("MainFilter:DoBeforeProcessing");
        }
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("MainFilter:DoAfterProcessing");
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
            String uri = req.getRequestURI();
            if ((uri.contains(".ttf") || uri.contains(".scss") || uri.contains(".svg")
                    || uri.contains(".eot") || uri.contains(".otf")
                    || uri.contains(".woff") || uri.contains(".woff2")
                    || uri.contains(".js") || uri.contains(".css")
                    || uri.contains(".jpg") || uri.contains(".png")
                    || uri.contains(".gif")
                    || uri.contains(".jpeg")) && !uri.contains(".jsp")) {
                chain.doFilter(request, response);
            } else {
                if (uri.contains(SIGNUPUSER)
                        || uri.contains(SIGNUPBYGOOGLE)
                        || uri.contains(LOGIN)
                        || uri.contains(LOGINBYGOOGLE)) {
                    chain.doFilter(request, response);
                    return;
                }
                String resource = null;
                int index = uri.lastIndexOf("/");
                resource = uri.substring(index + 1);
                HttpSession session = req.getSession();
                //phan quyen va dispatcher
                if (session != null) {
                    if (session.getAttribute("USERDTO") != null) {
                        UserDTO userDTO = (UserDTO) session.getAttribute("USERDTO");
                        String role = userDTO.getRoleID();
                        if (role.equalsIgnoreCase("AD") && LISTADMINCANDO.contains(resource)) {
                            chain.doFilter(request, response);
                            return;
                        } else if (!role.equalsIgnoreCase("AD") && LISTUSERCANDO.contains(resource)) {
                            chain.doFilter(request, response);
                            return;
                        } else {
                            res.sendRedirect(SEARCH);
                        }
                    } else {
                        if (LISTGUESSCANDO.contains(resource)) {
                            chain.doFilter(request, response);
                            return;
                        } else {
                            res.sendRedirect(SEARCH);
                        }
                    }
                } else {
                    if (LISTGUESSCANDO.contains(resource)) {
                        chain.doFilter(request, response);
                        return;
                    } else {
                        res.sendRedirect(SEARCH);
                        return;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error at MainFilter: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at MainFilter: " + ex.toString(), "Error!!");
        }

        doBeforeProcessing(request, response);

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
                log("MainFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("MainFilter()");
        }
        StringBuffer sb = new StringBuffer("MainFilter(");
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
