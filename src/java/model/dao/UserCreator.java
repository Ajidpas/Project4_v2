/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.entity.DBEntity;
import model.entity.User;

/**
 * Creator of the user object
 * @author Sasha
 */
public class UserCreator extends EntityCreator {
    
    /**
     * Constructor
     */
    public UserCreator() {
        super(USER_TABLE, USER_ID);
    }
    
    /**
     * Insert user into the data base from the object oriented entity
     * 
     * @param user user might be inserting into the data base
     * @throws SQLException 
     * @throws ServerOverloadedException
     * @return true is user inserting was successfull and false otherwise
     */
    public boolean insertUser(User user) throws SQLException, 
            ServerOverloadedException {
        boolean flag = false;
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.
                    prepareStatement(SQL_FOR_USER_INSERTING)) {
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPassword());
                ps.executeUpdate();
                flag = true;
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return flag;
    }
    
    /**
     * Update users' private information (first name, last name and email only)
     * @param newUser user object with new information
     * @return boolean true if updating was successful and false otherwise
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public boolean updateUser(User newUser) throws SQLException, 
            ServerOverloadedException {
        boolean flag = false;
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.
                    prepareStatement(SQL_FOR_USER_UPDATING)) {
                ps.setString(1, newUser.getFirstName());
                ps.setString(2, newUser.getLastName());
                ps.setString(3, newUser.getEmail());
                ps.setInt(4, newUser.getId());
                ps.executeUpdate();
                flag = true;
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return flag;
    }
    
    /**
     * Change user password 
     * @param user user object (here admin id is required only)
     * @param newPassword new user password
     * @return true if password changing is successful and false otherwise
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public boolean changePassword(User user, String newPassword) throws SQLException, 
            ServerOverloadedException {
        boolean flag = false;
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.
                    prepareStatement(SQL_TO_CHANGE_PASSWORD)) {
                ps.setString(1, newPassword);
                ps.setInt(2, user.getId());
                ps.executeUpdate();
                flag = true;
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return flag;
    } 

    /**
     * Get user from DB by email
     * @param email user's email
     * @return user if such user exists or null otherwise
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public DBEntity getUserByEmail(String email) throws SQLException, 
            ServerOverloadedException {
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_USER_BY_EMAIL);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getEntity(rs);
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return null;
    }
    
    /**
     * Get one user by result set
     * 
     * @param rs result set of sql query
     * @return DBEntity object
     * @throws SQLException
     */
    @Override
    protected DBEntity getEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        BigDecimal account = rs.getBigDecimal("account");
        User newUser = new User(firstName, lastName, email, password);
        newUser.setId(id);
        newUser.setAccount(account);
        return newUser;
    }
    
}
