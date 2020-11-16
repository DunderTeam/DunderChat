/*
 * Created by JFormDesigner on Mon Nov 16 18:08:01 CET 2020
 */

package view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Adrian Emil Chambe-Eng
 */
public class DialogConnect extends JDialog {
    public DialogConnect(Window owner) {
        super(owner);
        initComponents();
    }

    private void BtnConnectMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        DialogNewChat = new JDialog();
        Label = new JLabel();
        TxtFieldAddress = new JTextField();
        BtnConnect = new JButton();

        //======== DialogNewChat ========
        {
            Container DialogNewChatContentPane = DialogNewChat.getContentPane();

            //---- Label ----
            Label.setText("New Conversation");

            //---- TxtFieldAddress ----
            TxtFieldAddress.setText("username/ip");

            //---- BtnConnect ----
            BtnConnect.setText("connect");
            BtnConnect.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    BtnConnectMouseClicked(e);
                }
            });

            GroupLayout DialogNewChatContentPaneLayout = new GroupLayout(DialogNewChatContentPane);
            DialogNewChatContentPane.setLayout(DialogNewChatContentPaneLayout);
            DialogNewChatContentPaneLayout.setHorizontalGroup(
                DialogNewChatContentPaneLayout.createParallelGroup()
                    .addGroup(DialogNewChatContentPaneLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(DialogNewChatContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(Label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TxtFieldAddress)
                            .addComponent(BtnConnect))
                        .addContainerGap(48, Short.MAX_VALUE))
            );
            DialogNewChatContentPaneLayout.setVerticalGroup(
                DialogNewChatContentPaneLayout.createParallelGroup()
                    .addGroup(DialogNewChatContentPaneLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(Label)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtFieldAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnConnect)
                        .addContainerGap(63, Short.MAX_VALUE))
            );
            DialogNewChat.pack();
            DialogNewChat.setLocationRelativeTo(DialogNewChat.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JDialog DialogNewChat;
    private JLabel Label;
    private JTextField TxtFieldAddress;
    private JButton BtnConnect;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
