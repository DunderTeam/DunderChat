/*
 * Created by JFormDesigner on Mon Nov 16 17:19:41 CET 2020
 */

package view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Adrian Emil Chambe-Eng
 */
public class WindowRegister extends JFrame {
    public WindowRegister() {
        initComponents();
    }

    // Calls RegisterNewUser
    private void BtnConfirmMouseClicked(MouseEvent e) {
        RegisterNewUser();
    }

    // Opens a new LoginWindow and closes the registration window
    private void BtnReturnMouseClicked(MouseEvent e) {
        new view.gui.WindowLogin().setVisible(true);
        dispose();
    }

    // Tries to register a new user with the database
    private void RegisterNewUser() {
        String newUser = InputUsr.getText();
        char[] newPwd = InputPwd.getPassword();
        // TODO implement user registration
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        WindowTitle = new JLabel();
        InputPwd = new JPasswordField();
        BtnConfirm = new JButton();
        BtnReturn = new JButton();
        InputUsr = new JTextField();

        //======== this ========
        Container contentPane = getContentPane();

        //---- WindowTitle ----
        WindowTitle.setText("Register");
        WindowTitle.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 20));
        WindowTitle.setHorizontalAlignment(SwingConstants.CENTER);

        //---- InputPwd ----
        InputPwd.setText("password");

        //---- BtnConfirm ----
        BtnConfirm.setText("confirm");
        BtnConfirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BtnConfirmMouseClicked(e);
            }
        });

        //---- BtnReturn ----
        BtnReturn.setText("return");
        BtnReturn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BtnReturnMouseClicked(e);
            }
        });

        //---- InputUsr ----
        InputUsr.setText("username");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap(75, Short.MAX_VALUE)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(InputUsr, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
                        .addComponent(InputPwd, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(BtnConfirm)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BtnReturn))
                        .addComponent(WindowTitle, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(76, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(50, 50, 50)
                    .addComponent(WindowTitle)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(InputUsr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(InputPwd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(BtnConfirm)
                        .addComponent(BtnReturn))
                    .addContainerGap(93, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel WindowTitle;
    private JPasswordField InputPwd;
    private JButton BtnConfirm;
    private JButton BtnReturn;
    private JTextField InputUsr;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
