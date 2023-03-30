package carDealership;
import java.util.*;

public class Car 
{
  private int carID;
  private String model;
  private String brand;
  private String condition;
  private int year;
  private int price;
  private int stock;

  public Car(int carID, String model, String brand, String condition, int year, int price, int stock){
    this.carID = carID;
    this.model = model;
    this.brand = brand;
    this.condition = condition;
    this.year = year;
    this.price = price;
    this.stock = stock;
    
  }
  //Getter methods
  public int getCarID()
  {
    return carID;
  }

  public String getModel()
  {
    return model;
  }

  public String getBrand()
  {
    return brand;
  }

  public String getCondition()
  {
    return condition;
  }

  public int getYear()
  {
    return year;
  }

  public int getPrice()
  {
    return price;
  }

  public int getStock()
  {
    return stock;
  }

  public boolean canSell()
  {   
      return stock > 0;
  }
  
  public void sellTo(Customer customer)
  {
    if (canSell())
    {
      IO carIO = new IO("savedInfo/Cars.txt");
      String oldInfo = this.toString();
      carIO.delete(oldInfo);
      stock -= 1;
      String newInfo = this.toString();
      carIO.write(newInfo);
    }
  }


    public String toString()
    {
        return (getCarID() + " " + getBrand() + " " + getModel() + " " + getYear() + " " 
        + getCondition() + " " + getStock() + " " + getPrice());
    }    
}

//Used for sorting the price of the car in ascending order
class SortPrice implements Comparator<Car> {
    public int compare(Car c1, Car c2){
        return c1.getPrice() - c2.getPrice();
    }
}

//Used for sorting the brand of the car in ascending order
class SortBrand implements Comparator<Car>{
    public int compare(Car c1, Car c2){
        return c1.getBrand().compareTo(c2.getBrand());
    }
  }
  
