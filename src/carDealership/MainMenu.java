package carDealership;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//shows when a sales representative successfully login
public class MainMenu extends JFrame 
{
    private JPanel disPanel, cmdPanel;
    private JButton cusListing, carListing, logOut;
    private JLabel welcome, space;
    private SalesRep salesRep;
    private MainMenu menu;

    public MainMenu(SalesRep rep)
    {
        salesRep = rep;
        menu = this;
        setLayout(new BorderLayout());
        setTitle("Main Menu --- User: " + salesRep.getName() );
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 350));
        
        welcome = new JLabel("Welcome " + salesRep.getName() + "!");
        space = new JLabel("                                             "); 
        disPanel = new JPanel(new BorderLayout());
        cmdPanel = new JPanel();

        cusListing = new JButton("View Customer Listing");
        carListing = new JButton("View Car Listing");
        logOut = new JButton("Log Out");

        cusListing.setBackground(Color.WHITE);
        carListing.setBackground(Color.WHITE);
        logOut.setBackground(Color.WHITE);

        cusListing.addActionListener(new CusListingButtonListener());
        carListing.addActionListener(new CarListingButtonListener());
        logOut.addActionListener(new LogOutButtonActionListener());

        disPanel.add(space, BorderLayout.EAST);
        disPanel.add(welcome, BorderLayout.CENTER);
        disPanel.add(space, BorderLayout.WEST);
        disPanel.setBackground(Color.orange);

        cmdPanel.add(cusListing);
        cmdPanel.add(carListing);
        cmdPanel.add(logOut);

        add(disPanel, BorderLayout.CENTER);
        add(cmdPanel, BorderLayout.SOUTH);    

        pack();
        setVisible(true);
    }

    public SalesRep getSalesRep()
    {
        return salesRep;
    }

    private class CusListingButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new CustomerListing(salesRep);
            menu.setVisible(false);
        }
    }

    private class CarListingButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new CarListing(salesRep);
            menu.setVisible(false);
        }
    }

    private class LogOutButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            setVisible(false);
        }
    }

    
}
