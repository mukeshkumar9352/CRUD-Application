import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class CreateDB {
    static String url = "jdbc:mysql://localhost:3306/data";
    static String userName = "####";
    static String password = "******";

    static JTable table;
    static DefaultTableModel model;
    static JTextField t1,t2,t3;
    static JFrame frame;
    static JPanel panel,panel2;
    static JButton b1,b2,b3,b4,b5;
    static JLabel label,l1,l2,l3,l4;


    CreateDB() {
    // Frame setup
    frame = new JFrame("Student Record Manager");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setSize(600, 600);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout(10, 10));

    // Table and ScrollPane
    model = new DefaultTableModel();
    table = new JTable(model);
    table.setFont(new Font("Arial", Font.PLAIN, 14));
    table.setRowHeight(24);
    JScrollPane scrollPane = new JScrollPane(table);
    frame.add(scrollPane, BorderLayout.CENTER);

    // Input Panel (Top)
    panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createTitledBorder("Enter Student Details"));
    panel.setBackground(new Color(240, 240, 240));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);

    l1 = new JLabel("Roll:");
    t1 = new JTextField(10);
    l2 = new JLabel("Name:");
    t2 = new JTextField(10);
    l3 = new JLabel("Marks:");
    t3 = new JTextField(10);

    gbc.gridx = 0; gbc.gridy = 0; panel.add(l1, gbc);
    gbc.gridx = 1; gbc.gridy = 0; panel.add(t1, gbc);
    gbc.gridx = 0; gbc.gridy = 1; panel.add(l2, gbc);
    gbc.gridx = 1; gbc.gridy = 1; panel.add(t2, gbc);
    gbc.gridx = 0; gbc.gridy = 2; panel.add(l3, gbc);
    gbc.gridx = 1; gbc.gridy = 2; panel.add(t3, gbc);

    frame.add(panel, BorderLayout.NORTH);

    // Button Panel (Bottom)
    panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
    panel2.setBackground(new Color(200, 200, 200));

    b1 = new JButton("Retrieve");
    b2 = new JButton("Insert");
    b3 = new JButton("Clear");
    b4 = new JButton("Delete");
    b5=new JButton("Update");

    l4 = new JLabel("To delete, enter Roll and click Delete");
    l4.setForeground(Color.DARK_GRAY);

    panel2.add(b1);
    panel2.add(b2);
    panel2.add(b3);
    panel2.add(b4);
    panel2.add(b5);
    panel2.add(l4);

    frame.add(panel2, BorderLayout.SOUTH);

    // Action Listeners
    b1.addActionListener(_ -> retrive());
    b2.addActionListener(_ -> inserting());
    b3.addActionListener(_ -> clear());
    b4.addActionListener(_ -> delete());
    b5.addActionListener(_ -> update());

    frame.setVisible(true);
}

public static void update(){
    try {
        if(t1.getText().isEmpty()||t2.getText().isEmpty()||t3.getText().isEmpty()){
            
            JOptionPane.showMessageDialog(null,  " Fill all the data", "System Message", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            
        int roll=Integer.parseInt(t1.getText());
        String name=t2.getText();
        int marks=Integer.parseInt(t3.getText());
        Connection con=DriverManager.getConnection(url, userName, password);
        String sql= String.format("update student set name = '%s',marks=%d where roll=%d",name,marks,roll);
        Statement st=con.createStatement();
        int n=st.executeUpdate(sql);
        if(n>0){
            JOptionPane.showMessageDialog(null,  " Data is in updated successfully", "System Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    } catch (SQLException e) {
        
    }
}

    public static void retrive() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, userName, password);

        String sql = "select * from student";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = metaData.getColumnLabel(i + 1); 
        }

        model.setRowCount(0);
        model.setColumnIdentifiers(columnNames); 

        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                rowData[i] = rs.getObject(i + 1);
            }
            model.addRow(rowData); 
        }

        statement.close();
        connection.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public static void inserting() {
        try {
            String name=t2.getText();
            int marks=Integer.parseInt(t3.getText());
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,userName, password);
    
      String sql = String.format("INSERT INTO student (name, marks) VALUES ('%s', %d)", name, marks);

    
            Statement statement = connection.createStatement();
            int n=statement.executeUpdate(sql);
            if(n>0){
                JOptionPane.showMessageDialog(null,  " Data is in inserted successfully", "System Message", JOptionPane.INFORMATION_MESSAGE);
            }
            statement.close();
            
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void delete() {
        try {
            int id=Integer.parseInt(t1.getText());
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,userName, password);
    
            String sql = String.format("delete from student where roll=%d",id);
    
            Statement statement = connection.createStatement();
            int n=statement.executeUpdate(sql);
            if(n>0){
                JOptionPane.showMessageDialog(null,  " Data is in deleted successfully", "System Message", JOptionPane.INFORMATION_MESSAGE);
            }
            statement.close();
            
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void clear(){
        t1.setText("");
        t2.setText("");
        t3.setText("");
        model.setRowCount(0);
        model.setColumnCount(0);
    }
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

}
