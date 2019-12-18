import javax.swing.*;

/**
 * The class is designed to make a game window.
 *
 * @author qzydustin
 */

class GameFrame extends JFrame {
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 600;

    GameFrame(String word) {
        // adds gamePanel
        add(new GamePanel(word));
        // set size
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

}