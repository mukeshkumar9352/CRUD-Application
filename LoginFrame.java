import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class LoginFrame extends JFrame implements ActionListener {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;
    JLabel messageLabel;

    // Database connection details (same as in MyJFrame1)
    String url = "jdbc:mysql://localhost:3306/data";
    String userName = "#####";
    String password = "*******";

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10)); // Rows, cols, hgap, vgap
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        messageLabel = new JLabel("Please enter your credentials.");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);
        

        add(panel);

        loginButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (authenticate(username, password)) {
                messageLabel.setText("Login Successful!");
                JOptionPane.showMessageDialog(this, "Login Successful!");
                
                // If login is successful, open the main JFrame and close the login frame
                new CreateDB();
                this.dispose(); // Close the login frame
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean authenticate(String username, String password) {
        try (Connection con = DriverManager.getConnection(url, userName, this.password); // Use this.password for DB password
             PreparedStatement pstmt = con.prepareStatement("SELECT * FROM login WHERE user_name = ? AND password = ?")) {

            pstmt.setString(1, username);
            pstmt.setString(2, password); 

            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a matching user is found
        } catch (SQLException ex) {
            ex.printStackTrace();
          //  messageLabel.setText("Database error: " + ex.getMessage());
            return false;
        }
    }

}
