import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;

/**
 * 
 * @author z2farooq
 *
 */
public class VideoPlayer implements Runnable {

	private static class ScriptElement{
		public final String imageName;
		public final String scriptLine;
		public final int durationMs;

		public ScriptElement(String imageName, String script, int durationMs) {
			this.imageName = imageName;
			this.scriptLine = script;
			this.durationMs = durationMs;
		}
	}
	
	
	public ArrayList<ScriptElement> script = new ArrayList<>();
	private  String scriptName;
	private boolean runFlag = true;
	private double delta;
	private Thread text;
	
	
	@Override
	public void run() {
		Start.vPlayer.repaint();
		Start.vPlayer.revalidate();

		startUp(Start.vPlayer.getScript());
		loop();
	}
	
	
	private void startUp(File scriptFile) {
		try(FileInputStream scriptStream = new FileInputStream(scriptFile)) {
			script = makeScript(scriptStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scriptName = Start.vPlayer.getScriptName();
	}
	
	
	private void loop(){

		double nextTime = (double) System.nanoTime() / 1e6;
		
		while(runFlag == true){
			double currTime = (double) System.nanoTime() / 1e6;

			if(((Start.vPlayer.getEvent() == true || 
					currTime >= nextTime) && !(script.isEmpty())) && Start.vPlayer.getTextComplete()){
				//current image in script
				changePaint(script.get(0).imageName);

				//text handling
				handleText();

				//time keep
				delta = ((script.get(0).durationMs));
	
				//delta = 10000;
				nextTime += delta;

				//ends event
				Start.vPlayer.setEvent(false);
				
				script.remove(0);
			}

			if(((script.isEmpty() && currTime >= nextTime) || 
					(Start.vPlayer.getEvent() == true && script.isEmpty())) && Start.vPlayer.getTextComplete()){
				Start.vPlayer.setEvent(false);
				runFlag = false;
			}

			else {
				long sleepTime = (long) ((nextTime - currTime)/100);
				// sanity check

				if (sleepTime > 0) {
					// sleep until the next update

					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						// do nothing

						System.out.println("shit went wrong while in the player");
					}
				}
			}
		}

	}

	
	private void handleText(){
			Start.vPlayer.setCurrentLine(script.get(0).scriptLine);
			Start.vPlayer.setText(true);
			text =  new Thread(new TextBox());
			text.start();

	}

	private ArrayList<ScriptElement> makeScript(FileInputStream scriptStream){
		Scanner lineReader = new Scanner(scriptStream);
		ArrayList<ScriptElement> scripts = new ArrayList<>();
		
		while(lineReader.hasNext()) {
			String scriptline = lineReader.nextLine().trim();
			String[] scriptTokens = scriptline.split("[|]");
			scripts.add(new ScriptElement(scriptTokens[0],scriptTokens[1],Integer.parseInt(scriptTokens[2])));
		}
		lineReader.close();
		return scripts;	
	}


	
	private void changePaint(String name) {
		// TODO Auto-generated method stub

		String img = "/Resources/scene/"+scriptName+"/"+name+".png";
		try{
			Start.vPlayer.setImage(new ImageIcon(getClass().getResource(img)).getImage());
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

}
