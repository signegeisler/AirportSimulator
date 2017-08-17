package log;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Defines a logging window. 
 * 
 * From: http://www.java2s.com/Code/Java/Language-Basics/
 * JavalogLogandWindowJFrameframe.htm
 */
class LogWindow extends JFrame {

	private static final long serialVersionUID = 602520206431060277L;
	private JTextArea textArea = null;
	private JScrollPane pane = null;

	public LogWindow(String title, int width, int height) {
		super(title);
		setSize(width, height);
		textArea = new JTextArea();
		pane = new JScrollPane(textArea);
		getContentPane().add(pane);
		setVisible(true);
	}

	/**
	 * Appends the data to the text area.
	 * @param data	the Logging information data
	 */
	public void showInfo(String data) {
		textArea.append(data);
		this.getContentPane().validate();
	}
}