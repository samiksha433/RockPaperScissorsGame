import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.sound.sampled.*;
import java.io.*;

public class RockPaperScissorsGUI extends JFrame {
    private JButton btnRock, btnPaper, btnScissors, btnRestart;
    private JLabel playerImageLabel, computerImageLabel, resultLabel, scoreLabel;

    private int playerScore = 0;
    private int computerScore = 0;
    private final int MAX_SCORE = 5;

    public RockPaperScissorsGUI() {
        setTitle("Rock Paper Scissors - Image Game");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set background panel
        setContentPane(new BackgroundPanel("background1.jpg"));
        setLayout(new BorderLayout());

        // NORTH Panel
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(new Color(0, 0, 0, 100)); // semi-transparent black

        resultLabel = new JLabel("Choose your move!", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        resultLabel.setForeground(Color.WHITE); // white text
        topPanel.add(resultLabel);

        scoreLabel = new JLabel("Score: Player 0 - 0 Computer", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        scoreLabel.setForeground(Color.WHITE); // white text
        topPanel.add(scoreLabel);
        add(topPanel, BorderLayout.NORTH);

        // CENTER Panel
        JPanel imagePanel = new JPanel(new GridLayout(1, 2));
        imagePanel.setOpaque(false);
        playerImageLabel = new JLabel();
        computerImageLabel = new JLabel();
        playerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        computerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(playerImageLabel);
        imagePanel.add(computerImageLabel);
        add(imagePanel, BorderLayout.CENTER);

        // SOUTH Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);

        btnRock = new JButton(resizeIcon("rock.png", 80, 80));
        btnPaper = new JButton(resizeIcon("paper.png", 80, 80));
        btnScissors = new JButton(resizeIcon("scissors.png", 80, 80));
        btnRestart = new JButton("Restart");

        makeButtonTransparent(btnRock);
        makeButtonTransparent(btnPaper);
        makeButtonTransparent(btnScissors);
        makeButtonTransparent(btnRestart);

        buttonPanel.add(btnRock);
        buttonPanel.add(btnPaper);
        buttonPanel.add(btnScissors);
        buttonPanel.add(btnRestart);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        btnRock.addActionListener(e -> play("Rock"));
        btnPaper.addActionListener(e -> play("Paper"));
        btnScissors.addActionListener(e -> play("Scissors"));

        btnRestart.addActionListener(e -> {
            playerScore = 0;
            computerScore = 0;
            resultLabel.setText("Choose your move!");
            updateScoreLabel();
            playerImageLabel.setIcon(null);
            computerImageLabel.setIcon(null);
            enableButtons(true);
        });

        setVisible(true);
    }

    private void play(String playerChoice) {
        if (playerScore >= MAX_SCORE || computerScore >= MAX_SCORE) {
            return;
        }

        String[] choices = {"Rock", "Paper", "Scissors"};
        String computerChoice = choices[new Random().nextInt(3)];

        playerImageLabel.setIcon(resizeIcon(playerChoice.toLowerCase() + ".png", 120, 120));
        computerImageLabel.setIcon(resizeIcon(computerChoice.toLowerCase() + ".png", 120, 120));

        String result = determineWinner(playerChoice, computerChoice);
        resultLabel.setText(result);
        updateScoreLabel();

        if (playerScore >= MAX_SCORE) {
            resultLabel.setText("🎉 You won the game!");
            enableButtons(false);
            playGameOverSound();
        } else if (computerScore >= MAX_SCORE) {
            resultLabel.setText("💻 Computer won the game!");
            enableButtons(false);
            playGameOverSound();
        }
    }

    private String determineWinner(String player, String computer) {
        if (player.equals(computer)) return "It's a Draw!";
        if ((player.equals("Rock") && computer.equals("Scissors")) ||
            (player.equals("Paper") && computer.equals("Rock")) ||
            (player.equals("Scissors") && computer.equals("Paper"))) {
            playerScore++;
            return "You Win!";
        }
        computerScore++;
        return "Computer Wins!";
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: Player " + playerScore + " - " + computerScore + " Computer");
    }

    private void enableButtons(boolean enabled) {
        btnRock.setEnabled(enabled);
        btnPaper.setEnabled(enabled);
        btnScissors.setEnabled(enabled);
    }

    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private void makeButtonTransparent(JButton button) {
        button.setBorder(null);
        button.setContentAreaFilled(false);
    }

    private void playGameOverSound() {
        try {
            File soundFile = new File("gameover.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error playing sound: " + ex.getMessage());
        }
    }

    // Background Panel Class
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                System.out.println("Background image not found.");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null)
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        new RockPaperScissorsGUI();
    }
}
