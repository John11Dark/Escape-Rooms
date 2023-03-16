/**
 * @author John Muller
 */

package eu.johnmuller.ecaperooms.Views;

import eu.johnmuller.ecaperooms.Classes.Dialog;

import javax.swing.ImageIcon;

public class LoginForm extends javax.swing.JFrame {
    // user name value 
    public static String username;
    // set custom logo 
    ImageIcon logo = new ImageIcon(".//Assets//Images//Logo.png");

    public LoginForm() {
        initComponents();
        // apply custom logo
        this.setIconImage(logo.getImage());
    }

    private void initComponents() {

        backgroundImagePanel = new javax.swing.JPanel();
        loginBtn = new javax.swing.JButton();
        usernameTxtField = new javax.swing.JTextField();
        backgroundImageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Escape Rooms");
        setResizable(false);

        backgroundImagePanel.setLayout(null);

        loginBtn.setBackground(new java.awt.Color(27, 105, 120));
        loginBtn.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        loginBtn.setForeground(new java.awt.Color(236, 236, 236));
        loginBtn.setText("Login");
        loginBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(27, 105, 120), 0, true));
        loginBtn.setBorderPainted(false);
        loginBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginBtnMouseClicked(evt);
            }
        });
        backgroundImagePanel.add(loginBtn);
        loginBtn.setBounds(130, 250, 100, 40);

        usernameTxtField.setBackground(new java.awt.Color(58, 72, 77));
        usernameTxtField.setForeground(new java.awt.Color(236, 236, 236));
        usernameTxtField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(58, 72, 77), 6, true));
        usernameTxtField.setCaretColor(new java.awt.Color(236, 236, 236));
        backgroundImagePanel.add(usernameTxtField);
        usernameTxtField.setBounds(70, 210, 210, 30);

        backgroundImageLabel.setIcon(new javax.swing.ImageIcon(".//Assets//Images//LoginPage.png")); // NOI18N
        backgroundImagePanel.add(backgroundImageLabel);
        backgroundImageLabel.setBounds(0, -10, 380, 470);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(backgroundImagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(backgroundImagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE)));

        pack();
    }

    private void loginBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnMouseClicked
        this.username = usernameTxtField.getText().trim();
        // check if user input is valid  
        // if true destroy the current form and load the main form
        // and pass the username value
        if (!username.equals("") && !username.equals(" ") && username.matches("^[a-zA-Z\\s]*$") && ((username.length() >= 4) && (username.length() <= 18))) {
            this.dispose();
            new MainForm().setVisible(true);
            //I know that this is a stupid way to pass data to another form
            // but that is the only way i managed to do.
            // when setting a value as public and fetch it from another form
            // it gets value when the main method is called
            new AboutForm().username = this.username;
        }
        // if user input is invalid show an error dialog 
        else {
            Dialog.showErrorDialog("username field must be\nbetween 4-18 alphabetical letters", this, "Escape Rooms");
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    public String getUsername() {
        return this.usernameTxtField.getText();
    }

    private javax.swing.JPanel backgroundImagePanel;
    private javax.swing.JLabel backgroundImageLabel;
    private javax.swing.JButton loginBtn;
    private javax.swing.JTextField usernameTxtField;
}
