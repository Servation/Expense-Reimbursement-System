package com.revature.database;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TestMain {


    public static void main(String[] args){
        DatabaseHandler dbHandler = DatabaseHandler.getDbHandler();
        User user = dbHandler.getUser("tl123", "admin");
        boolean run = true;
        Scanner scanner = new Scanner(System.in);
        int input;
        if(user != null){
            while(run){
                System.out.println("0: Quit");
                System.out.println("1: Get your detail");
                System.out.println("2: View your reimbursement");
                System.out.println("3: Update your email");
                System.out.println("4: Update your username");
                System.out.println("5: Update your password");
                System.out.println("6: View reimbursement pending");
                System.out.println("7: Approve reimbursement");
                System.out.println("8: View all employee");
                System.out.println("------------------------\n");
                System.out.print("Enter your input: ");
                input = getNumber();
                switch (input){
                    case 1 -> System.out.println(user);
                    case 2-> {
                        System.out.println("Viewing your reimbursement!");
                        List<Reimbursement> reimbursementList = dbHandler.listUserReimbursement(user.getUser_id());
                        reimbursementList.forEach(System.out::println);
                    }
                    case 3 ->{
                        String newEmail;
                        System.out.print("Enter new email for your account, enter (exit) to stop: ");
                        newEmail = scanner.next();
                        if(newEmail.equalsIgnoreCase("exit")){
                            System.out.println("Going back to previous screen!");
                        }
                        else{
                            user = dbHandler.updateEmail(user.getUser_id(), newEmail);
                        }
                    }
                    case 4 -> {
                        String newUsername;
                        System.out.print("Enter new email for your account, enter (exit) to stop: ");
                        newUsername = scanner.next();
                        if(newUsername.equalsIgnoreCase("exit")){
                            System.out.println("Going back to previous screen!");
                        }
                        else{
                            user = dbHandler.updateUsername(user.getUser_id(), newUsername);
                        }
                    }
                    case 5 -> {
                        String newPassword;
                        System.out.print("Enter new email for your account, enter (exit) to stop: ");
                        newPassword = scanner.next();
                        if(newPassword.equalsIgnoreCase("exit")){
                            System.out.println("Going back to previous screen!");
                        }
                        else{
                            user = dbHandler.updatePassword(user.getUser_id(), newPassword);
                        }
                    }
                    case 6 -> {
                        System.out.println("Viewing pending reimbursements!");
                        List<Reimbursement> pendingList = dbHandler.listPending();
                        if(pendingList.size() != 0) {
                            pendingList.forEach(System.out::println);
                        }
                        else{
                            System.out.println("There is no pending reimbursement! ");
                        }
                    }
                    case 7 -> {
                        System.out.print("Which user's reimbursement do you want to see: ");
                        input = getNumber();
                        List<Reimbursement> userReimbursement = dbHandler.listUserReimbursement(input);
                        if(userReimbursement.size() != 0){
                            userReimbursement.forEach(System.out::println);
                        }
                        else {
                            System.out.println("This user does not exist or do not have any reimbursement");
                        }
                    }
                    case 8 -> {
                        System.out.println("Viewing all user!");
                        List<User> userList = dbHandler.listUser();
                        userList.forEach(System.out::println);
                    }
                    case 0 -> {
                        run = false;
                        System.out.println("Exit");
                    }
                    default -> System.out.println("Please enter a valid command");
                }
            }
        }
    }

    public static int getNumber(){
        Scanner scanner = new Scanner(System.in);
        int number;
        try{
            number = scanner.nextInt();
        } catch (InputMismatchException e){
            number = 0;
        }
        return number;
    }

}
