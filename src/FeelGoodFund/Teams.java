package FeelGoodFund;




import java.util.ArrayList;
import java.util.List;

import static FeelGoodFund.FileData.input;


public class Teams extends FeelGoodFundTracker {

    private static String teamName = "Software Team";
    static float budget = 500;
    static List<Employee> team = new ArrayList<>();




    // The following methods are actions that only the manager can preform

    //editFGFFunds() allows managers to revise team budgets. This implements flow chart process 11 - edit/revise FGF funds



    public static void editFGFFunds()
    {

        System.out.println("Would you like to increase the team budget?(y/n)\n");
        String budgetChoice = input.next();


        switch(budgetChoice)
        {
            case "y":
                System.out.println("How much money would you like to add to the team budget?\nINCREASE:");
                float increase = input.nextFloat();

            recordPurchase.put(getTimeStamp() + " INCREASE IN BUDGET" ,increase);
            case "n":
                break;
            default:
                System.out.println("Invalid choice. Returning to menu...");
                break;
        }

    }

    public static String getTeamName()
    {
    return teamName;
    }

    public static void setTeamName(String newName)
    {
       teamName = newName;
    }

    //editTeam() implements flow chart process 12 - Manage/Edit teams
    public static void editTeam() {


        int editTeamChoice;
        do {
            System.out.println("\nWould you like to:\n1.Change the team name\n2.Revise FGF Funds");
            editTeamChoice = input.nextInt();
        } while (!(editTeamChoice == 1) && !(editTeamChoice == 2));


        if (editTeamChoice == 1)
        {
            System.out.println("What would you like to change the team name to?");
            String teamName = input.next();
            Teams.setTeamName(teamName);
        } else
        {
            Teams.editFGFFunds();
        }
        managerOptions();
}

  public static void viewAllTeamMembers(){
        int counter = 0;
      System.out.println("TEAM MEMBERS:");
        for(Employee employee: team){


            System.out.println((counter++) + " "+employee.toString() +"\n");
        }
        pauseForUser();
      System.out.println("\nReturning to tracker interface....\n");
        trackerInterface();
  }
}
