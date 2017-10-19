package com.Dice;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
public class DiceGameGUI extends JFrame implements ActionListener{
    /** the game that keeps track of everything */
    private DiceGame theGame;

    /** player score */
    private JLabel scoreLabel;

    /** button to toll */
    private JButton rollButton;

    /** Game message */
    private JLabel messageLabel;

    /** field to enter the guess */
    private JTextField guess;

    /** menu items */
    private JMenuItem quitItem;
    private JMenuItem newGameItem;

    public static void main(String [] args){
        DiceGameGUI gui = new DiceGameGUI ();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Chandler's Dice Game");
        gui.pack();
        gui.setVisible(true);
    }

    public DiceGameGUI (){
        // instantiating an object of the DiceGame
        theGame = new DiceGame();
        setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        // game message
        messageLabel = new JLabel(theGame.getMessage());
        position.gridx = 0;
        position.gridy = 0;
        position.insets = new Insets(5,5,5,5);
        add(messageLabel, position);

        // guess
        guess = new JTextField(1);
        position.fill = GridBagConstraints.HORIZONTAL;
        position.gridx = 0;
        position.gridy = 1;
        add(new JLabel("Guess"), position);
        position.gridx = 1;
        position.gridy = 1;
        position.insets = new Insets(5,5,5,5);
        add(guess, position);

        // display all dice using a for loop
        position = new GridBagConstraints();
        position.gridy = 2;
        position.gridx = 0;
        position.insets = new Insets(10,5,10,5);
        for (int i=1; i<=3; i++){
            add(theGame.getDie(i), position);
            position.gridx++;
        }

        // score
        scoreLabel = new JLabel();
        position = new GridBagConstraints();
        position.gridx = 1;
        position.gridy = 3;
        position.insets = new Insets(5,5,5,5);
        add(scoreLabel, position);

        // roll button
        rollButton = new JButton("roll");
        position.gridx = 1;
        position.gridy = 4;
        add(rollButton, position);

        // associating the rollButton with the actionListener
        rollButton.addActionListener(this);

        setupMenus();
    }

    public void actionPerformed (ActionEvent e){

        //player selected quit from menu
        if (e.getSource() == quitItem){
            System.exit(1);
        }

        //player selected new game from menu
        else if (e.getSource() == newGameItem){
            restart();
        }

        //player clicked on the roll button
        else if (e.getSource() == rollButton) {
            if (checkValidGuess(guess.getText())) {
                theGame.setGuess(Integer.parseInt(guess.getText()));
                theGame.playGame();
            }
        }

        //update the score and the game message
        scoreLabel.setText("Score: " + theGame.getCredits());
        messageLabel.setText(theGame.getMessage());

    }

    private void setupMenus (){
        JMenuBar menus = new JMenuBar();
        setJMenuBar(menus);

        JMenu fileMenu = new JMenu("File");
        menus.add(fileMenu);

        newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(this);
        quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(this);

        fileMenu.add(newGameItem);
        fileMenu.add(quitItem);
    }

    private void restart() {
        theGame.restart();
        guess.setText("");

    }

    private boolean checkValidGuess(String numStr){
        boolean isValid = true;
        try{
            int val = Integer.parseInt(numStr);
            isValid = val >= 1 && val <= 6;
        }
        catch(NumberFormatException e){
            isValid = false;
        }

        // display error message if not a valid integer
        if (!isValid)
            JOptionPane.showMessageDialog(this, "Enter an integer between 1 and 6");

        return isValid;
    }
}

