package carDealership;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginScreen extends JFrame
  {
    ArrayList<String[]>   pwdList;
    private JTextField    username;
    private JTextField    password;
    private JButton       submit, clearAll;
    private JLabel        successful;
    private JLabel        uLabel;
    private JLabel        pLabel;

    private JPanel        panel;
    private JPanel        buttonPanel;
    private JPanel        success;

    private SalesRep      salesRep;
    private ArrayList<SalesRep> slist;

    public LoginScreen()
    {
      slist = SalesRep.getRepList();
      pwdList = new ArrayList<String[]>();
      panel = new JPanel();
      panel.setLayout(new GridLayout(0,1));

        setLayout(new BorderLayout());        
      username = new JTextField(30);
      password = new JTextField(30);
      uLabel = new JLabel("ID#: ");
      pLabel = new JLabel("Password: ");

      panel.add(uLabel);
      panel.add(username);
      panel.add(pLabel);
      panel.add(password);
      panel.setPreferredSize(new Dimension(349,120));

      buttonPanel = new JPanel();
      submit = new JButton("Submit");
      clearAll = new JButton("Clear All");
      ButtonListener buttonListener = new ButtonListener();
      submit.addActionListener(buttonListener);
      clearAll.addActionListener(buttonListener);

      buttonPanel.add(submit);
      buttonPanel.add(clearAll);
      buttonPanel.setPreferredSize(new Dimension(349,40));
      
      success = new JPanel();
      successful = new JLabel("");
      success.add(successful);
      success.setPreferredSize(new Dimension(349,40));

      add(panel, BorderLayout.NORTH);     
      add(buttonPanel, BorderLayout.CENTER);    
      add(success, BorderLayout.SOUTH);    

    setPreferredSize(new Dimension(400,350));
    setDefaultCloseOperation(EXIT_ON_CLOSE);

        pack();
        setVisible(true);

      loadCredentials("savedInfo/Passwords.txt");
      
    }
      private void loadCredentials(String passwordFile)
      {
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

      public SalesRep findRep(String id)
      {
          SalesRep retVal = null;
          for (SalesRep rep: slist)
          {
              if (rep.getId().equals(id))
                  retVal = rep;
          }
          return retVal;
  
      }

    private boolean passwordMatch(String[] info)
    {
        boolean retVal = false;
        for (String[] credential: pwdList)
        {
            if (Arrays.equals(info, credential))
                retVal = true;
        }
        return retVal;   
    }


    private class ButtonListener implements ActionListener
      {
        public void actionPerformed(ActionEvent event)
        {
          if(event.getSource() == submit)
          {
            if(username.getText().length() > 0 && password.getText().length() > 0)
            {
                String idStr = username.getText().trim();
                String passwordStr = password.getText();
                String[] info = {idStr, passwordStr};
                if (passwordMatch(info) && (findRep(idStr) != null))
                {
                    salesRep = findRep(idStr);
                    successful.setText("Login Successful");  
                    new MainMenu(salesRep); 
                    password.setText("");
                    username.setText("");
                    successful.setText(" ");
                }
                else{
                  successful.setText("ID# or password not found");
                }
            } 
          }else{
            password.setText("");
            username.setText("");
            successful.setText(" ");
          }
        }
      }
    
  }