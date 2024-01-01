import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NumberGuessingGameGUI extends JFrame {
    private int randomNumber;
    private int attempts;
    private int maxAttempts = 5;

    private JLabel guessLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JButton newGameButton;
    private JLabel feedbackLabel;
    private JTextArea historyArea;
    private JTextArea resultArea;

    private List<String> history;

    public NumberGuessingGameGUI() {
        setTitle("Number Guessing Game");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeGame();

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
    }

    private void initializeGame() {
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1;
        attempts = 0;
        history = new ArrayList<>();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        guessLabel = new JLabel("Enter your guess:");
        guessField = new JTextField(10);
        guessButton = new JButton("Guess");
        newGameButton = new JButton("New Game");
        feedbackLabel = new JLabel(" ");
        historyArea = new JTextArea(10, 20);
        resultArea = new JTextArea(3, 20);
        historyArea.setEditable(false);
        resultArea.setEditable(false);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(guessLabel, constraints);

        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(guessField, constraints);

        constraints.gridx = 1;
        panel.add(guessButton, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        panel.add(feedbackLabel, constraints);

        constraints.gridy = 3;
        panel.add(new JScrollPane(historyArea), constraints);

        constraints.gridy = 4;
        panel.add(new JScrollPane(resultArea), constraints);

        constraints.gridy = 5;
        panel.add(newGameButton, constraints);
        newGameButton.setVisible(false);

        add(panel);

        setComponentStyles();
    }

    private void setComponentStyles() {
        Font font = new Font("Arial", Font.PLAIN, 14);
        guessLabel.setFont(font);
        guessField.setFont(font);
        guessButton.setFont(font);
        feedbackLabel.setFont(font);
        newGameButton.setFont(font);
        historyArea.setFont(font);
        resultArea.setFont(font);
    }

    private void checkGuess() {
        int guess;
        try {
            guess = Integer.parseInt(guessField.getText());
            attempts++;

            String result;
            if (guess == randomNumber) {
                result = "Congratulations! You guessed it right in " + attempts + " attempts.";
                guessButton.setEnabled(false);
                newGameButton.setVisible(true);
            } else if (attempts >= maxAttempts) {
                result = "Out of attempts. The number was " + randomNumber;
                guessButton.setEnabled(false);
                newGameButton.setVisible(true);
            } else if (guess < randomNumber) {
                result = "Too low! Try again.";
                clearInput();
            } else {
                result = "Too high! Try again.";
                clearInput();
            }

            history.add("Guess #" + attempts + ": " + guess + " - " + result);
            updateHistory();
            updateResult(result);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }

    private void clearInput() {
        guessField.setText("");
        feedbackLabel.setText("");
    }

    private void updateHistory() {
        StringBuilder historyText = new StringBuilder();
        for (String entry : history) {
            historyText.append(entry).append("\n");
        }
        historyArea.setText(historyText.toString());
    }

    private void updateResult(String result) {
        resultArea.setText(result);
    }

    private void startNewGame() {
        attempts = 0;
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1;
        guessButton.setEnabled(true);
        newGameButton.setVisible(false);
        guessField.setText("");
        feedbackLabel.setText("");
        history.clear();
        updateHistory();
        updateResult("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGuessingGameGUI().setVisible(true);
            }
        });
    }
}
