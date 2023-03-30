package carDealership;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
public class EditWindow extends JFrame 
{
    
    private IO cusIO;
    private JLabel name, address, age, carIdLabel, carId, phoneNum, occupation, error;

    private JTextField nameText, addressText, ageText, phoneNumText, occupationText;

    private JButton save, clearAll, cancel;
    private JPanel  cmdPanel, editForm;
    private EditWindow thisWindow;
    private ArrayList<Car> carList;

    private Customer updatingCus;
    private CustomerListing cusListing;

    public EditWindow(CustomerListing cusListing, Customer customer)
    {
        carList = cusListing.getCarList(); 
        setLayout(new BorderLayout());
        this.cusListing = cusListing;
        thisWindow = this;
        this.updatingCus = customer;
        
        editForm = new JPanel();
        cmdPanel = new JPanel();
        
        error = new JLabel("");
        name = new JLabel("Name:");
        address = new JLabel("Address:");
        age = new JLabel("Age:");
        carIdLabel = new JLabel("Car ID:");
        carId = new JLabel(Integer.toString(customer.getCarBought().getCarID()));
        phoneNum = new JLabel("Phone Number:");
        occupation = new JLabel("Occupation:");
        
        addressText = new JTextField(20);
        nameText = new JTextField(20);
        ageText = new JTextField(20);
        phoneNumText = new JTextField(20);
        occupationText = new JTextField(20);

        save = new JButton("Save");
        cancel = new JButton("Cancel");
        clearAll = new JButton("Clear All");

        addressText.setText(customer.getAddress());
        nameText.setText(customer.getName());
        //carIdText.setText(Integer.toString(customer.getCarBought().getCarID()));
        ageText.setText(Integer.toString(customer.getAge()));
        phoneNumText.setText(Integer.toString(customer.getPhoneNo()));
        occupationText.setText(customer.getOccupation());
 
        editForm.add(name);
        editForm.add(nameText);
        editForm.add(address);
        editForm.add(addressText);
        editForm.add(age);
        editForm.add(ageText);
        editForm.add(carIdLabel);
        editForm.add(carId);
        editForm.add(phoneNum);
        editForm.add(phoneNumText);
        editForm.add(occupation);
        editForm.add(occupationText);

        editForm.setLayout(new GridLayout(10,10));
       
        cmdPanel.add(save);      
        cmdPanel.add(clearAll);
        cmdPanel.add(cancel);        
        cmdPanel.add(error);

        
        add(editForm, BorderLayout.CENTER);
        add(cmdPanel, BorderLayout.SOUTH);
    

        save.addActionListener(new SaveButtonListener());
        cancel.addActionListener(new cancelButtonListener());
        clearAll.addActionListener(new clearAllButtonListener());

        pack();
        setVisible(true);
    }

    public Car findCar(int carId)
    {
        
        Car retval = null;
        for (Car car : carList)
        {
            if (car.getCarID() == carId)
                retval = car;
        }     

        return retval;
    }

    //saves the information once entered
    private class SaveButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String name = nameText.getText();
            String address = addressText.getText();
            String ageStr = ageText.getText();
            String phoneNumStr = phoneNumText.getText();
            String occupation = occupationText.getText();
            //String newInfo = updatingCus.getCusID() +","+ name +","+ address +","+ ageStr + ","
              //                  + phoneNumStr +","+ occupation +","+ carId;

            if (phoneNumStr.length() < 10)
                error.setText("Please enter a vild phone number. Include the area code");

            if (name.length() >0 && address.length() > 0 && ageStr.length() > 0 && occupation.length() > 0)
                try
                {
                    String[] nameParts = name.split(" ");
                    int age = Integer.parseInt(ageText.getText());
                    int phoneNum = Integer.parseInt(phoneNumStr);

                    if (nameParts.length != 2) 
                    {
                        error.setText("Enter both first and last names");
                    }
                                        
                    else
                    {
                        String oldInfo = updatingCus.toString();
                        updatingCus.updateLocale(name, address, age, phoneNum, occupation, updatingCus.getCarBought());
                        String newInfo = updatingCus.toString();
                        update(oldInfo, newInfo);
                        cusListing.getModel().setRowCount(0);
                        cusListing.showTable(cusListing.getCusList());
                        thisWindow.setVisible(false);
                    }
                }

                catch (NumberFormatException nfe)
                {
                    error.setText("Enter a valid age, phone number or ID");
                }
            }
    } 

    private class cancelButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            thisWindow.setVisible(false);
        }
    }


    private class clearAllButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            nameText.setText("");
            addressText.setText("");
            ageText.setText("");
            phoneNumText.setText("");
            occupationText.setText("");
        }
    }

    public void update(String oldInfo, String newInfo)
    {
        cusIO = cusListing.getCusIO();
        cusIO.delete(oldInfo);
        cusIO.write(newInfo);

    }
}
