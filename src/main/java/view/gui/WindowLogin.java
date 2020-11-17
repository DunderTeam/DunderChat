/*
 * Created by JFormDesigner on Mon Nov 16 17:20:07 CET 2020
 */

package view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Adrian Emil Chambe-Eng
 */
public class WindowLogin extends JFrame {
    public WindowLogin() {
        initComponents();
    }

    // Calls LoginUser to actually log in the user
    private void BtnLoginMouseClicked(MouseEvent e) {
        LoginUser();
    }

    // Opens a new window with a registration form
    private void BtnRegisterMouseClicked(MouseEvent e) {
        new WindowRegister().setVisible(true);
        dispose();
    }

    // Exits the program with code 0
    private void BtnQuitMouseClicked(MouseEvent e) {
        System.exit(0);
    }

    private void LoginUser() {
        String username = InputUsr.getText();
        char[] password = InputPwd.getPassword();

        // TODO actually log the user in, currently just opens a default chat window
        new WindowChatting().setVisible(true);
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        AppName = new JLabel();
        InputUsr = new JTextField();
        InputPwd = new JPasswordField();
        BtnLogin = new JButton();
        BtnRegister = new JButton();
        BtnQuit = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("ChatApp");
        Container contentPane = getContentPane();

        //---- AppName ----
        AppName.setText("ChatApp");
        AppName.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 20));
        AppName.setHorizontalAlignment(SwingConstants.CENTER);

        //---- InputUsr ----
        InputUsr.setText("username");

        //---- InputPwd ----
        InputPwd.setText("password");

        //---- BtnLogin ----
        BtnLogin.setText("login");
        BtnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BtnLoginMouseClicked(e);
            }
        });

        //---- BtnRegister ----
        BtnRegister.setText("register");
        BtnRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BtnRegisterMouseClicked(e);
            }
        });

        //---- BtnQuit ----
        BtnQuit.setText("quit");
        BtnQuit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BtnQuitMouseClicked(e);
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap(75, Short.MAX_VALUE)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(BtnQuit)
                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(BtnLogin)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnRegister))
                            .addComponent(InputPwd)
                            .addComponent(InputUsr)
                            .addComponent(AppName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addContainerGap(76, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(50, 50, 50)
                    .addComponent(AppName)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(InputUsr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(InputPwd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(BtnLogin)
                        .addComponent(BtnRegister))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(BtnQuit)
                    .addContainerGap(57, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel AppName;
    private JTextField InputUsr;
    private JPasswordField InputPwd;
    private JButton BtnLogin;
    private JButton BtnRegister;
    private JButton BtnQuit;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
