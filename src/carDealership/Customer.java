package carDealership;
public class Customer extends Person{
    private Car carBought;
    private String occupation;
    private int phoneNo;
    private int age;
    private String address;
    private String cusID;
    private static int count = 1000;

    //Constructor
  public Customer(String name, String address, int age, int phoneNo, String occupation, Car carBought){

    super(name, age);
    this.address = address;
    this.carBought = carBought;
    this.occupation = occupation;
    this.phoneNo = phoneNo;
    this.age = age;  
    this.cusID = "C" + count;
    count++;
  
  }

    //Accessors
  public Car getCarBought(){
    return carBought;
  }

  public String getOccupation(){
    return occupation;
  }

  public int getPhoneNo(){
    return phoneNo;
  }

  public int getAge(){
    return age;
  }

  public String getAddress(){
    return address;
  }

  public String getCusID(){
    return cusID;
  }
  
public void updateLocale(String name, String address, int age, int phoneNo, String occupation, Car carBought)
{
  this.name = name;
  this.address = address;
  this.age = age;
  this.phoneNo = phoneNo;
  this.occupation = occupation;
  this.carBought = carBought;
}

  public String toString(){
    return getCusID() + "," + getName() + "," + getAddress() + "," + getAge() 
            + "," + getPhoneNo() + "," + getOccupation() + "," + getCarBought().getCarID();
  }
}