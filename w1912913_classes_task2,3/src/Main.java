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
                case "100":      // Viewing all fuel queues
                case "VRQ":
                    for(int i=0 ; i<quenum ; ++i) {
                        Queue[i].viewque(i);
                    }

                    break;

                case "101":
                case "VER":            // Viewing all empty queues
                    for(int i=0 ; i<quenum ; ++i) {
                        Queue[i].emptyque(i);
                    }
                    break;

                case "102":
                case "ACQ":           // Adding customer to fuel queue
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
                                    System.out.print("All 5 queues has filled.You will return to the waiting queue!!\n");
                                    addToWaitingQue();
                                }

                            }

                        }
                    }

                    stock = Queue[maxque].addCustomer(maxque,stock,income);

                    System.out.println(stock);

                    break;


                case "103":      // Remove a customer from a queue
                case "RCQ":
                    System.out.println("--Remove customer--");
                    System.out.print("Enter que number : ");

                    int que= input.nextInt();
                    que=que-1;

                    stock = Queue[que].remove(que,stock,waitinque,income);
                    System.out.println(stock);
                    break;

                case "104":
                case "PCQ":    // Remove a served customer.
                    System.out.println("--Remove served customer--");
                    System.out.print("Select Que(1,2,3) : ");
                    que=input.nextInt();
                    que=que-1;
                    Queue[que].removeServedCustomer(que,income,waitinque);
                    break;

                case "105":        // Viewing customers names in alphabatical order
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

                    // store customer details  into file
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
                   // load customer details from the file

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


                case "108":      // Viewing fuel stock
                case "STK":System.out.print(stock);
                    break;

                case "109":          // add fuel stock
                case "AFS":stock += addfuelstock();

                    break;

                case "110":         // Viewing income of each fuel queue
                case "IFQ":


                    for(int i=0 ; i<quenum ; ++i) {
                        System.out.println("Que " + (i+1) +" income is :" + income[i]);


                    }


                    break;

                case "999":     // Exit the program
                case "EXT":System.exit(0);
                    System.out.println("program has exited");
                    break;

                default:
                    System.out.println("\nEnter a valid code!!!");
            }
            System.out.print("\nEnter your requirement: ");
            req = input.next();               // Getting user input
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
    public static int addfuelstock(){             //add fuel stock
        System.out.print("Enter new fuel stock : ");
        Scanner input = new Scanner(System.in);
        int newstock = input.nextInt();


        return newstock;

    }
    public static void addToWaitingQue(){          // Waiting queue

        System.out.println("waitng que");         // If all queues are filled customer will adding waiting queue


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


}