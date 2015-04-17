package phone;

import model.QueryInDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gunner on 3/15/15.
 */
public class PhoneBookMain {
    QueryInDatabase qdb = new QueryInDatabase();
    public JFrame mainFrame;
    public JInternalFrame internalFrame;

    public PhoneBookMain(){
        prepareGUI();
    }

    public static void main(String[] args) {
        PhoneBookMain phoneBookMain = new PhoneBookMain();
    }

    private void prepareGUI(){
        mainFrame = new JFrame("PhoneBook Swing");
        mainFrame.setSize(400,400);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");

        JMenuItem close = new JMenuItem("Close");
        JMenuItem home = new JMenuItem("Home");
        JMenuItem addContact = new JMenuItem("Add Contact");
        JMenuItem editContact = new JMenuItem("Edit");
        JMenuItem search = new JMenuItem("Search");

        file.add(home);
        file.add(addContact);
        //file.add(editContact);
        file.add(search);
        file.add(close);

        menuBar.add(file);
        mainFrame.setJMenuBar(menuBar);

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        home.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //homeDisplay();
            }
        });

        addContact.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                addContact();
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchContact();
            }
        });
        mainFrame.setVisible(true);
    }

    public void addContact(){
        internalFrame = new JInternalFrame("Add Contact",true,true,true,true);
        JDesktopPane desktop = new JDesktopPane();
        internalFrame.setSize(380, 200);
        mainFrame.setVisible(true);
        JPanel northPanel = new JPanel();
        JPanel southPanel = new JPanel();

        northPanel.setLayout(new GridLayout(5,2));
        JLabel lbl_firstName = new JLabel("First Name");
        final JTextField txt_firstName = new JTextField();
        JLabel lbl_lastName = new JLabel("Last Name");
        final JTextField txt_lastName = new JTextField();
        JLabel lbl_Phone = new JLabel("Phone");
        final JTextField txt_phone = new JTextField();
        JLabel lbl_Email = new JLabel("Email");
        final JTextField txt_email = new JTextField();
        JLabel lbl_group = new JLabel("Group");
        final String[] p_group = new String[]{"Friend","Family","Work"};
        final JComboBox<String> groupList = new JComboBox<String>(p_group);

        northPanel.add(lbl_firstName);
        northPanel.add(txt_firstName);
        northPanel.add(lbl_lastName);
        northPanel.add(txt_lastName);
        northPanel.add(lbl_Phone);
        northPanel.add(txt_phone);
        northPanel.add(lbl_Email);
        northPanel.add(txt_email);
        northPanel.add(lbl_group);
        northPanel.add(groupList);

        JButton btn_add = new JButton("Add");
        btn_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("add button called..");
                String[] person = new String[5];
                person[0] = txt_firstName.getText();
                person[1] = txt_lastName.getText();
                person[2] = txt_phone.getText();
                person[3] = txt_email.getText();
                person[4] = (String) groupList.getSelectedItem();

                if(txt_firstName.getText().equals("") || txt_lastName.getText().equals("") ||
                        txt_phone.getText().equals("") || txt_email.getText().equals("")){
                    JOptionPane.showMessageDialog (null, "The Fields are not filled properly..Please Fill up all fields..!!", "Error",
                            JOptionPane.WARNING_MESSAGE);
                }else {
                    qdb.saveInDatabase(person);
                    System.out.print("add button called..");
                }
            }
        });

        JButton btn_reset = new JButton("Reset");
        btn_reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                txt_firstName.setText("");
                txt_lastName.setText("");
                txt_phone.setText("");
                txt_email.setText("");
                groupList.getSelectedIndex();
            }
        });

        southPanel.add(btn_add);
        southPanel.add(btn_reset);
        southPanel.setVisible(true);
        internalFrame.add(northPanel, BorderLayout.NORTH);
        internalFrame.add(southPanel, BorderLayout.SOUTH);

        internalFrame.setVisible(true);
        desktop.add(internalFrame);
        mainFrame.add(desktop);
    }

    public void searchContact(){
        internalFrame = new JInternalFrame("Search Contact By firstname",true,true,true,true);
        JDesktopPane desktop = new JDesktopPane();
        mainFrame.setVisible(true);
        internalFrame.setSize(380, 200);
        internalFrame.setVisible(true);
        JPanel northPanel = new JPanel();
        JPanel southPanel = new JPanel();

        northPanel.setLayout(new GridLayout(5,2));
        JLabel lbl_firstName = new JLabel("First Name");
        final JTextField txt_firstName = new JTextField();

        northPanel.add(lbl_firstName);
        northPanel.add(txt_firstName);

        JButton btn_search = new JButton("Search");
        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("search button called..");
                String searchfield = txt_firstName.getText();

                if(txt_firstName.getText().equals("")){
                    JOptionPane.showMessageDialog (null, "The Fields are not filled properly..Please Fill up all fields..!!", "Error",
                            JOptionPane.WARNING_MESSAGE);
                }else {
                    qdb.searchInDatabase(searchfield);
                    System.out.print("Search called..");
                }
            }
        });
        JButton btn_reset = new JButton("Reset");
        btn_reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                txt_firstName.setText("");
            }
        });

        southPanel.add(btn_search);
        southPanel.add(btn_reset);
        internalFrame.add(northPanel, BorderLayout.NORTH);
        internalFrame.add(southPanel, BorderLayout.SOUTH);

        internalFrame.setVisible(true);
        desktop.add(internalFrame);
        mainFrame.add(desktop);
    }
}
/*

    public void editContact(){
        JFrame frame = new JFrame();
        frame.setTitle("Edit testingtable.Contact");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setSize(300, 150);
        frame.setVisible(true);

    }
public void homeDisplay(){
        internalFrame = new JInternalFrame("Home",true,true,true,true);
        JDesktopPane desktop = new JDesktopPane();
        internalFrame.setSize(380, 200);
        final Object columnHeaders[] = {"FirstName ", "Lastname"};
        final Object rows[][] = null;
        *//*final Object rows[][] = {{"one",   "1"},
        {"two",   "2"},
        {"three", "3"},
        {"four",  "4"}};*//*
        JTable table = new JTable(rows, columnHeaders);
        JScrollPane scrollPane = new JScrollPane(table);


        //Object rows[][] = null;
        //Object headers[] = {"FirstName ", "Lastname"};

        QueryInDatabase qdb = new QueryInDatabase();
        qdb.fillTable(table);
        //String string[] = qdb.selectFromDatabase();
      *//*  int i=0,j=0;
        for(i=0,j=0;i<10 && j<10;i++)
            rows[i][j] = string[i];*//*
        //JTable table = new JTable(rows, headers);

        //JScrollPane scrollPane = new JScrollPane(table);
        //frame.add(scrollPane, BorderLayout.CENTER);

        internalFrame.add(scrollPane, BorderLayout.CENTER);
        internalFrame.setVisible(true);
        desktop.add(internalFrame);
        mainFrame.add(desktop);
        mainFrame.setVisible(true);
    }*/


