import java.io.BufferedWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class fuelQueue {


    public static final int quenum = 5;
    public static final int passnum = 6;
    public static final int waitnum = 12;
    private passenger[] passenger = new passenger[passnum];
    private double oneLiterPrice = 430.00;

    fuelQueue() {
        for (int i = 0; i < passnum; i++) {
            passenger[i] = new passenger();
        }

    }

    public int addCustomer() {  //add customer to a queue
        int count = 0, x;
        int max = 0;
        int maxque = 2;

        for (x = 0; x < quenum; x++) {

            if (passenger[x].Vehical_No.equals("empty")) {
                count++;

            }

        }
        return count;

    }

    public int addCustomer(int maxque, int stock, double income[]) {      // Adding a customers to fuel queues

        for (int y = 0; y < passnum; y++) {

            if (passenger[y].Vehical_No.equals("empty")) {

                System.out.print("\n--que " + (maxque + 1) + " has " + (y + 1) + " empty place-- \n");
                Scanner custumer = new Scanner(System.in);
                System.out.print("Enter customer First name:");
                passenger[y].First_Name = custumer.next();
                System.out.print("Enter customer Second name:");
                passenger[y].Second_Name = custumer.next();
                System.out.print("Enter Vehicle number:");
                passenger[y].Vehical_No = custumer.next();
                try {
                    System.out.print("Enter No of liters:");
                    passenger[y].No_Of_Liters = custumer.nextInt();
                }
                catch(InputMismatchException ex) {          // Show error message customer not enter an integer
                    System.out.println("This is not a valid input. Please enter integer value.");

                }
                calIncome(maxque, income, y);

            stock = stock - passenger[y].No_Of_Liters;       // show a warring message if stock below 50
            if (stock < 50) {
                System.out.print("Stock is below 500 liters. Your required liters should be below 50:");
            }
            break;

        }
    }

        return stock;
}

    public void viewque(int num) {   // view all fuel queues

        System.out.println("\nQue : " + (num+1) );
        for ( int y = 0; y < passnum; y++)
        {
            System.out.print("person : " + (y+1) + "\n\n");
            System.out.println("First name: " + passenger[y].First_Name);
            System.out.println("Second name: " + passenger[y].Second_Name);
            System.out.println("Vehicle Number:" +  passenger[y].Vehical_No);
            System.out.println("No of liters: " + passenger[y].No_Of_Liters + "\n");
        }

    }

    public void emptyque(int que) {   //view all empty queues

        for (int y = 0; y < passnum; y++) {

            if (passenger[y].Vehical_No.equals("empty")){
                System.out.print("\nQue : " + (que+1) );
                System.out.print("  place : " + (y+1) + " is Empty\n");


            }
        }

    }
    public int remove(int que,int stock,passenger[] waitinque,double [] income) {  //remove a customer from a queue
        int liters;
                                                                // Adding a customer for fuel queue from the waiting queue
        Scanner input = new Scanner(System.in);
        System.out.print("\nEnter place number : ");

        int place=input.nextInt();
        income[que] = income[que]-oneLiterPrice*passenger[place-1].No_Of_Liters;

        liters= passenger[place-1].No_Of_Liters;
        passenger[place-1].First_Name= "empty";
        passenger[place-1].Second_Name= "empty";
        passenger[place-1].Vehical_No= "empty";
        passenger[place-1].No_Of_Liters= 0;


        for (int y = place-1; y < passnum; y++) {
            if(y==passnum-1){
                passenger[y].First_Name = waitinque[0].First_Name;
                passenger[y].Second_Name = waitinque[0].Second_Name;
                passenger[y].Vehical_No = waitinque[0].Vehical_No;
                passenger[y].No_Of_Liters = waitinque[0].No_Of_Liters;
                calIncome(que,income,y);


                for(int x=0 ; x < waitnum-1 ; ++x) {
                    waitinque[x].First_Name = waitinque[x + 1].First_Name;
                    waitinque[x].Second_Name = waitinque[x + 1].Second_Name;
                    waitinque[x].Vehical_No = waitinque[x + 1].Vehical_No;
                    waitinque[x].No_Of_Liters = waitinque[x + 1].No_Of_Liters;
                }

            }
            else {
                passenger[y].First_Name = passenger[y + 1].First_Name;
                passenger[y].Second_Name = passenger[y + 1].Second_Name;
                passenger[y].Vehical_No = passenger[y + 1].Vehical_No;
                passenger[y].No_Of_Liters = passenger[y + 1].No_Of_Liters;
            }
        }

        return stock+liters;          // If customer will removing , automaticaly adding that customers required liters again to stock

    }

    public void removeServedCustomer(int que ,double[] income,passenger[] waitinque) {  //remove a served customer
// Remove a served customer from the fuel queue.
// Removing customer from the first place. This fuel management system created gets the fuel first in first customer in the queue.

        for (int y = 0; y < passnum; y++) {
            if(y==passnum-1){
                passenger[y].First_Name = waitinque[0].First_Name;
                passenger[y].Second_Name = waitinque[0].Second_Name;
                passenger[y].Vehical_No = waitinque[0].Vehical_No;
                passenger[y].No_Of_Liters = waitinque[0].No_Of_Liters;

                calIncome(que,income,y);


                for(int x=0 ; x < waitnum-1 ; ++x) {
                    waitinque[x].First_Name = waitinque[x + 1].First_Name;
                    waitinque[x].Second_Name = waitinque[x + 1].Second_Name;
                    waitinque[x].Vehical_No = waitinque[x + 1].Vehical_No;
                    waitinque[x].No_Of_Liters = waitinque[x + 1].No_Of_Liters;
                }

            }
            else {
                passenger[y].First_Name = passenger[y + 1].First_Name;
                passenger[y].Second_Name = passenger[y + 1].Second_Name;
                passenger[y].Vehical_No = passenger[y + 1].Vehical_No;
                passenger[y].No_Of_Liters = passenger[y + 1].No_Of_Liters;
            }
        }

    }
    public void calIncome(int maxque,double income[],int y) {
   // Calculate income of each queue


        income[maxque] += oneLiterPrice*passenger[y].No_Of_Liters;

    }


    public int alphabaticalOrder(String names[],int j) {

        for (int k = 0; k < passnum; k++) {
            if (!passenger[k].Vehical_No.equals("empty")) {
                names[j] = passenger[k].First_Name;
                j++;
            } else {

                j++;
            }

        }

        return j;
    }
    public void fileWrite (BufferedWriter fWrite , int x) {    // Store data for text file
        String fname;
        String Second_Name;
        String Vehical_No;
        int No_Of_Liters;
        try {
            for (int y = 0; y < passnum; y++) {
                fname = passenger[y].First_Name;
                Second_Name=passenger[y].Second_Name;
                Vehical_No=passenger[y].Vehical_No;
                No_Of_Liters=passenger[y].No_Of_Liters;
                fWrite.write("Queue : " + (x+1) + "\tFirst Name : " + fname + "\tSecond Name : " + Second_Name + "\tVehival No : " + Vehical_No + "\tNo Of Liters : " + No_Of_Liters + "\n");
            }
        }
        catch(IOException ex){
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
    }



}