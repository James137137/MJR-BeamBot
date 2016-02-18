package com.mjr.mjrbot;

import java.util.concurrent.ExecutionException;

import com.mjr.mjrbeam.MJR_BeamBot;

public class MJRBot extends MJR_BeamBot {

	public final static MJRBot bot = new MJRBot();

	public static void main(final String[] args) {
		Client_GUI gui = new Client_GUI();
		gui.setVisible(true);
	}

	public static void joinChannel(String channel) throws InterruptedException, ExecutionException {
		bot.setdebug(true);
		bot.connect(channel, AccountSettings.username, AccountSettings.password);
		if (bot.isConnected() && bot.isAuthenticated()) {
			Client_GUI.TextToConsole("MJRBot is Connected & Authenticated to Beam!", null);
			//bot.sendMessage("MJRBot Connected!");
		}
	}

	public static void leaveChannel() {
		bot.disconnect();
	}

	@Override
	protected void onMessage(String sender, String message) {
		System.out.println("here");
		Client_GUI.TextToConsole(message, sender);
		if(message.equalsIgnoreCase("test")){
			bot.deleteUserMessages(sender);
		}
	}

	@Override
	protected void onJoin(String sender) {
		Client_GUI.TextToConsole(sender + " has joined!", null);
		bot.addViewer(sender);
	}

	@Override
	protected void onPart(String sender) {
		Client_GUI.TextToConsole(sender + " has left!", null);
		bot.removeViewer(sender);
	}
}
