public class passenger {
    public String First_Name;
    public String Second_Name;
    public String Vehical_No;
    public int No_Of_Liters;

    passenger(){
        First_Name = "empty";
        Second_Name = "empty";
        Vehical_No = "empty";
        No_Of_Liters = 0;

    }

    public passenger(String Vehical_No ,int literCount, String fName, String sName){
        this.Vehical_No=Vehical_No;
        this.No_Of_Liters = literCount;
        this.First_Name = fName;
        this.Second_Name = sName;
    }
}