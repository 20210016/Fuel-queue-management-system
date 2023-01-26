package com.example.task4;
import java.io.*;
import java.util.Scanner;

public class Main {


    public static final int passnum=6;
    public static final int quenum=5;
    public static final int waitnum=12;

    public static Scanner input= new Scanner(System.in);
    public static fuelQueue [] Queue = new fuelQueue[quenum];
    public static passenger [] waitinque = new passenger[waitnum];


    public static void main(String[] args) {

        initialise();
        int stock = 6600;
        String req;
        double[] income = new double[quenum];


        System.out.println("""  
                Enter 099 or VGI View Graphical User Interface.     
                Enter 100 or VRQ View all fuel queues
                Enter 101 or VER View all empty queues
                Enter 102 or ACQ Add customer to a queue 
                Enter 103 or RCQ Remove a customer from a queue 
                Enter 104 or PCQ Remove a served customer 
                Enter 105 or VCS View customer stored in a alphabetical order
                Enter 106 or SPD Store program data into file
                Enter 107 or LPD Load program data from file 
                Enter 108 or STK View Remaining fuel stock
                Enter 109 or AFS Add full stock 
                Enter 999 or EXT Exit the program
                """);
        System.out.print("\nEnter your requirement: ");
        req = input.next();

        while(true){

            switch (req) {
                case "100":
                case "VRQ":
                    for(int i=0 ; i<quenum ; ++i) {
                        Queue[i].viewque(i);
                    }

                    break;

                case "099":
                case "VGI": HelloApplication.main(args);
                break;



                case "101":
                case "VER":
                    for(int i=0 ; i<quenum ; ++i) {
                        Queue[i].emptyque(i);
                    }
                    break;


                case "102":
                case "ACQ":
                    int count=0;
                    int max=0;
                    int min=0;
                    int maxque=0;
                    int minCount=0;

                    for(int i=0 ; i<quenum ; ++i) {

                        count = Queue[i].addCustomer();


                        if(max<count){
                            max=count;
                            maxque=i;
                            count=0;
                        }else {
                            min=count;
                            if(min==0){
                                minCount++;
                                if(minCount == quenum) {
                                    System.out.print("All 3 ques has filled.You will return to the waiting que!!\n");
                                    addToWaitingQue();
                                }

                            }

                        }
                    }



                    stock = Queue[maxque].addCustomer(maxque,stock,income);

                    System.out.println(stock);

                    break;


                case "103":
                case "RCQ":
                    System.out.println("--Remove customer--");
                    System.out.print("Enter que number : ");

                    int que= input.nextInt();
                    que=que-1;

                    stock = Queue[que].remove(que,stock,waitinque,income);
                    System.out.println(stock);
                    break;

                case "104":
                case "PCQ":
                    System.out.println("--Remove served customer--");
                    System.out.print("Select Que(1,2,3) : ");
                    que=input.nextInt();
                    que=que-1;
                    Queue[que].removeServedCustomer(que,income,waitinque);
                    break;


                case "105":
                case "VCS":
                    String[] names = new String[quenum*passnum];
                    int j=0;


                    for (int i = 0; i < quenum; i++) {
                        j=j+Queue[i].alphabaticalOrder(names,j);
                    }


                    String temp;

                    for (int i = 0; i < names.length; i++) {
                        for (j = 1; j < ((names.length) - i); j++) {
                            if(names[j]!= "empty" && names[j-1]!= "empty") {
                                names[j] = names[j].substring(0, 1).toUpperCase() + names[j].substring(1);
                                names[j - 1] = names[j - 1].substring(0, 1).toUpperCase() + names[j - 1].substring(1);
                            }
                            if (names[j - 1].charAt(0) > names[j].charAt(0)) {
                                temp = names[j - 1];
                                names[j - 1] = names[j];
                                names[j] = temp;

                            }
                            if (names[j - 1].charAt(0) == names[j].charAt(0)) {
                                if (names[j - 1].charAt(1) > names[j].charAt(1)) {
                                    temp = names[j - 1];
                                    names[j - 1] = names[j];
                                    names[j] = temp;

                                }

                            }

                        }
                    }

                    for (int i = 0; i < names.length; ++i) {
                        if (names[i] != "empty")
                            System.out.print(names[i] + "\n");
                    }

                    break;

                case "106":
                case "SPD":

                    // store program data into file
                    try {
                        BufferedWriter fWrite = new BufferedWriter(new FileWriter("data.txt"));
                        for (int x = 0; x < quenum; x++) {
                            Queue[x].fileWrite(fWrite,x) ;
                            fWrite.write("\n");
                        }
                        fWrite.close();

                    } catch (IOException ex) {
                        System.out.println("An error occurred.");
                        ex.printStackTrace();
                    }

                    break;

                case "107":
                case "LPD":


                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
                        String line = null;
                        try {
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                            }
                            reader.close();
                        } catch (IOException ex) {
                            System.out.println("An error occurred.");
                            ex.printStackTrace();
                        }
                    } catch (FileNotFoundException ex) {
                        System.out.println("file not found.");

                    }

                    break;


                case "108":
                case "STK":System.out.print(stock);
                    break;

                case "109":
                case "AFS":stock += addfuelstock();

                    break;

                case "110":
                case "IFQ":


                    for(int i=0 ; i<quenum ; ++i) {
                        System.out.println("Que " + (i+1) +" income is :" + income[i]);

                	    	   /*
                				income[i] = Queue[i].calIncome();
                				if(finalIncome[i]<income[i] && finalIncome[i]!=0 ) {
                				finalIncome[i]=finalIncome[i]+(finalIncome[i]-income[i]);
                				}if(finalIncome[i]==0) {
                					finalIncome[i] = income[i];
                				}
                				System.out.println("Que " + (i+1) +" income is :" + finalIncome[i]);
                				*/
                    }


                    break;

                case "999":
                case "EXT":System.exit(0);
                    System.out.println("program has exited");
                    break;

                default:
                    System.out.println("\nEnter a valid code!!!");
            }
            System.out.print("\nEnter your requirement: ");
            req = input.next();
        }


    }

    public static void initialise(){
        for (int x = 0; x < quenum; x++) {
            Queue[x] = new fuelQueue();
        }
        for (int x = 0; x < waitnum; x++) {
            waitinque[x] = new passenger();
        }

    }
    public static int addfuelstock(){  //add fuel stock
        System.out.print("Enter new fuel stock : ");
        Scanner input = new Scanner(System.in);
        int newstock = input.nextInt();


        return newstock;

    }
    public static void addToWaitingQue(){

        System.out.println("waitng que");


        for (int y = 0; y < passnum; y++) {

            if (waitinque[y].Vehical_No.equals("empty")){

                System.out.print("\n--waiting-que place " + (y+1) + " empty place-- \n");
                Scanner custumer = new Scanner(System.in);
                System.out.print("Enter customer First name:");
                waitinque[y].First_Name = custumer.next();
                System.out.print("Enter customer Second name:");
                waitinque[y].Second_Name = custumer.next();
                System.out.print("Enter Vehicle number:");
                waitinque[y].Vehical_No = custumer.next();
                System.out.print("Enter No of liters:");
                waitinque[y].No_Of_Liters = custumer.nextInt();

                break;

            }
        }


    }

       /* private static void displayWait() {
        	System.out.println("\n--WaitngQue--\n"  );
	    	for (int  y = 0; y < waitnum; y++)
	    	{
	    		System.out.print("person : " + (y+1) + "\n\n");
	    		System.out.println("First name: " + waitinque[y].First_Name);
	    		System.out.println("Second name: " + waitinque[y].Second_Name);
	    		System.out.println("Vehicle Number:" +  waitinque[y].Vehical_No);
	    		System.out.println("No of liters: " + waitinque[y].No_Of_Liters + "\n");
	    	}

		}
*/




}