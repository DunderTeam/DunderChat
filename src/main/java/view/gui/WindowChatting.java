/*
 * Created by JFormDesigner on Mon Nov 16 17:21:52 CET 2020
 */

package view.gui;

import controller.chat.Chat;
import controller.chat.ChatManager;
import model.networking.data.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import java.util.List;
import javax.swing.event.*;

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
        TxtFieldMsg.setText("");
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
        connectionDialog.setVisible(true); // here the modal dialog takes over
        connectNewChat();
        TxtFieldAddress.setText("");
    }

    private void connectNewChat() {
        // Gets the text in the user/ip TxtField
        String user = TxtFieldAddress.getText();
        //TODO connect to user and get actual address
        String address = "";
        ChatManager.addChat(user, address, 5555);
        chats.add(ChatManager.getChatById(user, address));
        addConversationToList(user);
    }

    private void ChatMsgSend() {
        // TODO actually send message and not just display locally
        String sender = "You";
        String message = TxtFieldMsg.getText();
        String address = chats.get(ListConversations.getSelectedIndex()).getAddress();

        Message msg = new Message();
        msg.setName(sender);
        msg.setData(message);

        Chat currentChat = chats.get(ListConversations.getSelectedIndex());
        ChatManager.addMessage(currentChat.getName(), currentChat.getAddress(), msg);

        TxtAreaChat.append(sender + ": " + message + "\n");
    }

    //Adds a new conversation to the list on the left
    public void addConversationToList(String name) {
        conversations.add(name);
        ListConversations.updateUI();
    }

    private void ListConversationsValueChanged(ListSelectionEvent e) {
        if (ListConversations.getSelectedIndex() > -1) {
            conversationSelected = true;
        }

        TxtAreaChat.setText("Currently chatting in conversation: " + ListConversations.getSelectedValue() + "\n");

        List<Message> messageHistory =  chats.get(ListConversations.getSelectedIndex()).getListMessages();
        for (int i = 0; i < messageHistory.toArray().length; i ++) {
            String tempSender = messageHistory.get(i).getName();
            String tempContent = messageHistory.get(i).getData();

            TxtAreaChat.append(tempSender + ": " + tempContent + "\n");
        }
    }

    private void BtnConnectMouseClicked(MouseEvent e) {
        connectionDialog.dispose();
    }

    private void TxtFieldAddressActionPerformed(ActionEvent e) {
        connectionDialog.dispose();
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here
    }

    private void initComponents() {
        conversations = new Vector<>();
        chats = new ArrayList<>();
        conversationSelected = false;
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        ListConversations = new JList<>(conversations);
        WindowTitle = new JLabel();
        ScrollPaneChatArea = new JScrollPane();
        TxtAreaChat = new JTextArea();
        TxtFieldMsg = new JTextField();
        BtnSend = new JButton();
        BtnQuit = new JButton();
        BtnNew = new JButton();
        connectionDialog = new JDialog();
        Label = new JLabel();
        TxtFieldAddress = new JTextField();
        BtnConnect = new JButton();

        //======== this ========
        setTitle("ChatApp");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(600, 400));
        Container contentPane = getContentPane();

        //---- ListConversations ----
        ListConversations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListConversations.addListSelectionListener(e -> ListConversationsValueChanged(e));

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
            TxtAreaChat.setPreferredSize(new Dimension(1920, 100));
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
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(BtnNew, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BtnQuit, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addComponent(WindowTitle, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ListConversations, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(TxtFieldMsg, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BtnSend))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ScrollPaneChatArea, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)))
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

        //======== connectionDialog ========
        {
            connectionDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            connectionDialog.setModal(true);
            Container connectionDialogContentPane = connectionDialog.getContentPane();

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

            GroupLayout connectionDialogContentPaneLayout = new GroupLayout(connectionDialogContentPane);
            connectionDialogContentPane.setLayout(connectionDialogContentPaneLayout);
            connectionDialogContentPaneLayout.setHorizontalGroup(
                connectionDialogContentPaneLayout.createParallelGroup()
                    .addGroup(connectionDialogContentPaneLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(connectionDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(BtnConnect, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TxtFieldAddress, GroupLayout.Alignment.LEADING))
                        .addGap(46, 46, 46))
            );
            connectionDialogContentPaneLayout.setVerticalGroup(
                connectionDialogContentPaneLayout.createParallelGroup()
                    .addGroup(connectionDialogContentPaneLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(Label)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtFieldAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnConnect)
                        .addContainerGap(63, Short.MAX_VALUE))
            );
            connectionDialog.pack();
            connectionDialog.setLocationRelativeTo(connectionDialog.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private boolean conversationSelected;
    private Vector<String> conversations;
    private List<Chat> chats;
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
    private JDialog connectionDialog;
    private JLabel Label;
    private JTextField TxtFieldAddress;
    private JButton BtnConnect;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
