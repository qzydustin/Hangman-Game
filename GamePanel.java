import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The class is designed to get user input in the field, judge whether it is correct, and draw the HangMan.
 *
 * @author qzydustin
 */
class GamePanel extends JPanel {
    private String word;
    private int wordLength;
    private int guessTime;
    private static final int MAX_GUESS_TIME = 7;

    private JTextField guessField = new JTextField();
    private JTextField letterField = new JTextField();
    private JButton letterButton = new JButton("Submit Letter");
    private JTextField guessedField = new JTextField();
    private JLabel resultLabel = new JLabel();


    /**
     * shows the information of the game, the picture of HangMan
     *
     * @param word which is chosen last step
     */
    GamePanel(String word) {
        // saves the parameter
        this.word = word;
        wordLength = word.length();
        //Remove comment to open test mode
        //System.out.print(word);
        setGuessField();
        JLabel tipLabel = new JLabel();
        tipLabel.setText("The word to guess has " + wordLength + " letters.");
        JPanel topPanel = new JPanel();
        //sets topPanel 9 rows and 1 cols and add
        topPanel.setLayout(new GridLayout(9, 1));
        JLabel guessLabel = new JLabel("Word to Guess");
        topPanel.add(guessLabel);
        topPanel.add(guessField);
        topPanel.add(tipLabel);
        JLabel letterLabel = new JLabel("Guess a letter");
        topPanel.add(letterLabel);
        topPanel.add(letterField);
        topPanel.add(letterButton);
        JLabel guessedLabel = new JLabel("Guessed Letter");
        topPanel.add(guessedLabel);
        topPanel.add(guessedField);
        topPanel.add(resultLabel);
        //sets topPanel at the top of gamePanel
        add(topPanel, BorderLayout.NORTH);
        //sets guessField and guessedField unable
        guessField.setEnabled(false);
        guessedField.setEnabled(false);
        //adds listener to Button
        ActionListener click = new Click();
        letterButton.addActionListener(click);
        //adds listener to keyboard, when there are more than one character in the field and input one character, delete the last one.
        letterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (letterField.getText().length() >= 1) {
                    letterField.setText("");
                }
            }
        });
    }

    /**
     * sets the default guessField
     */
    private void setGuessField() {
        String result = "_";
        for (int i = 1; i < wordLength; i++) {
            result = result.concat(" _");
        }
        guessField.setText(result);
    }

    /**
     * updates the guessField
     *
     * @param location the location of the character which you guess in the word
     * @param myChar   the char which you guess
     */
    private void setGuessField(int location, String myChar) {
        String field = guessField.getText();
        guessField.setText(field.substring(0, location) + myChar + field.substring(location + 1));
    }

    /**
     * sets the character which guessed before to the guessedField
     *
     * @param myChar the character which guessed
     */
    private void setGuessedWord(String myChar) {
        guessedField.setText(guessedField.getText() + " " + myChar);
    }

    /**
     * use a wonderful method to show the picture
     *
     * @param g the picture on the panel
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //x,y are the coordinates of a reference point which is to adapt to changes in window size
        int x = getWidth() / 2 - 200;
        int y = getHeight() - 350;
        /*
        case 7 draws the sixth step: right leg
        case 6 draws the fifth step: left leg
        case 5 draws the fourth step: right arm
        case 4 draws the third step: left arm
        case 3 draws the second step: body
        case 2 draws the first step: head
        case 1 draws the default picture: gallows
         */
        switch (guessTime) {
            case 7:
                g.drawLine(x + 100, y + 155, x + 135, y + 220);
            case 6:
                g.drawLine(x + 100, y + 155, x + 65, y + 220);
            case 5:
                g.drawLine(x + 100, y + 110, x + 125, y + 135);
            case 4:
                g.drawLine(x + 100, y + 110, x + 75, y + 135);
            case 3:
                g.drawLine(x + 100, y + 110, x + 100, y + 155);
            case 2:
                g.drawOval(x + 75, y + 60, 50, 50);
            case 1:
                g.drawLine(x + 100, y + 50, x + 100, y + 60);
                g.drawLine(x + 100, y + 50, x + 200, y + 50);
                g.drawLine(x + 200, y + 50, x + 200, y + 300);
                g.drawLine(x + 80, y + 300, x + 320, y + 300);
            case 0:
        }
    }

    /**
     * guessButton's Click listener
     */
    private class Click implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //determines if the input is regular
            if (isInputRegular()) {
                //Case conversion
                String character = letterField.getText().toLowerCase();
                letterField.setText("");
                setGuessedWord(character);
                //determines if the input is right
                if (isRight(character)) {
                    ifWin();
                } else {
                    guessTime++;
                    repaint();
                    ifEnd();
                }
            }
        }

        /**
         * determines if the game ends
         */
        private void ifEnd() {
            if (guessTime >= MAX_GUESS_TIME) {
                resultLabel.setText("You lose. The word was " + word);
                letterButton.setEnabled(false);
            }

        }

        /**
         * determines if user wins
         */
        private void ifWin() {
            if (guessField.getText().indexOf('_') == -1) {
                resultLabel.setText("You win.");
                letterButton.setEnabled(false);
            }
        }

        /**
         * determines if the input is right
         *
         * @param myChar the character which user inputs
         * @return true means user guesses correctly, false means user guesses incorrectly
         */
        private boolean isRight(String myChar) {
            String tempWord = word;
            int rightLocation = tempWord.indexOf(myChar);
            if (rightLocation == -1) {
                return false;
            }
            while (rightLocation != -1) {
                tempWord = tempWord.replaceFirst(myChar, "*");
                setGuessField(2 * rightLocation, myChar);
                rightLocation = tempWord.indexOf(myChar);
            }
            return true;
        }

        /**
         * determines if the input is regular
         *
         * @return true means input is regular, false means input is irregular
         */
        private boolean isInputRegular() {
            String input = letterField.getText().trim().toLowerCase();
            //if input nothing
            if (input.equals("")) {
                JOptionPane.showMessageDialog(null,
                        "You need to enter ONE LETTER.",
                        "Alert",
                        JOptionPane.ERROR_MESSAGE);
                letterField.setText("");
                return false;
            }
            char ch = input.charAt(0);
            //if input something which is not letter
            if (!Character.isLowerCase(ch)) {
                JOptionPane.showMessageDialog(null,
                        "You need to enter one LETTER.",
                        "Alert",
                        JOptionPane.ERROR_MESSAGE);
                letterField.setText("");
            }
            //if input something which has been chosen before
            else if (guessedField.getText().indexOf(ch) != -1) {
                JOptionPane.showMessageDialog(null,
                        "The character you have chosen before",
                        "Alert",
                        JOptionPane.ERROR_MESSAGE);
                letterField.setText("");
            } else {
                return true;
            }
            return false;
        }

    }
}
