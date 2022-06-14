package FeelGoodFund;


import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Calendar;

import static java.lang.System.exit;
import static FeelGoodFund.FileData.input;




public class FeelGoodFundTracker {

   static HashMap<String, Float> recordPurchase = new HashMap<>();
   static HashMap<String, String> events = new HashMap<>();

   static boolean manager = false;
   static String enteredUsername;



   // createNewEvent() and displayEvents() implement flow chart process 10 - adding a new event

   public static void createNewEvent(){

       input.nextLine(); //Clear buffer

       System.out.println("Please enter the name of your event:");
       String eventName = input.nextLine();
       System.out.println("\nPlease add the description of your event");
       String eventDescription = input.nextLine();

       events.put(eventName,eventDescription);

   }

   public static void displayEvents()
   {
       System.out.println("--------------TEAM EVENTS-----------------");
       if (events.isEmpty()){
           System.out.println("Your team currently has no events running.");
       }
       for (Map.Entry<String, String> event : events.entrySet()) {
           System.out.println("\n" + event.getKey() + "\ndescription:" + event.getValue() +"\n");
           for(int i = 0; i<40 ; i++){
               System.out.print("-");
           }
       }


   }

    //Prevent screens from flying too fast to increase usability
    public static void pauseForUser()
    {
        System.out.println("Press enter to continue");
        try
        {
            System.in.read();
        }
        catch(Exception ignored)
        {

        }
    }



    /*
    getTimeStamp() and addNewExpense() help to record and view history of expenses
    implements flow chart process 9 - add a new expense

    viewAllExpenses() is used to view the FGF balance
    implements flow chart process 8 - view the FGF balance
    */

    public static String getTimeStamp ()
    {
        Date time = Calendar.getInstance().getTime();
        return time.toString();
    }

    public static void viewAllExpenses()
    {

        int counter = 1;
        float totalSpent = 0.0f, increase = 0.0f;
        float budgetRemaining;


        if (recordPurchase.isEmpty())
        {
            System.out.println("There are no expenses made by this team");
        } else {
            for (Map.Entry<String, Float> purchase : recordPurchase.entrySet())
            {
                System.out.println("Index:" + counter + "|| Date:" + purchase.getKey() + "|| Amount:" + purchase.getValue() + "GBP");
                counter++;

                if (purchase.getKey().contains("INCREASE"))
                {
                    increase += purchase.getValue();

                }else
                {
                    totalSpent += purchase.getValue();
                }

            }
            budgetRemaining = Teams.budget - totalSpent;
            System.out.println("\n\nTotal spent:" + totalSpent);
            System.out.println("Budget remaining:" + (budgetRemaining+increase));

        }
    }
    public static void addNewExpense()
    {
        float expense = 0.0f;

        do {
            input.nextLine(); // empty buffer from any invalid input this can turn into an endless while loop

            System.out.println("Please enter the amount spent:");
            try {
                expense = Math.abs(input.nextFloat());  // no negative input
            } catch (InputMismatchException notAFloat) {
                System.out.println("A valid number has not been entered.");


            }

        } while (expense <= 0 ); //only allow numbers bigger than

        if (expense <= Teams.budget)
        {
            recordPurchase.put(getTimeStamp(), expense);

            System.out.println("Expense added! Returning to the Feel Good Fund tracker...");
        } else {
            System.out.println("Could not add expense. Value is larger than remaining budget");
        }
        trackerInterface();

    }



    //managerOptions() displays the manager page. Implements flow chart process 6 - display manager page
    public static void managerOptions()
    {

        String choice2;
        do{
            System.out.println
                   ("""
                    Status: Manager
                    1. View the FGF balance
                    2. Edit team
                    3. Log out
                    CHOICE:
                    """);

            choice2 = input.next();
            switch (choice2)
            {
                case "1" -> trackerInterface();  /*renamed viewBalance from report as the tracker has a list of functionalities
                                                  rather than just the ability to view a balance.*/
                case "2" -> Teams.editTeam();

                case "3" -> //reset manager access
                {
                    manager = false;
                    displayLogin();
                }
                default -> System.out.println("Incorrect input, please choose one of the options to proceed");
            }
        }while(choice2.equals("4"));



    }

    public static void trackerInterface()
    { //Display options you can do within the FGF tracker.Please view the individual methods to see their relevance


        viewAllExpenses();
        pauseForUser();

        System.out.println("\nWould you like to:\n1. Add a new expense\n2. Create new event\n3. View all team members\n4. Return to menu");

        String choice3;
        do{

                choice3 = input.next();

            switch (choice3) {

                case "1":
                    addNewExpense();
                    break;
                case "2":
                    createNewEvent();
                    break;
                case "3":
                     Teams.viewAllTeamMembers();
                     break;

                case "4":
                    if (manager){
                        managerOptions();
                    }else{
                        employeeOptions();
                    }
                    //exit
                    break;
                default:
                    System.out.println("Incorrect input, please choose one of the options to proceed");
                    break;
            }

        }while(!choice3.equals("4"));

    }

  /*employeeOptions() shows the team member/leader homepage.
    implements flow chart process 5 - show member/leader page */

    public static void employeeOptions()
    {

        String choice;
        do{
            System.out.println("Status: Team member/leader\nTeam Name:" + Teams.getTeamName());

            displayEvents();

            System.out.println
                   ("""

                    1. View FGF Tracker
                    2. Log out
                    Please choose a choice from the menu
                    CHOICE:
                    """);


            choice = input.next();
            switch (choice) {
                case "1" -> trackerInterface();
                case "2" ->
                {
                    FileData.writeRecords();
                    displayLogin();
                }
                default -> System.out.println("Incorrect input, please choose one of the options to proceed");
            }
        }while(!choice.equals("2"));

    }


    /* Homepage() is for activating flow chart processes 5 and 6 by
    following flow chart decision 4 -  check if user is a member/leader or manager */


    public static void Homepage ()
    {
        if (manager){
            managerOptions();

        }else{
            employeeOptions();
        }
    }

    /* temporaryLoginGenerator() is used to simulate the 'Forgot Password?' action
     related to:
     flow chart process 3 - give a new password via email
     flow chart decision 2 - I forgot the password.*/

    public static void temporaryLoginGenerator()
    {

        System.out.println("Enter '1' to generate a temporary password, Enter '2' to generate manager credentials");


        String temporaryPassword = "No password has been set";
        String email = "No email has been set";
        String choice = input.next();

        if (choice.equals("1"))
        {
             /*The method will temporarily overwrite the previous password.
             Very IMPORTANT words for the user to pay attention to are in capitals.*/

            System.out.println("Please enter your FULL redgate email and we will generate a temporary password");

            email = input.next();
            if(FileData.usersAndPasswords.containsKey(email))
            { //Makes sure temporary passwords are only given to existing emails

                temporaryPassword = "member" + Math.floor(Math.random() * 9000);


            }else
            {

                System.out.println("This email does not exist within the database. Please create a new user");
                pauseForUser();
                return;

            }

        } else if (choice.equals("2"))
        { //To log in as a manager


            email = "manager@redgate.co.uk";
            temporaryPassword = "manager_" + Math.floor(Math.random() * 9000);

        }else
        {
            System.out.println("You have entered an invalid option. Returning to menu...");
            return;
        }

        FileData.usersAndPasswords.put(email,temporaryPassword);


        String[] user = email.split("@",2);

        String emailLabel = "Redgate email: "+ email;
        String userLabel = "User Identified: " + user[0];
        String tempPassLabel = "NEW TEMPORARY PASSWORD: " + temporaryPassword;


       /*like said in the report, this password would have been sent to your email
       ,however, with the knowledge gained from this module, I am unable to do that.
       Instead, the password will be displayed whilst the program is running*/


        System.out.printf
                        ("""
                        
                        MATCH FOUND
                        
                        |%-60s|
                        |%-60s|
                        |%-60s|

                        """
                        ,userLabel,emailLabel,tempPassLabel );

       //As a lot of information will be displayed to the user here, padding is added for readability

        pauseForUser();

    }

       /*the displayLogin() method will log users into the system.
       This implements:
       flow chart process 1 and 4 - enter credentials, get the role of the user
       flow chart decision 1 and 3 - are credentials right? */

    public static void displayLogin()
    {


        boolean loginSuccessful = false;
        do {
            System.out.println
                    ("""
                    Welcome to Redgate's Feel Good Fund!
                    1. Login
                    2. Forgotten credentials
                    3. Create new user
                    4. Exit
                    Please choose an option from the above choices
                    CHOICE:
                    """);


            String choice = input.next().toLowerCase();

            switch (choice) {
                case "1", "login" ->
                {
                    System.out.println("EMAIL:");
                    enteredUsername = input.next();

                    System.out.println("PASSWORD:");
                    String enteredPassword = input.next();

                    if (FileData.usersAndPasswords.containsKey(enteredUsername))
                    {

                        if (FileData.usersAndPasswords.get(enteredUsername).equals(enteredPassword))
                        {
                            System.out.println("Log in successful...");

                            /*
                            manager emails will include the word 'manager' in their name
                            e.g. yournamemanager@redgate.co.uk or manager@redgate.co.uk
                             */

                            manager = enteredUsername.contains("manager");
                            loginSuccessful = true;
                            pauseForUser();
                            Homepage();


                        }

                    } else
                    {
                        System.out.println("Incorrect username or password");
                    }
                }
                case "2", "forgotten credentials", "fc" -> temporaryLoginGenerator();

                case "3", "create new user" -> FileData.addNewUserToFile();

                case "4", "exit" ->
                {
                    System.out.println("Thank you for using the Feel Good Fund Tracker, goodbye!");
                    exit(0);

                }

                default -> System.out.println("Invalid option, try again");
            }
        } while (!loginSuccessful);

    }

}
