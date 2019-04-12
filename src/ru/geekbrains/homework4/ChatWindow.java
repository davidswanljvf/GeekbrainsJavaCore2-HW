package ru.geekbrains.homework4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatWindow extends JFrame {
    private JTextArea TextZone;
    private JTextField TextField;
    public ChatWindow(){
        setTitle("DS Chat v.1.0");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 500);
        TextZone = new JTextArea();
        TextField = new JTextField();
        TextField.setPreferredSize(new Dimension(200, 20));
        JScrollPane jScrollPane = new JScrollPane(TextZone);
        TextZone.setEditable(false);
        TextZone.setLineWrap(true);
        JButton jButtonSend = new JButton("Отправить");
        JPanel jPanelBottom = new JPanel();
        jPanelBottom.add(TextField, BorderLayout.CENTER);
        jPanelBottom.add(jButtonSend, BorderLayout.EAST);
        add(jScrollPane, BorderLayout.CENTER);
        add(jPanelBottom, BorderLayout.SOUTH);
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                sendText();
            }
        });
        TextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                sendText();
            }
        });
        setVisible(true);
    }
    public void sendText(){
        TextZone.append(TextField.getText() + "\n");
        TextField.setText("");
    }

}