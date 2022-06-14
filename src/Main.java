
import FeelGoodFund.*;



public class Main {

    //SID: 2015597  GROUP NAME: Group 4


    public static void main(String[] args) {


        FileData.createLoginFile();  //First the program will create a file for comparing users emails and passwords

        FileData.createTrackerFile(); //Next it will create a file to store expenses stored in the tracker

        FileData.readFile();  //All the said files will then be read

        FeelGoodFundTracker.displayLogin(); //The start of the program is here. The user will log into the Feel Good Fund system

    }
}
