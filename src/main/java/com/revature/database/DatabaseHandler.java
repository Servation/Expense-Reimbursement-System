package com.revature.database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    private static DatabaseHandler dbHandler = null;
    private SessionFactory factory;
    private DatabaseHandler() {}

    public static DatabaseHandler getDbHandler(){
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
        User user = null;
        List<User> userList = listUser();
        for (User users : userList) {
            if (users.getUsername().equals(username) && users.getPassword().equals(password)) {
                user = users;
            }
        }
        return user;
    }

    public List<User> listUser(){
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
        return userList;
    }

    //updating current user's email
    public User updateEmail(int UserID, String newEmail){
        User user;
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            user = session.get(User.class, UserID);
            user.setEmail(newEmail);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            user = null;
        }
        return user;
    }
    //update username
    public User updateUsername(int UserID, String newUsername){
        User user;
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            user = session.get(User.class, UserID);
            user.setUsername(newUsername);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            user = null;
        }
        return user;
    }
    //update password
    public User updatePassword(int UserID, String newPassword){
        User user;
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            user = session.get(User.class, UserID);
            user.setPassword(newPassword);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            user = null;
        }
        return user;
    }

    //getting pending only
    public List<Reimbursement> listPending(){
        List<Reimbursement> currentList = listReimbursement();
        List<Reimbursement> pendingList = new ArrayList<>();

        for(Reimbursement reimbursement: currentList){
            if(reimbursement.getStatus().equals("Pending")) {
                pendingList.add(reimbursement);
            }
        }
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
        return myList;
    }
    //getting full reimbursement list
    public List<Reimbursement> listReimbursement(){
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
        return reimbursementsList;
    }

    public void addingUser(String first_name, String last_name, String email, String type, String username, String password){
        Transaction transaction = null;
        User newUser = null;

        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            newUser = new User(first_name, last_name, email, type, username, password);
            session.save(newUser);
            transaction.commit();
        } catch(HibernateException e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void addingReimbursement(int UserID, String title, double amount, String detail, String date){
        Transaction transaction = null;
        Reimbursement newReimbursement = null;

        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            newReimbursement = new Reimbursement(UserID, title, amount, detail, date, "Pending");
            session.save(newReimbursement);
            transaction.commit();
        } catch(HibernateException e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    public void approveReimbursement(int reimbursementID, String accept){
        Reimbursement updateReimbursement = null;
        Transaction transaction = null;

        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            updateReimbursement = session.get(Reimbursement.class, reimbursementID);
            System.out.println(updateReimbursement.toString());
            System.out.println(accept);
            updateReimbursement.setStatus(accept);
            transaction.commit();
        }catch(HibernateException e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}