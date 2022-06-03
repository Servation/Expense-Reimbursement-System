package com.revature.database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static Logger log = Logger.getLogger(DatabaseHandler.class.getName());
    private static DatabaseHandler dbHandler = null;
    private SessionFactory factory;
    private DatabaseHandler() {}

    public static DatabaseHandler getDbHandler(){
        log.info("Creating database connection");
        if(dbHandler == null){
            try{
                dbHandler = new DatabaseHandler();
                dbHandler.factory = new Configuration().configure().buildSessionFactory();
            } catch (Throwable ex){
                System.err.println("Failed to create Session Factory object");
            }
        }
        return dbHandler;
    }

    public User getUser(String username, String password){
        log.info("Getting user information for user: " + username + " password: " + password);
        User user = null;
        List<User> userList = listUser();
        for (User users : userList) {
            if (users.getUsername().equals(username) && users.getPassword().equals(password)) {
                user = users;
            }
        }
        log.info((user != null) ? "User found": "No user");
        return user;
    }

    public User getUserByEmail(String username, String email){
        log.info("Getting user information for user: " + username + " email: " + email);
        User user = null;
        List<User> userList = listUser();
        for (User users : userList) {
            if (users.getUsername().equals(username) && users.getEmail().equals(email)) {
                user = users;
            }
        }
        log.info((user != null) ? "User found!" : "No user found!");
        return user;
    }

    public List<User> listUser(){
        log.info("Getting list of users!");
        Transaction transaction = null;
        List<User> userList = new ArrayList<>();

        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            List currentList = session.createQuery("FROM User").list();
            for(Object users : currentList){
                userList.add((User) users);
            }
        }catch(HibernateException e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
            userList = null;
        }
        log.info((userList.size() != 0) ? "User list found!" : "Empty List!");
        return userList;
    }

    //updating current user's email
    public User updateEmail(int UserID, String newEmail){
        log.info("Updating email");
        User user;
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            user = session.get(User.class, UserID);
            user.setEmail(newEmail);
            transaction.commit();
            log.info("Email update!");
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            user = null;
            log.debug("Email not updated!");
        }
        return user;
    }
    //update username
    public User updateUsername(int UserID, String newUsername){
        log.info("Updating username");
        User user;
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            user = session.get(User.class, UserID);
            user.setUsername(newUsername);
            transaction.commit();
            log.info("Username updated");
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            user = null;
            log.debug("Username not updated");
        }
        return user;
    }
    //update password
    public User updatePassword(int UserID, String newPassword){
        log.info("Updating password");
        User user;
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            user = session.get(User.class, UserID);
            user.setPassword(newPassword);
            transaction.commit();
            log.info("Password updated");
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            user = null;
            log.debug("Password not updated");
        }
        return user;
    }

    //getting pending only
    public List<Reimbursement> listPending(){
        log.info("Viewing pending list");
        List<Reimbursement> currentList = listReimbursement();
        List<Reimbursement> pendingList = new ArrayList<>();

        for(Reimbursement reimbursement: currentList){
            if(reimbursement.getStatus().equals("Pending")) {
                pendingList.add(reimbursement);
            }
        }
        log.info((pendingList.size() != 0)? "Pending reimbursement list found": "Empty list");
        return pendingList;
    }

    public List<Reimbursement> listUserReimbursement(int userID){
        List<Reimbursement> currentList = listReimbursement();
        List<Reimbursement> myList = new ArrayList<>();

        for(Reimbursement reimbursement: currentList){
            if(reimbursement.getUser_ID() == userID) {
                myList.add(reimbursement);
            }
        }
        log.info((myList.size() != 0) ? "Found my list" : "Do not have any!");
        return myList;
    }
    //getting full reimbursement list
    public List<Reimbursement> listReimbursement(){
        log.info("Listing all reimbursement");
        Transaction transaction = null;
        List<Reimbursement> reimbursementsList = new ArrayList<>();

        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            List currentList = session.createQuery("FROM Reimbursement").list();
            for(Object reimbursements : currentList){
                reimbursementsList.add((Reimbursement) reimbursements);
            }
        }catch(HibernateException e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
            reimbursementsList = null;
        }
        log.info((reimbursementsList.size() != 0) ? "List exist" : "List do not exist");
        return reimbursementsList;
    }

    public void addingUser(String first_name, String last_name, String email, String type, String username, String password){
        log.info("Adding new user");
        Transaction transaction = null;
        User newUser = null;

        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            newUser = new User(first_name, last_name, email, type, username, password);
            session.save(newUser);
            transaction.commit();
            log.info("New user created");
        } catch(HibernateException e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
            log.debug("Cannot create new user!");
        }
    }

    public void addingReimbursement(int UserID, String title, double amount, String detail, String date, String image){
        log.info("Creating new reimbursement");
        Transaction transaction = null;
        Reimbursement newReimbursement = null;

        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            newReimbursement = new Reimbursement(UserID, title, amount, detail, date, "Pending",image);
            session.save(newReimbursement);
            transaction.commit();
            log.info("Reimbursement made");
        } catch(HibernateException e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
            log.debug("Reimbursement not made");
        }
    }
    public void approveReimbursement(int reimbursementID, String accept){
        log.info("Making verdict for reimbursement");
        Reimbursement updateReimbursement = null;
        Transaction transaction = null;

        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            updateReimbursement = session.get(Reimbursement.class, reimbursementID);
            System.out.println(updateReimbursement.toString());
            System.out.println(accept);
            updateReimbursement.setStatus(accept);
            transaction.commit();
            log.info("Verdict made");
        }catch(HibernateException e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
            log.info("Verdict not made");
        }
    }

    public String getEmailByTransactionId(int id) {
        log.info("Getting email");
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Reimbursement reimbursement = session.get(Reimbursement.class, id);
            User user = session.get(User.class, reimbursement.getUser_ID());
            log.info("Email found!");
            return user.getEmail();
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        log.debug("email not found.");
        return "";
    }
}
