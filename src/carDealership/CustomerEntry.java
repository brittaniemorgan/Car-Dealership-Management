package carDealership;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CustomerEntry extends JFrame{

    private JTextField  strName;      
    private JTextField  strAge;        
    private JTextField  strPhoneNo; 
    private JTextField  strOccupation; 
    private JTextField  strAddress; 
    private JTextField  strCar; 

    private JLabel warning;
  
    private JButton     cmdSave;
    private JButton     cmdClose;
    private JButton     cmdClearAll;

    private JPanel      command;
    private JPanel      display;

    private CustomerEntry cusEntry;
    private CustomerListing cusListing;
    private SalesRep salesRep;
    
    private IO cusIO;


    //screen so that customers' information can be added
    public CustomerEntry(CustomerListing cuslisting)
    {
        
        setTitle("Entering new customer");
        setPreferredSize(new Dimension(600,400));
        command = new JPanel();
        display = new JPanel();
      
        display.add(new JLabel("Name:")); 
        strName = new JTextField(10);
        display.add(strName);
      
        display.add(new JLabel("Age:"));
        strAge = new JTextField(3);
        display.add(strAge);   
      
        display.add(new JLabel("Address:"));
        strAddress = new JTextField(20);
        display.add(strAddress);
      
        display.add(new JLabel("Phone No:"));
        strPhoneNo = new JTextField(10);
        display.add(strPhoneNo);
      
        display.add(new JLabel("Occupation"));
        strOccupation = new JTextField(10);
        display.add(strOccupation);
      
        display.add(new JLabel("Car ID:"));
        strCar = new JTextField(6);
        display.add(strCar);

        warning = new JLabel("");
        warning.setForeground(Color.RED);

        display.setLayout(new GridLayout(10,10));
       
        cmdSave = new JButton("Save");
        cmdClose = new JButton("Close");
        cmdClearAll = new JButton("Clear All");

        cmdSave.addActionListener(new SaveButtonListener());
        cmdClose.addActionListener(new CloseButtonListener());
        cmdClearAll.addActionListener(new clearAllButtonListener());


        command.add(cmdSave);
        command.add(cmdClose);
        command.add(cmdClearAll);
        command.add(warning);
      
        add(display, BorderLayout.CENTER);
        add(command, BorderLayout.SOUTH);
        pack();
        setVisible(true);


        this.cusListing =  cuslisting;
        salesRep = cusListing.getSalesRep();
        cusEntry = this;
        cusIO = cusListing.getCusIO();
    }

    
    private class CloseButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

           cusEntry.setVisible(false);

        }

    }

    private class SaveButtonListener implements ActionListener
    {
        ArrayList<Customer> cusList;
        public void actionPerformed(ActionEvent e)
        {
            cusList = cusListing.getCusList();
            String[] name = strName.getText().split(" ");
            String address = strAddress.getText();
            int carID = Integer.parseInt(strCar.getText());
            Car car = salesRep.findCar(carID);

            if (name.length < 1 && address.length() < 1 && strAge.getText().length() < 1 && strPhoneNo.getText().length() < 1
                    && strCar.getText().length() < 1 && strOccupation.getText().length() < 1)
                    
                warning.setText("Complete all required fields");

            else if (name.length != 2)
                warning.setText("Enter First and Last Names");
                    
            else if (strPhoneNo.getText().length() < 7)
                warning.setText("Enter a valid phone number");

            else if (!(isInt(strAge.getText())) && !(isInt(strPhoneNo.getText())) && !(isInt(strCar.getText())))
                warning.setText("Enter a valid number");

            else if(car == null)
                warning.setText("Invalid Car ID");

            else if(car.canSell())
            {
                warning.setText("");                       
                       
               Customer cus = new Customer(strName.getText(), strAddress.getText(), Integer.parseInt(strAge.getText()),
                                               Integer.parseInt(strPhoneNo.getText()), strOccupation.getText(), car);

                salesRep.sellCar(car, cus);
                cusIO.write(cus.toString());
                cusList.add(cus);
                cusListing.addToTable(cus, cusList);
                cusEntry.setVisible(false);
                                              
            }

            else
            {
                warning.setText("Car out of Stock");
            }
        }
            
    }


     //ensures the value entered for number is an integer 
     public boolean isInt(String num) {
        try {
            Integer.parseInt(num);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

  //when this button is clicked it clears the textboxes 
  private class clearAllButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {;
            strName.setText("");
            strOccupation.setText("");
            strPhoneNo.setText("");
            strAge.setText("");
            strAddress.setText("");
            strCar.setText("");
        }
    }


}



  