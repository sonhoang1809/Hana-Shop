/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.filters;

import com.sample.hanashop.daos.FoodDAO;
import com.sample.hanashop.daos.UserDAO;
import com.sample.hanashop.dtos.FoodDTO;
import com.sample.hanashop.dtos.UserDTO;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.supports.Support;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author sonho
 */
public class LoadUserDetailsFilter implements Filter {

    private final String ADMIN = "user-details.jsp";
    private static final boolean debug = true;
    private static final Logger log = Logger.getLogger(LoadUserDetailsFilter.class.getName());
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public LoadUserDetailsFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("LoadUserDetailsFilter:DoBeforeProcessing");
        }

    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("LoadUserDetailsFilter:DoAfterProcessing");
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
            HttpSession session = req.getSession();
            UserDTO userDTO = (UserDTO) session.getAttribute("USERDTO");
            String userID = req.getParameter("userID");
            if (userDTO.getRoleID().equalsIgnoreCase("US")) {
                userDTO = UserDAO.getFullUserDetail(userDTO.getUserID());
            } else if (userDTO.getRoleID().equalsIgnoreCase("AD")) {
                userDTO = UserDAO.getFullUserDetail(userID);
            }
            req.setAttribute("FULLDETAIUSERDTO", userDTO);
            //list food recent view of user
            {
                if (userDTO != null) {
                    List<String> listIdFoodsRecentView = null;
                    if (userID == null || userID.equals("")) {
                        listIdFoodsRecentView = (List<String>) session.getAttribute("LISTIDFOODSRECENTVIEW");
                    }
                    if (listIdFoodsRecentView != null) {
                        UserDAO.setFoodRecentViewOfUser(userDTO.getUserID(),
                                Support.convertToStringIdFoodsRecent(listIdFoodsRecentView));
                    } else {
                        String idFoodsRecentView = UserDAO.getFoodsRecentViewOfUser(userDTO.getUserID());
                        if (idFoodsRecentView != null) {
                            listIdFoodsRecentView = Support.convertToListIdFoodsRecent(idFoodsRecentView);
                        }
                    }
                    if (listIdFoodsRecentView != null && listIdFoodsRecentView.size() >= 1) {
                        List<FoodDTO> listFoodDTORecentView = new ArrayList<>();
                        if (listIdFoodsRecentView.size() > 3) {
                            for (int i = listIdFoodsRecentView.size() - 1; i >= listIdFoodsRecentView.size() - 3; i--) {
                                FoodDTO foodDTO = FoodDAO.getFoodDTO(listIdFoodsRecentView.get(i));
                                listFoodDTORecentView.add(foodDTO);
                            }
                        } else {
                            for (int i = listIdFoodsRecentView.size() - 1; i >= 0; i--) {
                                FoodDTO foodDTO = FoodDAO.getFoodDTO(listIdFoodsRecentView.get(i));
                                listFoodDTORecentView.add(foodDTO);
                            }
                        }
                        req.setAttribute("LISTFOODRECENTVIEW", listFoodDTORecentView);
                    }
                }
            }

            chain.doFilter(request, response);

        } catch (Exception ex) {
            log.error("Error at LoadUserDetailsFilter: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at LoadUserDetailsFilter: " + ex.toString(), "Error!!");
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
                log("LoadUserDetailsFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("LoadUserDetailsFilter()");
        }
        StringBuffer sb = new StringBuffer("LoadUserDetailsFilter(");
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
