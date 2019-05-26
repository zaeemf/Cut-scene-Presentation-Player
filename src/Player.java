	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.util.Stack;

	import javax.swing.ImageIcon;

	public class Player implements Runnable {//Problem in Exe, not showing images, problem may stem from num variable/stack.pop()

		public static Stack<String> script = new Stack<String>();
		private static String scriptName;
		private static Boolean runFlag = true;
		private static double delta;
		private static Thread text;
		@Override
		public void run() {
			Start.vPlayer.repaint();
			Start.vPlayer.revalidate();
			
			startUp(Start.vPlayer.getScript());
			loop();
			shutdown();

		}

		private void startUp(File scriptFile) {
			try {
				FileInputStream scriptStream = new FileInputStream(scriptFile);
				script = makeScript(script, scriptStream);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scriptName = Start.vPlayer.getScriptName();
		}

		private void loop(){

			double nextTime = (double) System.nanoTime() / 1e6;

			while(runFlag == true){
				double currTime = (double) System.nanoTime() / 1e6;
				//System.out.println("looping");

				if(((Start.vPlayer.getEvent() == true || currTime >= nextTime) && !(script.empty())) && Start.vPlayer.getTextComplete()){
					//System.out.println("if init");

					//current image in script
//					System.out.println("current image pushing script: " + script.peek());
					draw(script.pop());

					//text handling
					TextHandling();

					//time keep
//					System.out.println("tiem delay pushing script: " + script.peek());
					delta = (Double.parseDouble(script.pop()));
					//delta = 10000;
					nextTime += delta;

					//ends event
					Start.vPlayer.setEvent(false);
				}

				if(((script.empty() && currTime >= nextTime) || (Start.vPlayer.getEvent() == true && script.empty())) && Start.vPlayer.getTextComplete()){
					Start.vPlayer.setEvent(false);
					runFlag = false;
				}

				else {
					//System.out.println("Else init");

					long sleepTime = (long) ((nextTime - currTime)/100);
					// sanity check

					if (sleepTime > 0) {
						//System.out.println("ifinElse init");
						// sleep until the next update

						try {
							//System.out.println("sleeping");

							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
							// do nothing

							System.out.println("shit went wrong while in the player");
						}
					}
				}
			}

		}

		private void shutdown() {
			// TODO Auto-generated method stub
			draw("END");
			Start.vPlayer.setDone(true);
		}

		private void TextHandling(){
//			System.out.println("Text handle: pushing script: " + script.peek());
			if(script.peek() == "empty"){
				script.pop();
			}

			else{
				Start.vPlayer.setCurrentLine(script.pop());
				Start.vPlayer.setText(true);
				text =  new Thread(new TextBox());
				text.start();
			}

		}

		private Stack<String> makeScript(Stack<String> script, FileInputStream scriptStream){
			return cutString(makeString("", scriptStream), script, false);
		}
		
		//converts txt input file into a string
		private String makeString(String cur, FileInputStream scriptStream){
			try {
				if(scriptStream.available() == 0){
					//System.out.println(cur);
					return cur;
				}
				else{
					cur += (char)scriptStream.read();
					return makeString(cur, scriptStream);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return cur;
			}
		}
		
		
		//cuts the string into portions to enter a stack each section denoted in the input txt file by a '|' symbol, each section denoting a relevant piece of information 
		private Stack<String> cutString(String scriptString, Stack<String> script, Boolean end){
			int index = (scriptString.length() - 1),oldIndex = (index + 1);
//			System.out.println(index);
//			System.out.println(scriptString.charAt(index));
			Boolean stop = false;

			while(stop == false){
//				System.out.println("while run " + index);
				if((0 >= index) || (scriptString.charAt(index) == '|')){
//					System.out.println("stop true");
					stop = true;
					if(0 >= index){
//						System.out.println("end True");
						end = true;
					}
				}
				else {
					index--;
				}
			}

			if(end==true){
				script.push(scriptString.substring((index), oldIndex));
//				System.out.println("pushing script: " + script.peek());
				return script;
			}
			script.push(scriptString.substring((index+1), oldIndex));
//			System.out.println("pushing script: " + script.peek());
			oldIndex = index;
			return cutString((scriptString.substring(0, index)), script, end);

		}	

		private void draw(String current) {
			// TODO Auto-generated method stub
//			System.out.println("pushing script: " + script.peek());
			changePaint(current);
			draw();	
			//start a thread that plays text
		}

		private void draw(){
			Start.vPlayer.repaint();
			Start.vPlayer.revalidate();
		}

		private void changePaint(String num) {
			// TODO Auto-generated method stub
			//use x to grab image from file and load it onto image variable using .setImage()

			String img = "/Resources/scene/"+scriptName+"/"+scriptName+"-"+num+".png";
			//System.out.println(x);
			Start.vPlayer.setImage(new ImageIcon(getClass().getResource(img)).getImage());
		}

	}
