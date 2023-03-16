/**
 * @author John Muller
 */
package eu.johnmuller.ecaperooms.Views;

import eu.johnmuller.ecaperooms.Classes.Dialog;
import eu.johnmuller.ecaperooms.Classes.FileIO;
import eu.johnmuller.ecaperooms.Classes.Validation;

import javax.swing.*;


public class MakeBooking extends javax.swing.JFrame {
    static final ImageIcon LOGO = new ImageIcon(".//Assets//Images//Logo.png");

    public int roomNumber;
    public String date;


    public MakeBooking() {
        initComponents();
        this.setIconImage(LOGO.getImage());
        // set room number
        this.roomNumberLbl.setText(" " + roomNumber);
        // set combo box values
        this.numberOfPersonComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"1", "2", "3", "4", "5"}));
        // set combo box selected index
        this.numberOfPersonComboBox.setSelectedIndex(1);
    }

    public boolean validateInput() {
        // using a separate method to check the input
        // inverse the condition (statement) so if one condition not valid will return from the function
        if (!Validation.string(this, this.nameField.getText(), 3, 12, "name")) return false;
        if (!Validation.string(this, this.surnameField.getText(), 3, 12, "surname")) return false;
        if (!Validation.stringDigits(this, this.contactNumField.getText(), 8, 8, "Contact number")) return false;
        // if all conditions are valid then return true
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundImagePanel = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        extraReqField = new javax.swing.JTextArea();
        surnameField = new javax.swing.JTextField();
        contactNumField = new javax.swing.JTextField();
        nameField = new javax.swing.JTextField();
        numberOfPersonComboBox = new javax.swing.JComboBox<>();
        roomNumberLbl = new javax.swing.JLabel();
        backgroundImagelabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Make a booking");

        backgroundImagePanel.setLayout(null);

        saveButton.setBackground(new java.awt.Color(227, 170, 40));
        saveButton.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        saveButton.setForeground(new java.awt.Color(236, 236, 236));
        saveButton.setIcon(new javax.swing.ImageIcon("E:\\OneDrive - Malta College of Arts, Science & Technology\\MCAST\\MCAST MQF4\\Year 2\\Lv4-Y2-Sem1\\ITSFT-406-2005  Programming Concepts\\Assignments\\Assignment 2\\EcapeRooms_MullerJohn4_2A\\Assets\\Images\\PrimaryButton.png")); // NOI18N
        saveButton.setText("Save");
        saveButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(27, 105, 120), 0, true));
        saveButton.setBorderPainted(false);
        saveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        backgroundImagePanel.add(saveButton);
        saveButton.setBounds(280, 380, 100, 30);

        cancelButton.setBackground(new java.awt.Color(27, 105, 120));
        cancelButton.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(236, 236, 236));
        cancelButton.setText("Cancle");
        cancelButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(27, 105, 120), 0, true));
        cancelButton.setBorderPainted(false);
        cancelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        backgroundImagePanel.add(cancelButton);
        cancelButton.setBounds(170, 380, 100, 30);

        jScrollPane1.setBackground(new java.awt.Color(56, 71, 77));
        jScrollPane1.setForeground(new java.awt.Color(225, 225, 225));
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        extraReqField.setBackground(new java.awt.Color(57, 71, 77));
        extraReqField.setColumns(10);
        extraReqField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        extraReqField.setForeground(new java.awt.Color(225, 225, 225));
        extraReqField.setLineWrap(true);
        extraReqField.setRows(5);
        extraReqField.setWrapStyleWord(true);
        jScrollPane1.setViewportView(extraReqField);

        backgroundImagePanel.add(jScrollPane1);
        jScrollPane1.setBounds(70, 300, 305, 60);

        surnameField.setBackground(new java.awt.Color(56, 71, 77));
        surnameField.setColumns(10);
        surnameField.setForeground(new java.awt.Color(225, 225, 225));
        surnameField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        surnameField.setBorder(null);
        surnameField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        backgroundImagePanel.add(surnameField);
        surnameField.setBounds(235, 160, 140, 30);

        contactNumField.setBackground(new java.awt.Color(56, 71, 77));
        contactNumField.setForeground(new java.awt.Color(225, 225, 225));
        contactNumField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        contactNumField.setBorder(null);
        contactNumField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        backgroundImagePanel.add(contactNumField);
        contactNumField.setBounds(68, 230, 140, 30);

        nameField.setBackground(new java.awt.Color(56, 71, 77));
        nameField.setForeground(new java.awt.Color(225, 225, 225));
        nameField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nameField.setBorder(null);
        nameField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        backgroundImagePanel.add(nameField);
        nameField.setBounds(68, 160, 140, 30);

        numberOfPersonComboBox.setBackground(new java.awt.Color(56, 71, 77));
        numberOfPersonComboBox.setForeground(new java.awt.Color(225, 225, 225));
        numberOfPersonComboBox.setBorder(null);
        numberOfPersonComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backgroundImagePanel.add(numberOfPersonComboBox);
        numberOfPersonComboBox.setBounds(235, 230, 140, 30);

        roomNumberLbl.setFont(new java.awt.Font("Montserrat Alternates", 1, 36)); // NOI18N
        roomNumberLbl.setForeground(new java.awt.Color(225, 225, 225));
        roomNumberLbl.setText("5");
        backgroundImagePanel.add(roomNumberLbl);
        roomNumberLbl.setBounds(325, 83, 32, 30);

        backgroundImagelabel.setIcon(new javax.swing.ImageIcon(".//Images//LoginPage.png")); // NOI18N
        backgroundImagelabel.setIcon(new javax.swing.ImageIcon("E:\\OneDrive - Malta College of Arts, Science & Technology\\MCAST\\MCAST MQF4\\Year 2\\Lv4-Y2-Sem1\\ITSFT-406-2005  Programming Concepts\\Assignments\\Assignment 2\\EcapeRooms_MullerJohn4_2A\\Assets\\Images\\makeBooking.png")); // NOI18N
        backgroundImagePanel.add(backgroundImagelabel);
        backgroundImagelabel.setBounds(0, 0, 450, 550);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundImagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundImagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // if all inputs are valid
        if (validateInput()) {
            // ask the user if they want to save the file
            if (Dialog.showConfirmationDialog(this, "Are you sure you want to save this booking", "Make booking " + roomNumber)) {
                String data = String.format("%s,%s,%s,%s,%s,%s,%s", roomNumber, nameField.getText(), surnameField.getText(), contactNumField.getText(), date, numberOfPersonComboBox.getSelectedItem(), extraReqField.getText());
                // then call storeBooking() from fileIO to store the data
                FileIO.storeBooking(this, new MainForm().FILEPATH, data, "Saved", "your booking for room " + roomNumber + " has been saved successfully");
                // after the data has been stored the form will be destroyed
                this.dispose();
            } else {
                // if they do not want to save confirm again
                if (Dialog.showConfirmationDialog(this, "Are you sure you want to cancel this booking", "Make booking " + roomNumber))
                    // if they do not want save destroy the form
                    this.dispose();
            }
        }
    }                                          

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed

        if ((surnameField.getText().length() > 0) || (nameField.getText().length() > 0) || (contactNumField.getText().length() > 0) || (extraReqField.getText().length() > 0)) {
            if (Dialog.showConfirmationDialog(this, "are you sure you want to cancel?", "Create new booking room " + roomNumber))
                this.dispose();
        } else {
            this.dispose();
        }


    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MakeBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MakeBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MakeBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MakeBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MakeBooking().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundImagePanel;
    private javax.swing.JLabel backgroundImagelabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField contactNumField;
    private javax.swing.JTextArea extraReqField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameField;
    private javax.swing.JComboBox<String> numberOfPersonComboBox;
    public javax.swing.JLabel roomNumberLbl;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField surnameField;
    // End of variables declaration//GEN-END:variables
}
