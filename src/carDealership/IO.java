package carDealership;
import java.util.*;
import java.io.*;

public class IO{

    private String fileName;
    private File file;
    private BufferedWriter writer;
    private Scanner scan;
    private ArrayList<Customer> cusList;
    private ArrayList<Car> carList;
    private ArrayList<String[]> pwdList;
    private static IO thisIO;

    public IO(String fileName)
    {
        try
        {
            this.fileName = fileName;
            writer = new BufferedWriter(new FileWriter(fileName, true));
            file = new File(fileName);
            scan = new Scanner(file);
        }

        catch (IOException io)
        {
            System.out.println("Cannot access/create file");
        }   
    }

    private IO()
    {
        loadCredentials("savedInfo/Passwords.txt");
        carList = loadCar("savedInfo/Cars.txt");
        cusList = loadCustomers("savedInfo/Customers.txt");
    }

    public static IO getStarterIO()
    {
        if (thisIO == null)
            thisIO = new IO();
        return thisIO;
    }

    public ArrayList<String[]> getCredentials()
    {
        return pwdList;
    }

    public ArrayList<Car> getCarList() {
        return carList;
    }

    public ArrayList<Customer> getCusList()
    {
        return cusList;
    }


    public BufferedWriter getWriter()
    {
        return writer;
    }
    
    public Scanner getScan()
    {
        return scan;
    }

    public File getFile()
    {
        return file;
    }
    
    public void write(String content) 
    {
        try
        {
            writer.write(content);
            writer.newLine();
            writer.flush();
        }

        catch (IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }

    
    public void delete(String lineToDelete)
    {
        ArrayList<String> newFileInfo = new ArrayList<String>();
        try
        {
            Scanner tempScan = new Scanner(file);
            while (tempScan.hasNext())
            {
                String nextLine = tempScan.nextLine();
                
                if (!nextLine.equals(lineToDelete))
                {
                    newFileInfo.add(nextLine);
                } 
            }
            writer.flush();
            tempScan.close();

            BufferedWriter newWriter = new BufferedWriter(new FileWriter(fileName));
            for (String line: newFileInfo)
            {
                newWriter.write(line);
                newWriter.newLine();
            }    

            newWriter.close();
        }catch(IOException e)
          {}
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

    private void loadCredentials(String passwordFile)
      {
          pwdList = new ArrayList<String[]>();
          Scanner scan;
          try
            {
                scan = new Scanner(new File(passwordFile));
                while(scan.hasNext())
                {
                    String[] info = scan.nextLine().split(",");
                    pwdList.add(info);                    
                }
              scan.close();
            }
            
          catch (IOException ioe)
            {
                System.out.println("File Error");
            }
              
      }


    public ArrayList<Car> loadCar(String cfile)
    {
        Scanner cscan = null;
        ArrayList<Car> clist = new ArrayList<Car>();
        try
        {
            cscan = new Scanner(new File(cfile));
            while(cscan.hasNext())
            {
                String [] nextLine = cscan.nextLine().split(" ");
                int carIDs = Integer.parseInt(nextLine[0]);
                String brands = nextLine[1];
                String models = nextLine[2];
                int years = Integer.parseInt(nextLine[3]);
                String conditions = nextLine[4];
                int stocks = Integer.parseInt(nextLine[5]);
                int prices = Integer.parseInt(nextLine[6]);
                Car car = new Car(carIDs, models, brands, conditions, years, prices, stocks);
                clist.add(car);
            }
            cscan.close();
        }
        catch(IOException e)
        {}
        return clist;
      
    } public ArrayList<Customer> loadCustomers(String fileName)
    {
        Scanner scan = null;
        ArrayList<Customer> customerList= new ArrayList<Customer>();
        try
        {
            File file = new File(fileName);
            scan = new Scanner(file);

            //if(!(file.exists()) && file.createNewFile())
            while (scan.hasNext())
            {
                String[] info = scan.nextLine().split(",");
                String name = info[1];
                String address = info[2];
                int age = Integer.parseInt(info[3]);
                int phoneNo = Integer.parseInt(info[4]);
                String occupation = info[5];
                int carId = Integer.parseInt(info[6]);
                
                Car car = findCar(carId);

                Customer customer = new Customer(name, address, age, phoneNo, occupation, car);
                customerList.add(customer);
                
            }
            
        }
        
        catch (IOException ioe)
        {
            System.out.println("File error");
        }

        catch(NumberFormatException nfe)
        {
            System.out.println("Invalid Number");
        }

        catch (ArrayIndexOutOfBoundsException ae)
        {
            System.out.println("Insufficient details");
        }

        return customerList;
    }
}




