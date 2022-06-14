package FeelGoodFund;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class FileData extends FeelGoodFundTracker {

    static Scanner input = new Scanner(System.in);
    static HashMap<String, String> usersAndPasswords = new HashMap<>();

    public static final String filePath = "C:\\Users\\44749\\IdeaProjects\\FGF";  // PLEASE PUT YOUR OWN FILE PATH HERE


    public static void createLoginFile()
    { //Create a file to store redgate emails and passwords
        try {
            File loginInfo = new File(filePath+"\\FGF_loginInformation.txt");

            if (loginInfo.createNewFile())
            {
                System.out.println("New login file has been made");
            } else
            {
                System.out.println("* There is an existing file containing login information *");
            }
        } catch (IOException e)
        {
            System.out.println("File creation unsuccessful.");
            e.printStackTrace();
        }
    }

    public static void createTrackerFile()
    { //Create a file to save records of expenses added to the Feel Good Fund tracker

        try {
            File purchaseInfo = new File(filePath + "\\FGF_trackerExpenses.txt");
            if (purchaseInfo.createNewFile())
            {
                System.out.println("New FGF file has been made");
            } else
            {
                System.out.println("* There is an existing file containing purchase information *\n\n");
            }
        } catch (IOException e)
        {
            System.out.println("File creation unsuccessful.");
            e.printStackTrace();
        }
    }


    public static void addNewUserToFile()
    { //Save the new redgate emails and passwords from displayLogin() in FeelGoodFundTracker class

        BufferedWriter entry = null;

        try
        {
            entry = new BufferedWriter(new FileWriter("FGF_loginInformation.txt"));


            Scanner input = new Scanner(System.in);

            System.out.println("Please enter your name");
            String name = input.next().toLowerCase();
            System.out.println("Please enter your password");
            String password = input.next();
            String email = (name + "@redgate.co.uk");



            usersAndPasswords.put(email, password);

            System.out.println("Your new email is:" + email +
                    "\nPlease go log in with the password you've chosen\n");

            Teams.team.add(new Employee(name, email));

            pauseForUser();


            for (Map.Entry<String, String> line : usersAndPasswords.entrySet())
            {

                entry.write(line.getKey() + "," + line.getValue());

                entry.newLine();
            }
            entry.flush();


        } catch (IOException trace)
        {
            trace.printStackTrace();
        } finally {
            try {
                entry.close();
            } catch (Exception fileError)
            {
                System.out.println("Error closing file");
            }
        }

        displayLogin();
    }


    public static void readFile()
    { //Reads all saved file information so that it can be passed onto the Feel Good Fund Tracker

        try {
            BufferedReader readData = new BufferedReader(new FileReader("FGF_loginInformation.txt"));
            BufferedReader readData2 = new BufferedReader(new FileReader("FGF_trackerExpenses.txt"));
            String line;

            while ((line = readData.readLine()) != null)
            {

                String[] divider = line.split(",", 2);
                if (divider.length >= 2) {
                    String email = divider[0];
                    String password = divider[1];
                    usersAndPasswords.put(email, password);

                    if(!email.contains("manager")){
                        String[] name = email.split("@",2);
                        Teams.team.add(new Employee(name[0], email));
                    }

                } else
                {
                    System.out.println("Invalid entry" + line);
                }
            }

            while ((line = readData2.readLine()) != null)
            {


                try
                {
                    String[] divider = line.split(",", 2);
                    if (divider.length >= 2) {

                        String date = divider[0];
                        Float expense = Float.parseFloat(divider[1]);
                        recordPurchase.put(date, expense);
                    } else {
                        System.out.println("Invalid entry" + line);
                    }
                } catch (NumberFormatException trackerInfo) {
                    System.out.println("Error retrieving tracker data");
                }
            }

            readData.close();
            readData2.close();

        } catch (FileNotFoundException e)
        {
            System.out.println("File does not exist");
        } catch (IOException ex) {
            System.out.println("Could not read line ");
        }
    }


    public static void writeRecords()
    { // This will write all expenses (from the tracker) into the FGF_trackerExpenses.txt file

        BufferedWriter entry = null;

        try {
            entry = new BufferedWriter(new FileWriter("FGF_trackerExpenses.txt"));
            for (Map.Entry<String, Float> line : recordPurchase.entrySet()) {

                entry.write(line.getKey() + "," + line.getValue());

                entry.newLine();
            }
            entry.flush();
        } catch (IOException trace)
        {
            trace.printStackTrace();
        } finally
        {
            try
            {
                entry.close();
            } catch (Exception fileError) {
                System.out.println("Error closing file");
            }
        }
    }

}






