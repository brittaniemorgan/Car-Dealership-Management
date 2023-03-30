package carDealership;
import java.util.*;
import java.io.*;

public class SalesRep extends Person
{
    private int numCarsSold, estDiscount;
    private ArrayList<Car> carList; 
    private ArrayList<Customer>  cusList; 
    private String[] serviceWorkers = {"nurse", "doctor", "teacher", "police", "soldier"};
    private static ArrayList<SalesRep> slist;
    private static int count = 1;
    private String id;
    private IO starterIO;
    
    
    public SalesRep(String name, int age, int carsSold)
    {
       super(name, age);
        numCarsSold = carsSold;
        id = "SR" +Integer.toString(100+count);
        count++;
        starterIO = IO.getStarterIO();
        carList = starterIO.getCarList();
        cusList = starterIO.getCusList();
    }

    public String getId()
    {
        return id;
    }

    public int getCarsSold()
    {
        return numCarsSold;
    }

    public static ArrayList<SalesRep> getRepList()
    {
        loadSalesRep(new File("savedInfo/SalesRepresentatives.txt"));
        return slist;
    }
    public ArrayList<Customer> getCusList()
    {
        return cusList;
    }

    public ArrayList<Car> getCarList()
    {
        return carList;
    }
    
    public void sellCar(Car car, Customer customer) 
    {
        car.sellTo(customer);
        numCarsSold ++;
    }

    public int getDiscount(Customer customer) 
    {
        int discount = 0;
        if ((customer.getAge() > 60) && estDiscount < 15)
            discount = 15;
        for (String occupation:serviceWorkers)
        {
            if (customer.getOccupation().toLowerCase().compareTo(occupation) == 0)
                discount = 20;
        } 

        return discount;
    }

        public Car findCar(int id)
    {
        Car car = null;
        for (Car c:carList)
        {
            if(c.getCarID() == id)
                car = c;
        }
        return car;
    }

    public static void loadSalesRep(File sfile)    
    {
        Scanner scan;
        try 
        {
            slist = new ArrayList<SalesRep>();
            scan = new Scanner(sfile);
            while(scan.hasNext())
            {
                String[] info = scan.nextLine().split(",");
                String name = info[0];
                int age = Integer.parseInt(info[1]);
                int numCarsSold = Integer.parseInt(info[2]);
                SalesRep rep = new SalesRep(name, age, numCarsSold);
                slist.add(rep);
            }
        }

        catch(IOException ioe)
        {
            System.out.println("File Error");
        }

        catch(NumberFormatException nfe)
        {
            System.out.println("Invalid number for age or number of cars sold");
        }

        catch (ArrayIndexOutOfBoundsException ae)
        {
            System.out.println("Insufficient details");
        }
    }
}