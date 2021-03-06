/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal.admin;

import controller.ConfigManager;
import controller.action.postactions.personal.SetOrderStatus;
import java.io.IOException;
import javax.servlet.ServletException;
import model.entity.Admin;
import model.entity.Order;

/**
 * Setting order status to READY
 * @author Sasha
 */
public class ConfirmPreparedOrder extends SetOrderStatus {

    /**
     * Set READY order status 
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected String doExecute() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
//            sendRedirect(null, "login.errormessage.loginplease", "home");
            setMessages(null, "login.errormessage.loginplease");
            return ConfigManager.getProperty("path.page.home");
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
//            sendRedirect(null, "administration.user.order.errormessage.wrongorderid");
            setMessages(null, "administration.user.order.errormessage.wrongorderid");
            return request.getHeader("Referer");
        }
        int orderId = Integer.parseInt(orderIdString);
        if (!setStatus(orderId, Order.OrderStatus.READY)) {
            return null;
        }
        sendRedirect(null, null);
        return request.getHeader("Referer");
    }
    
}
