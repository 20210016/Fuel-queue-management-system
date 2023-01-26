import java.io.*;
import java.util.Scanner;

public class arrayVersion {
    private static final int rows=3;
    private static final int cols=6;
    static Scanner input= new Scanner(System.in);
    public static void main(String[] args) {
        String[][] queues = new String[rows][cols];
        int stock = 6600;
        String req;

        for (int x = 0; x < rows; x++) {

            for (int y = 0; y < cols; y++) {

                    queues[x][y] = "";

            }

        }
        // initialize(queues);

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

        while (!((req.equals("999"))||(req.equals("EXT")))){

            switch (req) {
                case "100":
                case "VRQ":
                    viewque(queues);
                    break;

                case "101":
                case "VER":
                    emptyque(queues);
                    break;

                case "102":
                case "ACQ":
                    stock=addCustomer(queues,stock);
                    break;

                case "103":
                case "RCQ":
                    stock=remove(queues,stock);
                    break;

                case "104":
                case "PCQ":
                    removeServedCustomer(queues);
                    break;

                case "105":
                case "VCS":
                    alphabaticalOrder(queues);
                    break;

                case "106":
                case "SPD":
                    fileWrite(queues);
                    break;

                case "107":
                case "LPD":
                    fileRead();
                    break;

                case "108":
                case "STK":System.out.print(stock);
                    break;

                case "109":
                case "AFS":stock+=addfuelstock();

                    break;

                case "999":
                case "EXT":  // exit the program
                    break;
                default:
                    System.out.println("\nEnter a valid code!!!");
            }
            System.out.print("\nEnter your requirement: ");
            req = input.next();
        }
    }
    static void viewque(String[][]queues) {   // view all fuel queues

        for ( int x = 0; x < rows; x++)
        {
            System.out.println("\nQue : " + (x+1) );
            for ( int y = 0; y < cols; y++)
            {
                System.out.print("person : " + (y+1) + "\t");
                System.out.println(queues[x][y] );
            }

        }
    }

    static void emptyque(String[][]queues) {   //view all empty queues

        for (int x = 0; x < rows; x++) {

            for (int y = 0; y < cols; y++) {

                if (queues[x][y] == ""){
                    System.out.print("\nQue : " + (x+1) );
                    System.out.print("  place : " + (y+1) + " is Empty");


                }
            }

        }
    }
    static int addCustomer(String[][]queues ,int stock ) {  //add customer to a queue
        int count=0,x;
        int max=0;
        int maxque=2;

        for (x = 0; x < rows; x++) {

            for (int y = 0; y < cols; y++) {

                if (queues[x][y] == ""){
                    count++;
                }
            }
            if(max<count){
                max=count;
                maxque=x;
            }
            if(max==0){
                System.out.print("All 3 ques has filled ");
                return -1;

            }
            count=0;

        }
        for (int y = 0; y < cols; y++) {

            if (queues[maxque][y] == ""){
                System.out.print("\n--que " + (maxque+1)+  " has " + (y+1) + " empty place-- \n");
                System.out.print("Enter customer name:");
                Scanner custumer = new Scanner(System.in);
                queues[maxque][y] = custumer.next();
                stock -= 10;
                if(stock<500){
                    System.out.print("Stock is below 500 liters!!!:");
                }
                break;
            }
        }


        return stock;

    }

    static int remove(String[][]queues,int stock) {  //remove a customer from a queue

        System.out.println("--Remove customer--");
        System.out.print("Enter que number : ");
        int que=input.nextInt();
        System.out.print("\nEnter place number : ");
        int place=input.nextInt();
        queues[que-1][place-1] = "";
        return stock += 10;

    }

    static int addfuelstock(){  //add fuel stock
        System.out.print("Enter new fuel stock : ");
        int newstock = input.nextInt();


        return newstock;

    }
    static void removeServedCustomer(String[][]queues) {  //remove a served customer

        System.out.println("--Remove served customer--");
        System.out.print("Select Que(1,2,3) : ");
        int que=input.nextInt();



        for (int y = 0; y < cols; y++) {
            if(y==5){
                queues[que-1][y] = "";
            }
            else {
                queues[que - 1][y] = queues[que - 1][y + 1];
            }
        }

    }
    static void alphabaticalOrder(String[][]queues) {  // view customers names in alphabetical order

        String[] names = new String[18];
        int j = 0;

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < cols; k++) {
                if (queues[i][k] != "") {
                    names[j] = queues[i][k];
                    j++;
                } else {
                    names[j] = "empty";

                    j++;
                }
            }
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
    }

        static void fileWrite (String[][]queues){  // store program data into file
            try {
                BufferedWriter fWrite = new BufferedWriter(new FileWriter("data.txt"));
                for (int x = 0; x < rows; x++) {
                    for (int y = 0; y < cols; y++) {
                        fWrite.write("Queue : " + (x+1) + "\tName : " + queues[x][y] + "\n");
                    }
                    fWrite.write("\n");
                }
                fWrite.close();

            } catch (IOException ex) {
                System.out.println("An error occurred.");
                ex.printStackTrace();
            }
        }


     static void fileRead() {  // load program data from file
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
    }


}






