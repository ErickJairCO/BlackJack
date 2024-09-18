package juego.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import juego.card.Card;
import juego.blackjack.Blackjack;

public class InterfazBlackjack {
    private JFrame frame;
    private JPanel gamePanel;
    private JButton hitButton, stayButton;

    private Blackjack blackjack;  // Referencia a la lógica del juego

    // Tamaño del tablero y cartas
    private int boardWidth = 600;
    private int boardHeight = boardWidth;
    private int cardWidth = 110;
    private int cardHeight = 154;

    public InterfazBlackjack(Blackjack blackjack) {
        this.blackjack = blackjack;  // Recibe la lógica del juego
        createUI();
    }

    private void createUI() {
        // Configuración de la ventana
        frame = new JFrame("BlackJack");
        gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGame(g);  // Llamar a la función que dibuja las cartas
            }
        };

        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");

        gamePanel.setLayout(new BorderLayout());
        frame.add(gamePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(stayButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(boardWidth, boardHeight + 100);  // Ajuste del tamaño del frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Añadir funcionalidad a los botones
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!blackjack.hit()) {
                    hitButton.setEnabled(false);  // Deshabilitar botón si el jugador se pasa
                }
                gamePanel.repaint();
            }
        });

        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                blackjack.stay();
                gamePanel.repaint();
            }
        });
    }

    private void drawGame(Graphics g) {
        try {
            // Dibujar la carta oculta del dealer
            Image hiddenCardImg = new ImageIcon(getClass().getResource("/juego/images/BACK.png")).getImage();
            if (!stayButton.isEnabled()) {
                Card hiddenCard = blackjack.getCartaOculta();
                hiddenCardImg = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
            }
            g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

            // Dibujar las cartas del dealer
            ArrayList<Card> dealerHand = blackjack.getManoDealer();
            for (int i = 0; i < dealerHand.size(); i++) {
                Card card = dealerHand.get(i);
                Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5) * i, 20, cardWidth, cardHeight, null);
            }

            // Dibujar las cartas del jugador
            ArrayList<Card> playerHand = blackjack.getManoJugador();
            for (int i = 0; i < playerHand.size(); i++) {
                Card card = playerHand.get(i);
                Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                g.drawImage(cardImg, 20 + (cardWidth + 5) * i, 320, cardWidth, cardHeight, null);
            }

            // Si el jugador ha presionado "Stay", evaluar quién gana
            if (!stayButton.isEnabled()) {
                int dealerSum = blackjack.getDealerSuma();
                int playerSum = blackjack.getJugadorSuma();
                
                String message = determineWinner(dealerSum, playerSum);
                
                // Dibujar el mensaje en pantalla
                g.setFont(new Font("Arial", Font.PLAIN, 30));
                g.setColor(Color.white);
                g.drawString(message, 220, 250);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String determineWinner(int dealerSum, int playerSum) {
        if (playerSum > 21) {
            return "You Lose!";
        } else if (dealerSum > 21) {
            return "You Win!";
        } else if (playerSum == dealerSum) {
            return "Tie!";
        } else if (playerSum > dealerSum) {
            return "You Win!";
        } else {
            return "You Lose!";
        }
    }

    public void updateUI() {
        gamePanel.repaint();
    }
}
