package model;

import phone.PhoneBookMain;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/**
 * Created by gunner on 4/6/15.
 */
public class QueryInDatabase {
    DatabaseConnection db = new DatabaseConnection();
    Connection conn = db.getConnection();

    public void saveInDatabase(String[] person) {
        String firstName = person[0];
        String lastName = person[1];
        String phone = person[2];
        String email = person[3];
        String person_group = person[4];

        try {
            String query = "insert into person(firstname,lastname,phone,email,p_group) values (?,?,?,?,?)";
            PreparedStatement insertEvent = conn.prepareStatement(query);
            insertEvent.setString(1, firstName);
            insertEvent.setString(2, lastName);
            insertEvent.setString(3, phone);
            insertEvent.setString(4, email);
            insertEvent.setString(5, person_group);

            int result = insertEvent.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Contact Successfully Added..!!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Contact Adding Failed..!!", "Failed", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchInDatabase(String searchField){
        String fName = searchField;
        System.out.print("from text "+fName);

        String searchQuery = "Select firstname,lastname,phone,email,p_group"+
                " from person" + " where firstname = ?";

        System.out.println("the query::"+searchQuery);

        try {
            PreparedStatement statement = conn.prepareStatement(searchQuery);
            statement.setString(1,searchField);

            ResultSet results = statement.executeQuery();
                String[] retrievedValue = new String[5];
                while (results.next()) {
                    String firstname = results.getString("firstname");
                    String lastname = results.getString("lastname");
                    String phone = results.getString("phone");
                    String email = results.getString("email");
                    String p_group = results.getString("p_group");

                    retrievedValue[0] = firstname;
                    retrievedValue[1] = lastname;
                    retrievedValue[2] = phone;
                    retrievedValue[3] = email;
                    retrievedValue[4] = p_group;

                    System.out.println("firstName : " + firstname);
                    System.out.println("lastname : " + lastname);
                    System.out.println("phone  : " + phone);
                    System.out.println("email : " + email);
                    System.out.println("Group : " + p_group);
                }
                searchDisplayResultGUI(retrievedValue);

        /*if(!results.next()) {
                    JOptionPane.showMessageDialog(null, "No Value Found", "Failed!", JOptionPane.INFORMATION_MESSAGE);
                }*/

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void searchDisplayResultGUI(String[] retrievedValue){
        System.out.print("firstname "+retrievedValue[0]);

        JFrame mainFrame = new JFrame("PhoneBook Search");
        mainFrame.setSize(400,400);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        JInternalFrame internalFrame = new JInternalFrame("Search Contact",true,true,true,true);
        JDesktopPane desktop = new JDesktopPane();
        internalFrame.setSize(380, 200);


        JPanel northPanel = new JPanel();
        JPanel southPanel = new JPanel();


        northPanel.setLayout(new GridLayout(5,2));
        JLabel lbl_firstName = new JLabel("First Name");
        JLabel firstname = new JLabel(retrievedValue[0].toString());
        JLabel lbl_lastName = new JLabel("Last Name");
        JLabel lname = new JLabel(retrievedValue[1].toString());
        JLabel lbl_Phone = new JLabel("Phone");
        JLabel ph = new JLabel(retrievedValue[2].toString());
        JLabel lbl_Email = new JLabel("Email");
        JLabel email = new JLabel(retrievedValue[3].toString());
        JLabel lbl_group = new JLabel("Group");
        JLabel group = new JLabel(retrievedValue[4].toString());

        northPanel.add(lbl_firstName);
        northPanel.add(firstname);
        northPanel.add(lbl_lastName);
        northPanel.add(lname);
        northPanel.add(lbl_Phone);
        northPanel.add(ph);
        northPanel.add(lbl_Email);
        northPanel.add(email);
        northPanel.add(lbl_group);
        northPanel.add(group);

        internalFrame.add(northPanel, BorderLayout.NORTH);
        internalFrame.add(southPanel, BorderLayout.SOUTH);
        northPanel.setVisible(true);
        southPanel.setVisible(true);

        internalFrame.setVisible(true);
        desktop.add(internalFrame);
        mainFrame.add(desktop);
    }

    public String[] selectFromDatabase() {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet results = stmt.executeQuery("Select firstname,lastname,phone,email,p_group from person");
            String[] retrievedValue = new String[5];
            while(results.next()){
                String firstname = results.getString("firstname");
                String lastname = results.getString("lastname");
                String phone = results.getString("phone");
                String email = results.getString("email");
                String p_group = results.getString("p_group");

                retrievedValue[0] = firstname;
                retrievedValue[1] = lastname;
                retrievedValue[2] = phone;
                retrievedValue[3] = email;
                retrievedValue[4] = p_group;
            }
            ResultSetMetaData meta = results.getMetaData();
            DefaultTableModel dtm = new DefaultTableModel();

            int numberOfColumns = meta.getColumnCount();
            while (results.next())
            {
                Object [] rowData = new Object[numberOfColumns];
                for (int i = 0; i < rowData.length; ++i)
                {
                    rowData[i] = results.getObject(i+1);
                }
                dtm.addRow(rowData);
            }
            //gui.jTable1.setModel(dtm);
            dtm.fireTableDataChanged();
            return retrievedValue;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void fillTable(JTable table){
        try
        {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("Select firstname,lastname,phone,email,p_group from person");

            while(table.getRowCount() > 0){
                ((DefaultTableModel) table.getModel()).removeRow(0);
            }
            int columns = rs.getMetaData().getColumnCount();
            while(rs.next()){
                Object[] row = new Object[columns];
                for (int i = 1; i <= columns; i++)
                {
                    row[i - 1] = rs.getObject(i);
                }
                ((DefaultTableModel) table.getModel()).insertRow(rs.getRow()-1,row);
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void populateInTable(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Object rows[][] = null;
        Object headers[] = {"FirstName ", "Lastname"};

        QueryInDatabase qdb = new QueryInDatabase();
        String string[] = qdb.selectFromDatabase();
        int i=0,j=0;
        for(i=0,j=0;i<10 && j<10;i++)
            rows[i][j] = string[i];
        JTable table = new JTable(rows, headers);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(300, 150);
        frame.setVisible(true);
    }

        // rs is the ResultSet of the Database table
        /*public void displayData(ResultSet rs){
            //jt Represents JTable
            //jf represents JFrame
            int i;
            int count;
            String a[];
            String header[] = {"FirstName ", "Lastname"};   //Table Header Values, change, as your wish
            count = header.length;

            //First set the Table header
            for(i = 0; i < count; i++)
            {
                model.addColumn(header[i]);
            }
            jt.setModel(model);                             //Represents table Model
            jf.add(jt.getTableHeader(),BorderLayout.NORTH);

            a = new String[count];
// Adding Database table Data in the JTable
            try
            {
                while (rs.next())
                {
                    for(i = 0; i < count; i++)
                    {
                        a[i] = rs.getString(i+1);
                    }
                    model.addRow(a);                //Adding the row in table model
                    jt.setModel(model);             // set the model in jtable
                }
            }

            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "Exception : "+e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
*/

}
