package FeelGoodFund;

public class Employee {


    private String fullName;
    private String email;


    public String getFullName() {
        return fullName;
    }



    Employee(){
    }

    Employee(String fullName, String email){
        this.email = email;
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Name:" + fullName + "| Email:" + email ;
    }
}
