/**
 * @author John Muller
 */
package eu.johnmuller.ecaperooms.Views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class ViewBooking extends javax.swing.JFrame {
    private static final ImageIcon LOGO = new ImageIcon(".//Assets//Images//Logo.png");

    public ViewBooking() {
        initComponents();
        this.setIconImage(LOGO.getImage());
    }

    private void initComponents() {

        backgroundImagePanel = new JPanel();
        closeButton = new JButton();
        surnameLbl = new JLabel();
        contactNumLbl = new JLabel();
        numOfPersonsLbl = new JLabel();
        extraReqLbl = new JLabel();
        nameLbl = new JLabel();
        roomNumberLbl = new JLabel();
        backgroundImageLabel = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Review booking");

        backgroundImagePanel.setLayout(null);

        closeButton.setBackground(new Color(27, 105, 120));
        closeButton.setFont(new Font("Trebuchet MS", 1, 18)); // NOI18N
        closeButton.setForeground(new Color(236, 236, 236));
        closeButton.setIcon(new ImageIcon(".//Assets//Images//buttonContainer.png"));
        closeButton.setText("Close");
        closeButton.setBorder(new LineBorder(new Color(27, 105, 120), 0, true));
        closeButton.setBorderPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setHorizontalTextPosition(SwingConstants.CENTER);

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        backgroundImagePanel.add(closeButton);
        closeButton.setBounds(90, 380, 80, 30);

        surnameLbl.setFont(new Font("Montserrat Alternates", 1, 12)); // NOI18N
        surnameLbl.setForeground(new Color(236, 236, 236));
        surnameLbl.setText("Muller");
        backgroundImagePanel.add(surnameLbl);
        surnameLbl.setBounds(240, 156, 150, 40);

        contactNumLbl.setFont(new Font("Montserrat Alternates", 1, 12)); // NOI18N
        contactNumLbl.setForeground(new Color(236, 236, 236));
        contactNumLbl.setText("79230000");
        backgroundImagePanel.add(contactNumLbl);
        contactNumLbl.setBounds(70, 230, 150, 30);

        numOfPersonsLbl.setFont(new Font("Montserrat Alternates", 1, 12)); // NOI18N
        numOfPersonsLbl.setForeground(new Color(236, 236, 236));
        numOfPersonsLbl.setText("5");
        backgroundImagePanel.add(numOfPersonsLbl);
        numOfPersonsLbl.setBounds(240, 230, 150, 30);

        extraReqLbl.setFont(new Font("Montserrat Alternates", 1, 12)); // NOI18N
        extraReqLbl.setForeground(new Color(236, 236, 236));
        extraReqLbl.setText("Horror theme");
        backgroundImagePanel.add(extraReqLbl);
        extraReqLbl.setBounds(70, 300, 310, 20);

        nameLbl.setFont(new Font("Montserrat Alternates", 1, 12)); // NOI18N
        nameLbl.setForeground(new Color(236, 236, 236));
        nameLbl.setText("John ");
        backgroundImagePanel.add(nameLbl);
        nameLbl.setBounds(70, 160, 140, 30);

        roomNumberLbl.setFont(new Font("Montserrat Alternates", 1, 36)); // NOI18N
        roomNumberLbl.setForeground(new Color(236, 236, 236));
        roomNumberLbl.setHorizontalAlignment(SwingConstants.CENTER);
        roomNumberLbl.setText("1");
        backgroundImagePanel.add(roomNumberLbl);
        roomNumberLbl.setBounds(350, 80, 40, 40);

        backgroundImageLabel.setIcon(new ImageIcon(".//Assets//Images//ViewBooking.png"));
        backgroundImagePanel.add(backgroundImageLabel);
        backgroundImageLabel.setBounds(0, 0, 450, 550);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(backgroundImagePanel, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(backgroundImagePanel, GroupLayout.PREFERRED_SIZE, 550, GroupLayout.PREFERRED_SIZE));

        pack();
    }

    private void closeButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

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
            java.util.logging.Logger.getLogger(ViewBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewBooking().setVisible(true);
            }
        });
    }

    private JPanel backgroundImagePanel;
    private JButton closeButton;
    private JLabel backgroundImageLabel;
    public JLabel contactNumLbl;
    public JLabel extraReqLbl;
    public JLabel nameLbl;
    public JLabel numOfPersonsLbl;
    public JLabel roomNumberLbl;
    public JLabel surnameLbl;
}
