/*
 * Created by JFormDesigner on Mon Nov 16 17:20:07 CET 2020
 */

package view.gui;



import controller.Controller;
import model.networking.server.PublicIP;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;

/**
 * @author Adrian Emil Chambe-Eng
 */
public class WindowLogin extends JFrame {
    public WindowLogin() {
        initComponents();
    }

    //Calls controller to try to log in a user
    private void attemptLogin() {
        String username = InputUsr.getText();
        String password = InputPwd.getText();
        String Ip = PublicIP.get().getIp();

        if (username.equals("") || password.equals("")) {
            displayErrorDialog("Could not log in!");
        } else {

            Controller.Login(username, password, Ip); // Log user in to the database

        }
    }

    //Changes the logged in username
    public static void setLoggedInUserName(String user) {
        loggedInUserName.setText(user);
    }

    //Open the chat window with the displayed username that is passed to it
    public void loginUser(String username) {
        new WindowChatting(username).setVisible(true);
        dispose();
    }

    public static void setLoginFail(String status) {
        loginFail.setText(status);
    }

    //Calls controller to register a new user
    private void attemptRegisterNewUser() {
        String usr = txtFieldRegisterUsr.getText();
        String pwd = pwdFieldRegisterPwd.getText();

        if (usr.equals("") || pwd.equals("")) {
            displayErrorDialog("Username or password is blank!");
        } else {
            Controller.RegisterUser(usr, pwd, PublicIP.get().getIp());
            //TODO call controller to register
            System.out.println(usr + " | " + pwd);
            txtFieldRegisterUsr.setText("");
            pwdFieldRegisterPwd.setText("");
            DialogRegister.dispose();
        }
    }

    //Displays an error dialog with the given text
    private void displayErrorDialog(String error) {
        dialogErrorLabel.setText(error);
        dialogError.setVisible(true);
    }

    private void loggedInUserNameChanged(PropertyChangeEvent e) {
        System.out.println("Name Changed");
        loginUser(loggedInUserName.getText());
    }

    private void loginFailChanged(PropertyChangeEvent e) {
        if (!loginFail.getText().equals("")) {
            displayErrorDialog("Login failed: Database error");
        }
        loginFail.setText("");
    }

    private void registerFailChanged(PropertyChangeEvent e) {
        if (!registerFail.getText().equals("")) {
            displayErrorDialog("Failed to register: Database error");
        }
        registerFail.setText("");
    }

    private void newRegisteredUserChanged(PropertyChangeEvent e) {

    }

    //================ Action/Event Listeners ================

    private void InputPwdActionPerformed(ActionEvent e) {
        attemptLogin();
    }

    // Calls LoginUser to actually log in the user
    private void BtnLoginMouseClicked(MouseEvent e) {
        attemptLogin();
    }

    // Opens a new window with a registration form
    private void BtnRegisterMouseClicked(MouseEvent e) {
        DialogRegister.setVisible(true);
    }

    // Exits the program with code 0
    private void BtnQuitMouseClicked(MouseEvent e) {
        Controller.Shutdown();
    }

    //======== Register New User Dialog ========

    private void pwdFieldRegisterPwdActionPerformed(ActionEvent e) {
        attemptRegisterNewUser();
    }

    private void btnRegisterNewUserActionPerformed(ActionEvent e) {
        attemptRegisterNewUser();
    }

    private void dialogRegisterBtnBackActionPerformed(ActionEvent e) {
        DialogRegister.dispose();
    }

    //======== Error Dialog ========

    private void dialogErrorBtnConfirmActionPerformed(ActionEvent e) {
        dialogError.dispose();
    }

    private void initComponents() {
        loggedInUserName = new JLabel();
        loggedInUserName.addPropertyChangeListener(this::loggedInUserNameChanged);

        loginFail = new JLabel();
        loginFail.addPropertyChangeListener(this::loginFailChanged);

        registerFail = new JLabel();
        registerFail.addPropertyChangeListener(this::registerFailChanged);

        newRegisteredUser = new JLabel();
        newRegisteredUser.addPropertyChangeListener(this::newRegisteredUserChanged);
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        AppName = new JLabel();
        InputUsr = new JTextField();
        InputPwd = new JPasswordField();
        BtnLogin = new JButton();
        BtnQuit = new JButton();
        labelUsr = new JLabel();
        labelPwd = new JLabel();
        BtnRegister = new JButton();
        DialogRegister = new JDialog();
        LabelRegister = new JLabel();
        txtFieldRegisterUsr = new JTextField();
        pwdFieldRegisterPwd = new JPasswordField();
        btnRegisterNewUser = new JButton();
        dialogRegisterLabelUsr = new JLabel();
        dialogRegisterLabelPwd = new JLabel();
        dialogRegisterBtnBack = new JButton();
        dialogError = new JDialog();
        dialogErrorLabel = new JLabel();
        dialogErrorBtnConfirm = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("ChatApp");
        setResizable(false);
        Container contentPane = getContentPane();

        //---- AppName ----
        AppName.setText("ChatApp");
        AppName.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 20));
        AppName.setHorizontalAlignment(SwingConstants.CENTER);

        //---- InputPwd ----
        InputPwd.addActionListener(e -> InputPwdActionPerformed(e));

        //---- BtnLogin ----
        BtnLogin.setText("login");
        BtnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BtnLoginMouseClicked(e);
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

        //---- labelUsr ----
        labelUsr.setText("Username");

        //---- labelPwd ----
        labelPwd.setText("Password");

        //---- BtnRegister ----
        BtnRegister.setText("register");
        BtnRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BtnRegisterMouseClicked(e);
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(InputUsr)
                        .addComponent(labelPwd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AppName, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(labelUsr, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(InputPwd, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(BtnQuit, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                .addComponent(BtnLogin, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BtnRegister, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)))
                    .addContainerGap(23, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(AppName)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(labelUsr)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(InputUsr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(labelPwd)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(InputPwd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(BtnLogin)
                        .addComponent(BtnRegister))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(BtnQuit)
                    .addContainerGap(23, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());

        //======== DialogRegister ========
        {
            DialogRegister.setModal(true);
            DialogRegister.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            DialogRegister.setResizable(false);
            Container DialogRegisterContentPane = DialogRegister.getContentPane();

            //---- LabelRegister ----
            LabelRegister.setText("Register a new user");
            LabelRegister.setFont(LabelRegister.getFont().deriveFont(LabelRegister.getFont().getSize() + 4f));

            //---- txtFieldRegisterUsr ----
            txtFieldRegisterUsr.setToolTipText("username");

            //---- pwdFieldRegisterPwd ----
            pwdFieldRegisterPwd.addActionListener(e -> pwdFieldRegisterPwdActionPerformed(e));

            //---- btnRegisterNewUser ----
            btnRegisterNewUser.setText("register");
            btnRegisterNewUser.addActionListener(e -> btnRegisterNewUserActionPerformed(e));

            //---- dialogRegisterLabelUsr ----
            dialogRegisterLabelUsr.setText("Username");
            dialogRegisterLabelUsr.setHorizontalAlignment(SwingConstants.CENTER);

            //---- dialogRegisterLabelPwd ----
            dialogRegisterLabelPwd.setText("Password");
            dialogRegisterLabelPwd.setHorizontalAlignment(SwingConstants.CENTER);

            //---- dialogRegisterBtnBack ----
            dialogRegisterBtnBack.setText("back");
            dialogRegisterBtnBack.addActionListener(e -> dialogRegisterBtnBackActionPerformed(e));

            GroupLayout DialogRegisterContentPaneLayout = new GroupLayout(DialogRegisterContentPane);
            DialogRegisterContentPane.setLayout(DialogRegisterContentPaneLayout);
            DialogRegisterContentPaneLayout.setHorizontalGroup(
                DialogRegisterContentPaneLayout.createParallelGroup()
                    .addGroup(DialogRegisterContentPaneLayout.createSequentialGroup()
                        .addGroup(DialogRegisterContentPaneLayout.createParallelGroup()
                            .addGroup(DialogRegisterContentPaneLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(LabelRegister))
                            .addGroup(DialogRegisterContentPaneLayout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(DialogRegisterContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dialogRegisterLabelUsr, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtFieldRegisterUsr, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                    .addComponent(dialogRegisterLabelPwd, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                    .addComponent(pwdFieldRegisterPwd, GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnRegisterNewUser, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                    .addComponent(dialogRegisterBtnBack, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))))
                        .addContainerGap(24, Short.MAX_VALUE))
            );
            DialogRegisterContentPaneLayout.setVerticalGroup(
                DialogRegisterContentPaneLayout.createParallelGroup()
                    .addGroup(DialogRegisterContentPaneLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(LabelRegister)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dialogRegisterLabelUsr)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFieldRegisterUsr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dialogRegisterLabelPwd)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pwdFieldRegisterPwd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRegisterNewUser)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dialogRegisterBtnBack)
                        .addContainerGap(43, Short.MAX_VALUE))
            );
            DialogRegister.pack();
            DialogRegister.setLocationRelativeTo(DialogRegister.getOwner());
        }

        //======== dialogError ========
        {
            dialogError.setModal(true);
            dialogError.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialogError.setAlwaysOnTop(true);
            dialogError.setTitle("ERROR!");
            dialogError.setResizable(false);
            Container dialogErrorContentPane = dialogError.getContentPane();

            //---- dialogErrorLabel ----
            dialogErrorLabel.setText("Error text");
            dialogErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- dialogErrorBtnConfirm ----
            dialogErrorBtnConfirm.setText("OK");
            dialogErrorBtnConfirm.addActionListener(e -> dialogErrorBtnConfirmActionPerformed(e));

            GroupLayout dialogErrorContentPaneLayout = new GroupLayout(dialogErrorContentPane);
            dialogErrorContentPane.setLayout(dialogErrorContentPaneLayout);
            dialogErrorContentPaneLayout.setHorizontalGroup(
                dialogErrorContentPaneLayout.createParallelGroup()
                    .addGroup(dialogErrorContentPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dialogErrorLabel, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(dialogErrorContentPaneLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(dialogErrorBtnConfirm)
                        .addContainerGap(85, Short.MAX_VALUE))
            );
            dialogErrorContentPaneLayout.setVerticalGroup(
                dialogErrorContentPaneLayout.createParallelGroup()
                    .addGroup(dialogErrorContentPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dialogErrorLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dialogErrorBtnConfirm)
                        .addContainerGap(8, Short.MAX_VALUE))
            );
            dialogError.pack();
            dialogError.setLocationRelativeTo(null);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private static JLabel loggedInUserName;
    private static JLabel loginFail;
    private static JLabel registerFail;
    private static JLabel newRegisteredUser;

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel AppName;
    private JTextField InputUsr;
    private JPasswordField InputPwd;
    private JButton BtnLogin;
    private JButton BtnQuit;
    private JLabel labelUsr;
    private JLabel labelPwd;
    private JButton BtnRegister;
    private JDialog DialogRegister;
    private JLabel LabelRegister;
    private JTextField txtFieldRegisterUsr;
    private JPasswordField pwdFieldRegisterPwd;
    private JButton btnRegisterNewUser;
    private JLabel dialogRegisterLabelUsr;
    private JLabel dialogRegisterLabelPwd;
    private JButton dialogRegisterBtnBack;
    private JDialog dialogError;
    private JLabel dialogErrorLabel;
    private JButton dialogErrorBtnConfirm;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
