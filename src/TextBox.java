import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class TextBox implements Runnable{

	private static String Speech;
	private static double delta = 180;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//System.out.println("running");
		startUp(Start.vPlayer.getCurrentLine());
		loop();
		shutdown();
	}

	private void startUp(String currentLine) {
		// TODO Auto-generated method stub
		Start.vPlayer.setTextComplete(false);
		Speech = currentLine;
		Start.vPlayer.setCurrentLine("");
	}

	private void loop(){
		delta = 25;
		double nextTime = (double) System.nanoTime() / 1e6;
		while(!Speech.isEmpty()){
			double currTime = (double) System.nanoTime() / 1e6;

			if(currTime >= nextTime){
				Start.vPlayer.setCurrentLine(Start.vPlayer.getCurrentLine() + Speech.substring(0, 1));
				Speech = Speech.substring(1);
				if(Start.vPlayer.getEvent()){
					Start.vPlayer.setCurrentLine(Start.vPlayer.getCurrentLine() + Speech.substring(0));
					Speech = "";
					Start.vPlayer.setEvent(false);
				}
				//playSound();
				draw();
				nextTime += delta;
			}

			long sleepTime = (long) ((nextTime - currTime)/100);
			// sanity check

			if (sleepTime > 0) {
				// sleep until the next update

				try {
					//System.out.println("sleeping");

					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// do nothing

					System.out.println("shit went wrong");
				}
			}
		}
	}

	private void shutdown(){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Start.vPlayer.setTextComplete(true);
	}

	private void draw(){
		Start.vPlayer.repaint();
		Start.vPlayer.revalidate();
	}

	public void playSound() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(this.getClass().getResource("/Resources/sounds/Jump.wav").getFile()));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch(Exception ex) {
			System.out.println("Error with TextBox sound.");
			ex.printStackTrace();
		}
	}

}
