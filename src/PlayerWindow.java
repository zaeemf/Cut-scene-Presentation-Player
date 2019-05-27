	import javax.swing.JFrame;
	
	//JFrame to hold the Player Panel
	public class PlayerWindow extends JFrame{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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
