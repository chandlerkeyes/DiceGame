package com.Dice;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class GVdie extends JPanel implements MouseListener, Comparable {
    /** current value of the die */
    private int myValue, displayValue;

    /** is the dice currently on hold? */
    private boolean held;

    /** current size in pixels */
    private int mySize;

    /** dot size in pixels defined by overall die size */
    private int dotSize;

    /** offset in pixels for the left row of dots */
    private int left;

    /** offset in pixels for the right row of dots */
    private int right;

    /** offset in pixels for the middle dot */
    private int middle;

    /** color of the dice when held */
    private Color HELD_COLOR = Color.pink;

    /** default color of dice */
    private Color BACKGROUND = Color.white;

    /** repeats for animation */
    private int NUM_ROLLS;

    /** Timer for animation */
    private javax.swing.Timer myTimer;


    /*****************************************************************
     constructor creates a die of specified size X size pixels

     @param size the length of each side in pixels
     *****************************************************************/
    public GVdie(int size) {
// initialize the die and determine display characteristics
        mySize = size;
        held = false;
        dotSize = mySize / 5;
        int spacer = (mySize - (3*dotSize))/4;
        left = spacer;
        right = mySize - spacer - dotSize;
        middle = (mySize - dotSize) /2;
        setBackground(BACKGROUND);
        setForeground(Color.black);
        setSize(size,size);
        setPreferredSize(new Dimension(size, size));
        setMinimumSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));

// create the fancy border
        Border raised = BorderFactory.createRaisedBevelBorder();
        Border lowered = BorderFactory.createLoweredBevelBorder();
        Border compound = BorderFactory.createCompoundBorder(raised, lowered);
        setBorder(compound);

// set default values
        displayValue = myValue = (int) (Math.random()*6)+1;
        setNumRolls(6);
        myTimer = new javax.swing.Timer(250, new Animator());
        addMouseListener(this);
    }

    public GVdie() {
        this(100);
    }

    public boolean isHeld(){
        return held;
    }
    public void setBlank(){
        displayValue = 0;
        repaint();
    }

    public void setHeld(boolean h){
        held = h;
        if(held){
            setBackground(HELD_COLOR);
        }else{
            setBackground(BACKGROUND);
        }
        repaint();
    }

    public void setForeground(Color c){
        super.setForeground(c);
    }

    public void roll (){
        myValue = (int) (Math.random()*6)+1;

        // start the animated roll
        myTimer.restart();

    }

    public void setDelay (int msec){
        if (msec > 0)
            myTimer = new javax.swing.Timer(msec, new Animator());
    }

    public void setNumRolls (int num){
        NUM_ROLLS = 0;
        if (num > 0)
            NUM_ROLLS = num;
    }
    public int getValue(){
        return myValue;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

// paint dots
        switch (displayValue){
            case 1:
                g.fillOval (middle,middle,dotSize,dotSize);
                break;
            case 2:
                g.fillOval (left,left,dotSize,dotSize);
                g.fillOval (right,right,dotSize,dotSize);
                break;
            case 3:
                g.fillOval (middle,left,dotSize,dotSize);
                g.fillOval (middle,middle,dotSize,dotSize);
                g.fillOval (middle,right,dotSize,dotSize);
                break;
            case 5:     g.fillOval (middle,middle,dotSize,dotSize);
                // fall throught and paint four more dots
            case 4:
                g.fillOval (left,left,dotSize,dotSize);
                g.fillOval (left,right,dotSize,dotSize);
                g.fillOval (right,left,dotSize,dotSize);
                g.fillOval (right,right,dotSize,dotSize);
                break;
            case 6:
                g.fillOval (left,left,dotSize,dotSize);
                g.fillOval (left,middle,dotSize,dotSize);
                g.fillOval (left,right,dotSize,dotSize);
                g.fillOval (right,left,dotSize,dotSize);
                g.fillOval (right,middle,dotSize,dotSize);
                g.fillOval (right,right,dotSize,dotSize);
                break;
        }

    }
    public void mouseClicked(MouseEvent e){
        if(held){
            held = false;
            setBackground(BACKGROUND);
        }else{
            held = true;
            setBackground(HELD_COLOR);
        }
        repaint();

    }
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public int compareTo(Object o){
        GVdie d = (GVdie) o;
        return getValue() - d.getValue();
    }
    private class Animator implements ActionListener{
        int count = 0;
        public void actionPerformed(ActionEvent e){
            displayValue = (int) (Math.random()*6)+1;
            repaint();
            count++;
            // Should we stop rolling?
            if (count == NUM_ROLLS){
                count=0;
                myTimer.stop();
                displayValue = myValue;
                repaint();
            }
        }
    }


}