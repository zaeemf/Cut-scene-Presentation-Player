import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;

import javax.swing.JPanel;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PlayerPanel extends JPanel implements MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	private static Image bg, textbox, exit;
	private static Graphics2D g2d;
	private static File script;
	private static Thread t1= new Thread(new Player());
	private static String scriptName, currentLine;
	private static int x_val = 167, y_val = 484, sx1 = 0, sy1 = 0, sx2 = 166, sy2 = 93;
	private static Boolean event = false, textComplete = true, text = false;
	private static FontMetrics fm;
	private static Font font = new Font("TimesNewRoman", Font.PLAIN, 18);

	public PlayerPanel(File script, String scriptName) {
		setFocusable(true);
		requestFocusInWindow();
		setScript(script);
		setScriptName(scriptName);
		addMouseListener(this);
		addMouseMotionListener(this);
		textbox = (new ImageIcon(getClass().getResource("/Resources/Box.png"))).getImage();
		exit = (new ImageIcon(getClass().getResource("/Resources/exit_both.png"))).getImage();
		t1.start();
	}
	public PlayerPanel() {
		setFocusable(true);
		requestFocusInWindow();
		addMouseListener(this);
		addMouseMotionListener(this);
		textbox = (new ImageIcon(getClass().getResource("/Resources/Box.png"))).getImage();
		exit = (new ImageIcon(getClass().getResource("/Resources/exit_button_both.png"))).getImage();
	}

	public void paint(Graphics g) {
		g.setFont(font);
		fm = g.getFontMetrics();
		g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		g2d.drawImage(bg, 0, 0, this);
		g2d.drawImage(textbox, 0, 0, this);
		g2d.drawImage(exit, 1280-166, 0, 1280, 93, sx1, sy1, sx2, sy2, this);

		if(text){
			g.setColor(new Color(159, 159, 159, 255));
			g2d.drawString(currentLine, x_val, y_val);
		}
		g2d.dispose();
	}
	public void startThread() {
		t1.start();
	}
	public FontMetrics getFm() {
		return fm;
	}

	public void setFm(FontMetrics fm) {
		PlayerPanel.fm = fm;
	}

	public void setImage(Image current){
		bg = current;
	}

	public void setScript(File curScript){
		script = curScript;
	}

	public  File getScript(){
		return script;
	}

	public void setScriptName(String s){
		scriptName = s;
	}

	public String getScriptName(){
		return scriptName;
	}

	public Boolean getEvent() {
		return event;
	}

	public void setEvent(Boolean x) {
		event = x;
	}

	public void setDone(Boolean x){
	}

	public Boolean getTextComplete() {
		return textComplete;
	}

	public void setTextComplete(Boolean textComplete) {
		PlayerPanel.textComplete = textComplete;
	}

	public void setCurrentLine(String currentLine) {
		PlayerPanel.currentLine = currentLine;
	}

	public String getCurrentLine(){
		return currentLine;
	}

	public  Boolean getText() {
		return text;
	}

	public void setText(Boolean text) {
		PlayerPanel.text = text;
	}

	public void setX_val(int x) {
		PlayerPanel.x_val = x;
	}

	public int getX_val(){
		return x_val;
	}

	public void setY_val(int y) {
		PlayerPanel.y_val = y;
	}

	public int getY_val(){
		return y_val;
	}


	////Implementations
	//relevant
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getX() >= (1280-160) && e.getY() <= (93)){
			JFrame confirmbox = new JFrame("Exit");

			if (JOptionPane.showConfirmDialog( confirmbox,"confirm if you Want to Exit","Name of the Application or Title",
					JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
		else{
			setEvent(true);
		}
	}



	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getX() >= (1280-160) && e.getY() <= (93)) {
			sx1 = 160;
			sx2 = 326;
			repaint();
			revalidate();
		}
		else if((e.getX() <= (1280-160) && e.getY() >= (93))){
			sx1 = 0;
			sx2 = 166;
			repaint();
			revalidate();
		}	
	}


}
