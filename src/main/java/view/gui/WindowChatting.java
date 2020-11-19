/*
 * Created by JFormDesigner on Mon Nov 16 17:21:52 CET 2020
 */

package view.gui;

import model.chat.Chat;
import model.chat.ChatManager;
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
    public WindowChatting(String username) {
        initComponents();
        setLoggedInUsrName(username);
        getConversations();
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

        if (message == "") {
            setErrorText("Cannot send empty message!");
            dialogError.setVisible(true);
        } else if (ListConversations.getSelectedIndex() < 0) {
            setErrorText("Select a conversation before sending a message!");
            dialogError.setVisible(true);
        } else {
            Message msg = new Message();
            String address = chats.get(ListConversations.getSelectedIndex()).getAddress();

            msg.setName(sender);
            msg.setData(message);

            Chat currentChat = chats.get(ListConversations.getSelectedIndex());
            ChatManager.addMessage(currentChat.getName(), currentChat.getAddress(), msg);

            TxtAreaChat.append(sender + ": " + message + "\n");
        }
    }

    //Adds a new conversation to the list on the left
    public void addConversationToList(String name) {
        conversations.add(name);
        ListConversations.updateUI();
    }

    //Adds a message to local cache of chats
    public void addMessageToConversation(Message msg, Chat chat) {
        for (Chat ch: chats) {
            if (ch.getName().equals(chat.getName()) && ch.getAddress().equals(chat.getAddress())) {
                ch.addMessageToList(msg);
            }
        }
    }

    public void setLoggedInUsrName (String usr) {
        loggedInUser = usr;
        WindowTitle.setText(usr);
    }

    private void getConversations() {
        List<Chat> tempChats = ChatManager.getChatList();

        for (int i = 0; i < tempChats.size(); i++) {
            chats.add(tempChats.get(i));
            addConversationToList(tempChats.get(i).getName());
        }
    }

    private void setErrorText(String error) {
        dialogErrorLabel.setText(error);
    }

    private void deleteUser(String usr) {
        //TODO call controller and delete user, currently only logs out
        logOut();
    }

    private void logOut() {
        System.out.println("Logging out...");
        new WindowLogin().setVisible(true);
        dispose();
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

    // Opens a dialog box to connect to a new User/IP
    private void BtnNewMouseClicked(MouseEvent e) {
        connectionDialog.setVisible(true); // here the modal dialog takes over
        connectNewChat();
        TxtFieldAddress.setText("");
    }

    // Executed when the user clicks on a conversation from the list
    private void ListConversationsValueChanged(ListSelectionEvent e) {
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

    private void SettingLogoutActionPerformed(ActionEvent e) {
        logOut();
    }

    private void SettingQuitActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    private void SettingsProfileChangeUsrActionPerformed(ActionEvent e) {
        changeUsrDialog.setVisible(true);
    }

    private void SettingsProfileChangePwdActionPerformed(ActionEvent e) {
        changePasswordDialog.setVisible(true);
    }

    private void SettingsProfileDeleteUsrActionPerformed(ActionEvent e) {
        dialogDeleteUsr.setVisible(true);
    }

    private void BtnConfirmNewUsrActionPerformed(ActionEvent e) {
        if (TxtFieldNewUsr.getText().equals("")) {
            setErrorText("Username needed!");
            dialogError.setVisible(true);
        } else {
            changeUsrDialog.dispose();
            setLoggedInUsrName(TxtFieldNewUsr.getText());
            TxtFieldNewUsr.setText("");
        }
    }

    private void BtnConfirmChangePwdActionPerformed(ActionEvent e) {
        if (PwdFieldChangePwdNew.getText().equals("") || PwdFieldChangePwdOld.getText().equals("")) {
            setErrorText("Both fields required!");
            dialogError.setVisible(true);
        } else {
            changePasswordDialog.dispose();
            PwdFieldChangePwdOld.setText("");
            PwdFieldChangePwdNew.setText("");
        }
    }

    private void thisMouseClicked(MouseEvent e) {
        System.out.println("Click");
    }

    private void dialogErrorBtnConfirmActionPerformed(ActionEvent e) {
        dialogError.dispose();
    }

    private void btnDeleteUsrYesActionPerformed(ActionEvent e) {
        deleteUser (loggedInUser);
        dialogDeleteUsr.dispose();
    }

    private void btnDeleteUsrNoActionPerformed(ActionEvent e) {
        dialogDeleteUsr.dispose();
    }

    private void initComponents() {
        conversations = new Vector<>();
        chats = new ArrayList<>();
        loggedInUser = "";
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        MenuBar = new JMenuBar();
        SettingsMenu = new JMenu();
        SettingProfile = new JMenu();
        SettingsProfileChangeUsr = new JMenuItem();
        SettingsProfileChangePwd = new JMenuItem();
        SettingsProfileDeleteUsr = new JMenuItem();
        SettingLogout = new JMenuItem();
        SettingQuit = new JMenuItem();
        ListConversations = new JList<>(conversations);
        WindowTitle = new JLabel();
        ScrollPaneChatArea = new JScrollPane();
        TxtAreaChat = new JTextArea();
        TxtFieldMsg = new JTextField();
        BtnSend = new JButton();
        BtnNew = new JButton();
        connectionDialog = new JDialog();
        Label = new JLabel();
        TxtFieldAddress = new JTextField();
        BtnConnect = new JButton();
        changeUsrDialog = new JDialog();
        LabelNewUsr = new JLabel();
        TxtFieldNewUsr = new JTextField();
        BtnConfirmNewUsr = new JButton();
        changePasswordDialog = new JDialog();
        LabelChangePwdOld = new JLabel();
        BtnConfirmChangePwd = new JButton();
        PwdFieldChangePwdOld = new JPasswordField();
        LalebChangePwdNew = new JLabel();
        PwdFieldChangePwdNew = new JPasswordField();
        dialogError = new JDialog();
        dialogErrorLabel = new JLabel();
        dialogErrorBtnConfirm = new JButton();
        dialogDeleteUsr = new JDialog();
        dialogDeleteUsrLabel1 = new JLabel();
        dialogDeleteUsrLabel2 = new JLabel();
        btnDeleteUsrYes = new JButton();
        btnDeleteUsrNo = new JButton();

        //======== this ========
        setTitle("ChatApp");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300, 400));
        setResizable(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                thisMouseClicked(e);
            }
        });
        Container contentPane = getContentPane();

        //======== MenuBar ========
        {

            //======== SettingsMenu ========
            {
                SettingsMenu.setText("Settings");

                //======== SettingProfile ========
                {
                    SettingProfile.setText("Profile Settings");

                    //---- SettingsProfileChangeUsr ----
                    SettingsProfileChangeUsr.setText("Change Username");
                    SettingsProfileChangeUsr.addActionListener(e -> SettingsProfileChangeUsrActionPerformed(e));
                    SettingProfile.add(SettingsProfileChangeUsr);

                    //---- SettingsProfileChangePwd ----
                    SettingsProfileChangePwd.setText("Change Password");
                    SettingsProfileChangePwd.addActionListener(e -> SettingsProfileChangePwdActionPerformed(e));
                    SettingProfile.add(SettingsProfileChangePwd);

                    //---- SettingsProfileDeleteUsr ----
                    SettingsProfileDeleteUsr.setText("Delete User");
                    SettingsProfileDeleteUsr.addActionListener(e -> SettingsProfileDeleteUsrActionPerformed(e));
                    SettingProfile.add(SettingsProfileDeleteUsr);
                }
                SettingsMenu.add(SettingProfile);

                //---- SettingLogout ----
                SettingLogout.setText("Log Out");
                SettingLogout.addActionListener(e -> SettingLogoutActionPerformed(e));
                SettingsMenu.add(SettingLogout);

                //---- SettingQuit ----
                SettingQuit.setText("Quit");
                SettingQuit.addActionListener(e -> SettingQuitActionPerformed(e));
                SettingsMenu.add(SettingQuit);
            }
            MenuBar.add(SettingsMenu);
        }
        setJMenuBar(MenuBar);

        //---- ListConversations ----
        ListConversations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListConversations.addListSelectionListener(e -> ListConversationsValueChanged(e));

        //---- WindowTitle ----
        WindowTitle.setText("Username");
        WindowTitle.setMinimumSize(new Dimension(60, 15));
        WindowTitle.setMaximumSize(new Dimension(60, 30));
        WindowTitle.setPreferredSize(new Dimension(60, 20));
        WindowTitle.setHorizontalAlignment(SwingConstants.CENTER);
        WindowTitle.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 14));

        //======== ScrollPaneChatArea ========
        {
            ScrollPaneChatArea.setPreferredSize(new Dimension(1920, 55));

            //---- TxtAreaChat ----
            TxtAreaChat.setLineWrap(true);
            TxtAreaChat.setWrapStyleWord(true);
            TxtAreaChat.setEditable(false);
            TxtAreaChat.setText("Welcome to ChatApp. Select a conversation from the list or start a new one to begin.");
            TxtAreaChat.setPreferredSize(null);
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
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(BtnNew)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(TxtFieldMsg, GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BtnSend))
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(WindowTitle, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ListConversations, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ScrollPaneChatArea, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)))
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
                            .addComponent(ListConversations, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE))
                        .addComponent(ScrollPaneChatArea, GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(BtnNew)
                        .addComponent(BtnSend)
                        .addComponent(TxtFieldMsg, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());

        //======== connectionDialog ========
        {
            connectionDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            connectionDialog.setModal(true);
            connectionDialog.setResizable(false);
            Container connectionDialogContentPane = connectionDialog.getContentPane();

            //---- Label ----
            Label.setText("Enter Username or IP address");

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
                        .addGroup(connectionDialogContentPaneLayout.createParallelGroup()
                            .addComponent(TxtFieldAddress, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(BtnConnect, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(46, 46, 46))
                    .addGroup(connectionDialogContentPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Label, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                        .addContainerGap())
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
                        .addContainerGap(18, Short.MAX_VALUE))
            );
            connectionDialog.pack();
            connectionDialog.setLocationRelativeTo(connectionDialog.getOwner());
        }

        //======== changeUsrDialog ========
        {
            changeUsrDialog.setResizable(false);
            Container changeUsrDialogContentPane = changeUsrDialog.getContentPane();

            //---- LabelNewUsr ----
            LabelNewUsr.setText("Enter New Username");

            //---- BtnConfirmNewUsr ----
            BtnConfirmNewUsr.setText("confirm");
            BtnConfirmNewUsr.addActionListener(e -> BtnConfirmNewUsrActionPerformed(e));

            GroupLayout changeUsrDialogContentPaneLayout = new GroupLayout(changeUsrDialogContentPane);
            changeUsrDialogContentPane.setLayout(changeUsrDialogContentPaneLayout);
            changeUsrDialogContentPaneLayout.setHorizontalGroup(
                changeUsrDialogContentPaneLayout.createParallelGroup()
                    .addGroup(changeUsrDialogContentPaneLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(changeUsrDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(LabelNewUsr, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TxtFieldNewUsr)
                            .addComponent(BtnConfirmNewUsr, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(37, Short.MAX_VALUE))
            );
            changeUsrDialogContentPaneLayout.setVerticalGroup(
                changeUsrDialogContentPaneLayout.createParallelGroup()
                    .addGroup(changeUsrDialogContentPaneLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(LabelNewUsr)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtFieldNewUsr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnConfirmNewUsr)
                        .addContainerGap(23, Short.MAX_VALUE))
            );
            changeUsrDialog.pack();
            changeUsrDialog.setLocationRelativeTo(changeUsrDialog.getOwner());
        }

        //======== changePasswordDialog ========
        {
            changePasswordDialog.setResizable(false);
            Container changePasswordDialogContentPane = changePasswordDialog.getContentPane();

            //---- LabelChangePwdOld ----
            LabelChangePwdOld.setText("Enter old password");

            //---- BtnConfirmChangePwd ----
            BtnConfirmChangePwd.setText("confirm");
            BtnConfirmChangePwd.addActionListener(e -> BtnConfirmChangePwdActionPerformed(e));

            //---- LalebChangePwdNew ----
            LalebChangePwdNew.setText("Enter new pasword");

            GroupLayout changePasswordDialogContentPaneLayout = new GroupLayout(changePasswordDialogContentPane);
            changePasswordDialogContentPane.setLayout(changePasswordDialogContentPaneLayout);
            changePasswordDialogContentPaneLayout.setHorizontalGroup(
                changePasswordDialogContentPaneLayout.createParallelGroup()
                    .addGroup(changePasswordDialogContentPaneLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(changePasswordDialogContentPaneLayout.createParallelGroup()
                            .addComponent(BtnConfirmChangePwd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PwdFieldChangePwdNew, GroupLayout.Alignment.TRAILING)
                            .addGroup(changePasswordDialogContentPaneLayout.createSequentialGroup()
                                .addGroup(changePasswordDialogContentPaneLayout.createParallelGroup()
                                    .addGroup(changePasswordDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(LabelChangePwdOld, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(PwdFieldChangePwdOld))
                                    .addComponent(LalebChangePwdNew))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(46, 46, 46))
            );
            changePasswordDialogContentPaneLayout.setVerticalGroup(
                changePasswordDialogContentPaneLayout.createParallelGroup()
                    .addGroup(changePasswordDialogContentPaneLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(LabelChangePwdOld)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PwdFieldChangePwdOld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LalebChangePwdNew)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PwdFieldChangePwdNew, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnConfirmChangePwd)
                        .addContainerGap(15, Short.MAX_VALUE))
            );
            changePasswordDialog.pack();
            changePasswordDialog.setLocationRelativeTo(changePasswordDialog.getOwner());
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

        //======== dialogDeleteUsr ========
        {
            dialogDeleteUsr.setTitle("Warning!");
            dialogDeleteUsr.setResizable(false);
            dialogDeleteUsr.setModal(true);
            dialogDeleteUsr.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            Container dialogDeleteUsrContentPane = dialogDeleteUsr.getContentPane();

            //---- dialogDeleteUsrLabel1 ----
            dialogDeleteUsrLabel1.setText("This will delete your user");
            dialogDeleteUsrLabel1.setHorizontalAlignment(SwingConstants.CENTER);

            //---- dialogDeleteUsrLabel2 ----
            dialogDeleteUsrLabel2.setText("Are you sure?");
            dialogDeleteUsrLabel2.setHorizontalAlignment(SwingConstants.CENTER);

            //---- btnDeleteUsrYes ----
            btnDeleteUsrYes.setText("YES");
            btnDeleteUsrYes.addActionListener(e -> btnDeleteUsrYesActionPerformed(e));

            //---- btnDeleteUsrNo ----
            btnDeleteUsrNo.setText("NO");
            btnDeleteUsrNo.addActionListener(e -> btnDeleteUsrNoActionPerformed(e));

            GroupLayout dialogDeleteUsrContentPaneLayout = new GroupLayout(dialogDeleteUsrContentPane);
            dialogDeleteUsrContentPane.setLayout(dialogDeleteUsrContentPaneLayout);
            dialogDeleteUsrContentPaneLayout.setHorizontalGroup(
                dialogDeleteUsrContentPaneLayout.createParallelGroup()
                    .addGroup(dialogDeleteUsrContentPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(dialogDeleteUsrContentPaneLayout.createParallelGroup()
                            .addComponent(dialogDeleteUsrLabel2, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(dialogDeleteUsrLabel1, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(dialogDeleteUsrContentPaneLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnDeleteUsrYes, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteUsrNo, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(18, Short.MAX_VALUE))
            );
            dialogDeleteUsrContentPaneLayout.setVerticalGroup(
                dialogDeleteUsrContentPaneLayout.createParallelGroup()
                    .addGroup(dialogDeleteUsrContentPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dialogDeleteUsrLabel1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dialogDeleteUsrLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(dialogDeleteUsrContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDeleteUsrYes)
                            .addComponent(btnDeleteUsrNo))
                        .addContainerGap(19, Short.MAX_VALUE))
            );
            dialogDeleteUsr.pack();
            dialogDeleteUsr.setLocationRelativeTo(dialogDeleteUsr.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private Vector<String> conversations;
    private List<Chat> chats;
    private String loggedInUser;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JMenuBar MenuBar;
    private JMenu SettingsMenu;
    private JMenu SettingProfile;
    private JMenuItem SettingsProfileChangeUsr;
    private JMenuItem SettingsProfileChangePwd;
    private JMenuItem SettingsProfileDeleteUsr;
    private JMenuItem SettingLogout;
    private JMenuItem SettingQuit;
    private JList<String> ListConversations;
    private JLabel WindowTitle;
    private JScrollPane ScrollPaneChatArea;
    private JTextArea TxtAreaChat;
    private JTextField TxtFieldMsg;
    private JButton BtnSend;
    private JButton BtnNew;
    private JDialog connectionDialog;
    private JLabel Label;
    private JTextField TxtFieldAddress;
    private JButton BtnConnect;
    private JDialog changeUsrDialog;
    private JLabel LabelNewUsr;
    private JTextField TxtFieldNewUsr;
    private JButton BtnConfirmNewUsr;
    private JDialog changePasswordDialog;
    private JLabel LabelChangePwdOld;
    private JButton BtnConfirmChangePwd;
    private JPasswordField PwdFieldChangePwdOld;
    private JLabel LalebChangePwdNew;
    private JPasswordField PwdFieldChangePwdNew;
    private JDialog dialogError;
    private JLabel dialogErrorLabel;
    private JButton dialogErrorBtnConfirm;
    private JDialog dialogDeleteUsr;
    private JLabel dialogDeleteUsrLabel1;
    private JLabel dialogDeleteUsrLabel2;
    private JButton btnDeleteUsrYes;
    private JButton btnDeleteUsrNo;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
