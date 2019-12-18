import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class is designed to load the word file, choose a word random, and make a window.
 *
 * @author qzydustin
 */

public class Game {
    public static void main(String[] args) {
        String word = null;
        // tries load the txt to the ArrayList. If fails, show Alert.
        try {
            word = chooseWord(args[0]);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Wrong file path",
                    "Alert",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        // makes a visible window.
        GameFrame frame = new GameFrame(word);
        frame.setTitle("HangMan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Gets a random word from the file
     *
     * @param path location of txt file
     * @return a random word
     */

    private static String chooseWord(String path) throws FileNotFoundException {
        //reads the file to a ArrayList
        Scanner s = new Scanner(new File(path));
        ArrayList<String> list = new ArrayList<>();
        while (s.hasNext()) {
            list.add(s.next());
        }
        s.close();
        //chooses a random word from the ArrayList
        int index = (new Random().nextInt(list.size()));
        return list.get(index);
    }

}
