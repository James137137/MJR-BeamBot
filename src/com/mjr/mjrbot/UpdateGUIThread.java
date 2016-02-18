package com.mjr.mjrbot;

public class UpdateGUIThread extends Thread {

	@Override
	public void run() {
		while (true) {
			Client_GUI.viewerstext.setText("Viewer List: " + MJRBot.bot.getViewers().size());
			Client_GUI.viewers.setText("");
			for (int i = 0; i < MJRBot.bot.getViewers().size(); i++) {
				Client_GUI.viewers.append(MJRBot.bot.getViewers().get(
						i)
						+ "\n");
			}
			Client_GUI.viewers.setCaretPosition(0);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
