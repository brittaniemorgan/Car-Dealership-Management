package carDealership;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class CustomerListing extends JFrame {

    private SalesRep salesRep;
    private IO cusIO;
    private ArrayList<Customer> cusList; 
    private ArrayList<Car> carList;
    private ArrayList<Customer> cusEligibleForDiscount;

    private CustomerListing thisListing;

    private JButton sortByName, discountEligible, edit, sellCar, delete, viewMenu;
    private JButton sortByAge;

    private JPanel cmdPanel;
    private JPanel displayPanel; 

    private JTable customerTable;
    private DefaultTableModel model;
    private JScrollPane scrollPane;

    private JLabel customerId;

    private JTextField customerIdText;

    public CustomerListing(SalesRep salesRep) 
    {
    //shows a list of all customers          
        cusIO = new IO("savedInfo/Customers.txt");
        this.salesRep = salesRep; 
        thisListing = this;
        cusList = salesRep.getCusList();
        cusEligibleForDiscount = new ArrayList<Customer>();
        carList = salesRep.getCarList();
        
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        cmdPanel = new JPanel();
        displayPanel = new JPanel(); 

        displayPanel.setLayout(new GridLayout(3,0));

        String[] columnNames = {"ID", "Name", "Age", "Occupation","Phone Number","Address", "ID of Car Bought", "Discount"};
        model = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(model);

        customerTable.getColumnModel().getColumn(0).setPreferredWidth(150);       
        customerTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        customerTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        customerTable.getColumnModel().getColumn(3).setPreferredWidth(500);
        customerTable.getColumnModel().getColumn(4).setPreferredWidth(300);
        customerTable.getColumnModel().getColumn(5).setPreferredWidth(500);
        customerTable.getColumnModel().getColumn(6).setPreferredWidth(300);    
        customerTable.getColumnModel().getColumn(7).setPreferredWidth(200);    
      
        scrollPane = new JScrollPane(customerTable);
        displayPanel.add(scrollPane);

        sortByName = new JButton("Sort by Name");
        sortByAge = new JButton("Sort by Age");
        sellCar = new JButton("Sell Car/Add Customer");
        edit = new JButton("Edit Customer");
        delete = new JButton("Delete Customer");
        discountEligible = new JButton("Show customers eligible for discount");
        viewMenu = new JButton("Main Menu");

        sortByName.addActionListener(new sortNameButtonListener());
        sortByAge.addActionListener(new sortAgeButtonListener());
        
        sellCar.addActionListener(new sellCarButtonListener());
        edit.addActionListener(new editButtonListener());
        discountEligible.addActionListener(new discountEligibleButtonListener());
        delete.addActionListener(new deleteCustomerButtonListener());
        viewMenu.addActionListener(new viewMenuButtonListener());

        cmdPanel.setPreferredSize(new Dimension(40,40));
        cmdPanel.add(sortByName);
        cmdPanel.add(sortByAge);        
        cmdPanel.add(discountEligible);
        
        cmdPanel.add(sellCar);
        cmdPanel.add(edit);
        cmdPanel.add(delete);
        cmdPanel.add(viewMenu);

        showTable(cusList);

        displayPanel.setPreferredSize(new Dimension(700,400));

        displayPanel.add(cmdPanel);

        add(displayPanel);
        cusList = salesRep.getCusList();
        

        pack();
        setVisible(true);
    }


    public ArrayList<Car> getCarList()
    {
        return carList;
    }

    
    public IO getCusIO()
    {
        return cusIO;
    }
    public SalesRep getSalesRep()
    {
        return salesRep;
    }

    public ArrayList<Customer> getCusList()
    {
        return cusList;
    }
    
    public DefaultTableModel getModel()
    {
        return model;
    }
    public void addToTable(Customer customer, ArrayList<Customer> list)
    {
        String cusId = customer.getCusID();
        String name = customer.getName();
        String address = customer.getAddress();
        String age = Integer.toString(customer.getAge());
        String phoneNo = Integer.toString(customer.getPhoneNo());
        String occupation = customer.getOccupation();
        String carId = Integer.toString(customer.getCarBought().getCarID());
        String discount = Integer.toString(salesRep.getDiscount(customer));
        String[] cInfo = {cusId, name, age, occupation, phoneNo, address, carId, discount};  

        model.addRow(cInfo);
    }

    public void showTable(ArrayList<Customer> list)
    {
        if (list.size() > 0)
        {
            for (Customer customer: list)
            {
                addToTable(customer, list);
            }
        }
    }
    
    //removes a customer from the table
    public void remove(Customer customer)
    {
        cusList.remove(customer);
        model.setRowCount(0);
        showTable(cusList);
    }
    
    public Customer findCustomer(String ID){
        for (Customer i: cusList){
            if ((i.getCusID()).equals(ID))
                return i;
        }
    return null;
    }
    
    private class discountEligibleButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            model.setRowCount(0);
            for (Customer customer : cusList)
            {
                if (salesRep.getDiscount(customer) > 0) 
                {
                    addToTable(customer, cusEligibleForDiscount);
                }
            }
            
        }
    }
    
    private class sortNameButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            model.setRowCount(0);
            Collections.sort(cusList, new NameSort());
            showTable(cusList);
        }
    }

    private class sortAgeButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            model.setRowCount(0);
            Collections.sort(cusList, new AgeSort());
            showTable(cusList);
        }
    }
    

    private class sellCarButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new CustomerEntry(thisListing);
        }
    }

    private class editButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

            new ConfirmationWindow();   

        }
    }

    private class viewMenuButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new MainMenu(salesRep);
            thisListing.setVisible(false);
        }
    }
    private class ConfirmationWindow extends JFrame
    {
        JLabel warning;
        JPanel confirmPanel;
        JButton enter; 
        
        public ConfirmationWindow()
        {
            confirmPanel = new JPanel();
            warning = new JLabel("");
            customerIdText = new JTextField(8);
            customerId = new JLabel("Enter the ID number of the customer to be edited");
            enter = new JButton("Enter");

            enter.addActionListener(new EnterButtonListener());
            warning.setForeground(Color.red);

            confirmPanel.setPreferredSize(new Dimension(500,100));
            confirmPanel.add(customerId);
            confirmPanel.add(customerIdText);
            confirmPanel.add(enter);
            confirmPanel.add(warning, BorderLayout.SOUTH);
           
            add(confirmPanel);

            pack();
            setVisible(true);  
        }

        private class EnterButtonListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    String id = customerIdText.getText();
                    if (findCustomer(id) != null)
                    {
                        new EditWindow(thisListing,findCustomer(id));
                        setVisible(false);
                    }

                    else
                        warning.setText("Customer not found");
                }

                catch (NumberFormatException nfe)
                {
                    warning.setText("Enter a vaid ID number");
                }
            }
        }
    }

  private class deleteCustomerButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            JFrame delFrame = new JFrame();
            delFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            delFrame.add(new DeleteCustomer(thisListing,delFrame));
            delFrame.pack();
            delFrame.setVisible(true);
        }
    }
  

}
