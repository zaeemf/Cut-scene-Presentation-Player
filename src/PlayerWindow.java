	import javax.swing.JFrame;
	
	//JFrame to hold the Player Panel
	public class PlayerWindow extends JFrame{
		public PlayerWindow(PlayerPanel x){
			add(x);
			setTitle("Cut-scene Player");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(1280, 720);
			setLocationRelativeTo(null);
			setVisible(true);
			setResizable(false);
			requestFocusInWindow();

		}

	}
