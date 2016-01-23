/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.action.ConcreteLink;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.entity.Admin;
import model.entity.Order;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class GetAllOrders extends AdminGetAction {

    @Override
    protected void doExecute() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return;
        }
        List<Order> orders = getAllOrders();
        if (orders == null || orders.size() < 1) {
            request.setAttribute("message", "administration.orders.message.noorders");
        } else {
            request.setAttribute("orders", orders);
        }
        List<User> users = getAllUsers();
        if (users == null || users.size() < 1) {
            request.setAttribute("message", "administration.users.message.nousers");
        } else {
            Map<Integer, User> userMap = createUserMap(users);
            request.setAttribute("userMap", userMap);
        }
        goToPage("administration.orders.text.title", "/view/person/admin/allorders.jsp");
    }
    
    /**
     * Get all orders from data base
     * @return list oof orders
     * @throws ServletException
     * @throws IOException 
     */
    private List<Order> getAllOrders() throws ServletException, IOException {
        OrderCreator orderCreator = new OrderCreator();
        try {
            return (List<Order>) orderCreator.getAllEntities();
        } catch (SQLException e) {
            sendRedirect(null, "exception.errormessage.sqlexception", "administration");
            return null;
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "administration");
            return null;
        }
    }
    
    /**
     * Create user map: key is user id, value = user object
     * @param users list of users
     * @return hash map with users by user id keys
     */
    private Map<Integer, User> createUserMap(List<User> users) {
        Map<Integer, User> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user);
        }
        return userMap;
    }
    
    /**
     * Get array list of link chain direct to current page (in fact this method 
     * gets link chain of its' previous page, add its' own link and return 
     * created array list)
     * 
     * @return array list of links
     */
    @Override
    public List<ConcreteLink> getLink() {
        List<ConcreteLink> links = new ArrayList<>();
        links.addAll(new Administration().getLink());
        String linkValue = "/servlet?getAction=getAllOrders";
        String linkName = "administration.orders.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
