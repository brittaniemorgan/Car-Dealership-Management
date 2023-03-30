package carDealership;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.table.*;
import java.util.*;

public class CarListing extends JFrame
  {
    private CarListing listing;
    private SalesRep salesRep;
    private ArrayList<Car> clist;
    private JButton sortCbrand;
    private JButton sortCPrice;
    private JButton viewMenu;
    private JScrollPane scrollPane;
    private JTable carTable;
    private DefaultTableModel model;
    private JButton deleteCar;

    private JPanel command;
    private JPanel display;
      
    private IO carIO;


    public CarListing(SalesRep rep){

        this.salesRep = rep;
        clist = rep.getCarList();
        
       setLayout(new BorderLayout());

        listing = this;
        command = new JPanel();
        display = new JPanel();
        
        String [] columnNames = {"ID#","Brand", "Model", "Year", "Condition", "Amount in Stock" ,"Price"};
        model = new DefaultTableModel(columnNames,0);
        carTable = new JTable(model);
        
        //setting the widths of the columns
        carTable.getColumnModel().getColumn(0).setPreferredWidth(150);       
        carTable.getColumnModel().getColumn(1).setPreferredWidth(500);
        carTable.getColumnModel().getColumn(2).setPreferredWidth(500);
        carTable.getColumnModel().getColumn(3).setPreferredWidth(500);
        carTable.getColumnModel().getColumn(4).setPreferredWidth(500);
        carTable.getColumnModel().getColumn(5).setPreferredWidth(500);
        carTable.getColumnModel().getColumn(6).setPreferredWidth(500);               
        
        carTable.setPreferredScrollableViewportSize(new DimensionUIResource(500, clist.size()*15 +50));
        carTable.setFillsViewportHeight(true);
      
        showTable(clist);
        
        scrollPane = new JScrollPane(carTable);

        display.add(scrollPane);

        //creating the buttons
        sortCbrand = new JButton("Sort by Car Brand");
        sortCPrice = new JButton("Sort by Car Price");
        deleteCar = new JButton("Delete");
        viewMenu = new JButton("Main Menu");

        sortCbrand.addActionListener(new sortBrandButtonListener());
      sortCPrice.addActionListener(new sortPriceButtonListener());
        deleteCar.addActionListener(new deleteCarButtonListener());
        viewMenu.addActionListener(new viewMenuButtonListener());

        command.add(sortCbrand);
        command.add(sortCPrice);
        command.add(deleteCar);
        command.add(viewMenu);

        add(display, BorderLayout.CENTER);
        add(command, BorderLayout.SOUTH); 

        carIO = new IO("savedInfo/Cars.txt");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        
    }

      public IO getCarIO()
      {
          return carIO;
      }

      public DefaultTableModel getModel()
      {
          return model;
      }

    public void showTable(ArrayList<Car> clist)
    {

      if(clist.size()>0){
          for (int i=0; i<clist.size(); i++)
            addToTable(clist.get(i));
      }
  
    }

    public ArrayList<Car> getCarList(){
      return clist;
    }

    private void addToTable(Car car){
        String[] carInfo = {"" + car.getCarID(), car.getBrand(), car.getModel(), "" + car.getYear(), car.getCondition(), "" + car.getStock(), "" + car.getPrice()};
        model.addRow(carInfo);
   
    }

    private class sortBrandButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            model.setRowCount(0);
            Collections.sort(clist, new SortBrand());
            showTable(clist);
        }
    }

    private class sortPriceButtonListener implements ActionListener
      {
        public void actionPerformed(ActionEvent event)
        {
          model.setRowCount(0);
          Collections.sort(clist, new SortPrice());
          showTable(clist);
        }
      }

    private class deleteCarButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            JFrame delFrame = new JFrame();
            delFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            delFrame.add(new DeleteCar(listing, delFrame));
            delFrame.pack();
            delFrame.setVisible(true);
                   
        }
    }

          private class viewMenuButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new MainMenu(salesRep);
            listing.setVisible(false);
        }
    }
  //Finds the postion of a car in the car list
  public int findCar(int ID){
    int pos = -1;
    for (Car i: clist){
      if ((i.getCarID())==ID){
        pos= clist.indexOf(i);
      }
    
   }
    return pos;
  }
    
  }
  





