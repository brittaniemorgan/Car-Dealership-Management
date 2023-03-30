package carDealership;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class DeleteCar extends JPanel{
   private JLabel enterID;
   private JButton delete;
   private JTextField carID;
   private JLabel warning;
   private CarListing clist;
   private JFrame delframe1;
   private IO carIO;

  //window to delete a car from the car list table
  public DeleteCar(CarListing listing, JFrame delframe1)
        {
            this.clist = listing;
            carIO = clist.getCarIO();
            enterID = new JLabel("Enter the ID number of the car to be deleted:");
            carID = new JTextField(8);
            warning = new JLabel("");
    
            delete = new JButton("Delete");

            delete.addActionListener(new DeleteButtonListener());

            add(enterID);
            add(carID);
            add(delete);
            add(warning);   

            this.delframe1 = delframe1;
  
}


  private class DeleteButtonListener implements ActionListener
        {
          public int findCar(int ID){
          int pos = -1;
          for (Car i: clist.getCarList()){
            if ((i.getCarID())==ID)
                pos= clist.getCarList().indexOf(i);
          }
          return pos;
          }
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int id = Integer.parseInt(carID.getText());
                    if (findCar(id) != -1)
                    {
                        int carpos= findCar(id);  
                        Car car = clist.getCarList().get(carpos);
                        String idStr = Integer.toString(id);
                        String brand = car.getBrand();
                        String model = car.getModel();
                        String year = Integer.toString(car.getYear());
                        String condition = car.getCondition();
                        String stock = Integer.toString(car.getStock());
                        String price = Integer.toString(car.getPrice());
                        String carInfo = car.toString();
                        
                        clist.getModel().setRowCount(0);
                        clist.getCarList().remove(carpos);
                        clist.showTable(clist.getCarList());
                        carIO.delete(carInfo);
                        setVisible(false);
                        delframe1.setVisible(false);
                    }
                }
                catch (NumberFormatException nfe)
                {
                    warning.setText("Enter a vaid ID number");
                }

            }
        }
}

  
  
