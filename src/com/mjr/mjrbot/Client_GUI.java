package com.mjr.mjrbot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class Client_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	public static JTextField channel;
	private JTextField sendmessage;

	private JLabel connecttext;
	private JLabel consoletext;
	public static JLabel viewerstext;
	private JLabel sendmessagetext;

	private JToggleButton connect;
	private JButton send;

	private JScrollPane scrollPaneConsole;
	private static JTextArea console;

	private JScrollPane scrollPaneViewers;
	public static JTextArea viewers;

	public static UpdateGUIThread thread = new UpdateGUIThread();

	public Client_GUI() {
		super("MJR Bot - Beam Version"); // Jframe title
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650, 430);
		setLayout(null);
		setResizable(false);

		connect = new JToggleButton("Join Channel");
		channel = new JTextField();
		connecttext = new JLabel("Enter channel to join:");
		consoletext = new JLabel("Console:");
		viewerstext = new JLabel("Viewer List:");
		console = new JTextArea();
		scrollPaneConsole = new JScrollPane(console);
		viewers = new JTextArea();
		scrollPaneViewers = new JScrollPane(viewers);

		send = new JButton("Send Mesage");
		sendmessage = new JTextField();
		sendmessagetext = new JLabel("Message to chat: ");

		console.setEditable(false);
		console.setLineWrap(true);
		viewers.setEditable(false);

		connect.setBounds(140, 25, 120, 25);
		send.setBounds(320, 75, 120, 25);

		channel.setBounds(10, 25, 125, 25);
		sendmessage.setBounds(10, 75, 295, 25);

		connecttext.setBounds(10, 2, 125, 25);
		consoletext.setBounds(10, 100, 125, 25);
		viewerstext.setBounds(450, 10, 125, 25);
		sendmessagetext.setBounds(10, 50, 125, 25);

		scrollPaneConsole.setBounds(10, 120, 430, 270);
		scrollPaneViewers.setBounds(450, 35, 180, 360);

		connect.addActionListener(this);
		send.addActionListener(this);

		add(connect);
		add(send);
		add(channel);
		add(sendmessage);
		add(consoletext);
		add(sendmessagetext);
		add(connecttext);
		add(viewerstext);
		add(scrollPaneConsole);
		add(scrollPaneViewers);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(connect)) {
			if (connect.isSelected()) {
				try {
					MJRBot.joinChannel(channel.getText());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				if (!thread.isAlive())
					thread.start();
				connect.setText("Leave Channel");
				channel.setEnabled(false);
			} else {
				MJRBot.leaveChannel();
				console.setText("");
				viewers.setText("");
				channel.setText("");
				connect.setText("Join Channel");
				channel.setEnabled(true);
				// Leave TODO
			}
		}
		if (event.getSource().equals(send)) {
			MJRBot.bot.sendMessage(sendmessage.getText());
			sendmessage.setText("");
		}
	}

	public static void TextToConsole(String message, String sender) {
		if (sender != null) {
			if (sender.equalsIgnoreCase(MJRBot.bot.getBotName())) {
				console.append("\n" + "[Bot] " + sender + ": " + message);
				console.setCaretPosition(console.getDocument().getLength());
			} else if (MJRBot.bot.isUserMod(sender)) {
				System.out.println(MJRBot.bot.isUserMod(sender));
				console.append("\n" + "[Moderator] " + sender + ": " + message);
				console.setCaretPosition(console.getDocument().getLength());
			} else {
				console.append("\n" + "[User] " + sender + ": " + message);
				console.setCaretPosition(console.getDocument().getLength());
			}
		} else {
			console.append("\n" + "[MJRBot Info] " + message);
			console.setCaretPosition(console.getDocument().getLength());
			return;
		}
	}
}
