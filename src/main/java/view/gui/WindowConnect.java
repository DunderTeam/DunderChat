/*
 * Created by JFormDesigner on Mon Nov 16 21:51:17 CET 2020
 */

package view.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author unknown
 */
public class WindowConnect extends JFrame {
    public WindowConnect() {
        initComponents();
    }

    private void BtnConnectMouseClicked(MouseEvent e) {
        ConnectToUser();
    }

    // Connects to another user of the application, starting a conversation
    private void ConnectToUser() {
        // TODO implement connection code
        // TODO calls CreateNewConversation() in WindowChatting to make a new conversation and add it to the list
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        Label = new JLabel();
        TxtFieldAddress = new JTextField();
        BtnConnect = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();

        //---- Label ----
        Label.setText("New Conversation");

        //---- TxtFieldAddress ----
        TxtFieldAddress.setText("Username/IP");

        //---- BtnConnect ----
        BtnConnect.setText("connect");
        BtnConnect.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BtnConnectMouseClicked(e);
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(46, 46, 46)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(BtnConnect, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TxtFieldAddress, GroupLayout.Alignment.LEADING))
                    .addGap(46, 46, 46))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(Label)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(TxtFieldAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(BtnConnect)
                    .addContainerGap(63, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel Label;
    private JTextField TxtFieldAddress;
    private JButton BtnConnect;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
