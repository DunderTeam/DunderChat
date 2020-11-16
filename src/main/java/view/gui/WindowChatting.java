/*
 * Created by JFormDesigner on Mon Nov 16 17:21:52 CET 2020
 */

package view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Adrian Emil Chambe-Eng
 */
public class WindowChatting extends JFrame {
    public WindowChatting() {
        initComponents();
    }

    // Calls ChatMsgSend() when the user presses enter in the chat field
    private void TxtFieldMsgActionPerformed(ActionEvent e) {
        ChatMsgSend();
    }

    // Calls ChatMsgSend() if the user clicks the send button
    private void BtnSendMouseClicked(MouseEvent e) {
        ChatMsgSend();
    }

    // Quits with exit code 0
    private void BtnQuitMouseClicked(MouseEvent e) {
        System.exit(0);
    }

    // Opens a dialog box to connect to a new User/IP
    // TODO Fix this, currently opens an empty dialog box
    private void BtnNewMouseClicked(MouseEvent e) {
        new view.gui.DialogConnect(this).setVisible(true);
    }

    private void ChatMsgSend() {
        // TODO actually send message and not just display locally
        String sender = "You";
        String message = TxtFieldMsg.getText();
        if (message.length() > 0) {
            TxtAreaChat.append(sender + ": " + message + "\n");
            TxtFieldMsg.setText("");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        ListConversations = new JList<>();
        WindowTitle = new JLabel();
        ScrollPaneChatArea = new JScrollPane();
        TxtAreaChat = new JTextArea();
        TxtFieldMsg = new JTextField();
        BtnSend = new JButton();
        BtnQuit = new JButton();
        BtnNew = new JButton();

        //======== this ========
        setTitle("ChatApp");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(600, 400));
        Container contentPane = getContentPane();

        //---- ListConversations ----
        ListConversations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListConversations.setModel(new AbstractListModel<String>() {
            String[] values = {
                "Chat1",
                "Chat2"
            };
            @Override
            public int getSize() { return values.length; }
            @Override
            public String getElementAt(int i) { return values[i]; }
        });

        //---- WindowTitle ----
        WindowTitle.setText("Conversations");
        WindowTitle.setMinimumSize(new Dimension(60, 15));
        WindowTitle.setMaximumSize(new Dimension(60, 30));
        WindowTitle.setPreferredSize(new Dimension(60, 20));
        WindowTitle.setHorizontalAlignment(SwingConstants.CENTER);
        WindowTitle.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 14));

        //======== ScrollPaneChatArea ========
        {

            //---- TxtAreaChat ----
            TxtAreaChat.setLineWrap(true);
            TxtAreaChat.setWrapStyleWord(true);
            TxtAreaChat.setEditable(false);
            TxtAreaChat.setText("Welcome to ChatApp. Select a conversation from the list or start a new one to begin.");
            ScrollPaneChatArea.setViewportView(TxtAreaChat);
        }

        //---- TxtFieldMsg ----
        TxtFieldMsg.setPreferredSize(new Dimension(6000, 30));
        TxtFieldMsg.setToolTipText("Chat here");
        TxtFieldMsg.addActionListener(e -> TxtFieldMsgActionPerformed(e));

        //---- BtnSend ----
        BtnSend.setText("send");
        BtnSend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BtnSendMouseClicked(e);
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

        //---- BtnNew ----
        BtnNew.setText("new");
        BtnNew.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BtnNewMouseClicked(e);
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(WindowTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                            .addComponent(BtnNew, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BtnQuit, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(ListConversations, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(TxtFieldMsg, GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BtnSend))
                        .addComponent(ScrollPaneChatArea))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(WindowTitle, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ListConversations, GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
                        .addComponent(ScrollPaneChatArea, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(BtnSend)
                        .addComponent(BtnNew)
                        .addComponent(BtnQuit)
                        .addComponent(TxtFieldMsg, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JList<String> ListConversations;
    private JLabel WindowTitle;
    private JScrollPane ScrollPaneChatArea;
    private JTextArea TxtAreaChat;
    private JTextField TxtFieldMsg;
    private JButton BtnSend;
    private JButton BtnQuit;
    private JButton BtnNew;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
