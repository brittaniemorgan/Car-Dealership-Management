package carDealership;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteCustomer extends JPanel{
  
   private IO               cusIO;
   private JLabel           enterID;
   private JButton          delete;
   private JTextField       cusID;
   private JLabel           warning;
   private CustomerListing  cuslist;
   private JFrame           delframe;

  //deletes a customer from the customer list table
  public DeleteCustomer(CustomerListing listing, JFrame delframe)
        {
            setPreferredSize(new Dimension(400, 100));
            cusIO = listing.getCusIO();
            this.cuslist = listing;
            
            enterID = new JLabel("Enter the ID number of the customer to be deleted:");
            cusID = new JTextField(8);
            warning = new JLabel("");
    
            delete = new JButton("Delete");

            warning.setForeground(Color.RED);
            delete.addActionListener(new DeleteButtonListener());

            add(enterID);
            add(cusID);
            add(delete);
            add(warning);

            this.delframe = delframe;
  
}
 
  private class DeleteButtonListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    String id = cusID.getText();
                    if (cuslist.findCustomer(id) != null)
                    {
                        Customer cus = cuslist.findCustomer(id);
                        cuslist.getModel().setRowCount(0);
                        cuslist.remove(cus);                        
                        cusIO.delete(cus.toString());
                        setVisible(false);
                        delframe.setVisible(false);
                    }
                    else
                        warning.setText("Enter a valid customer ID");
                }
                catch (NumberFormatException nfe)
                {
                    warning.setText("Enter a vaid ID number");
                }

            }
        }
}