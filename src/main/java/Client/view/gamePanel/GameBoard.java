package Client.view.gamePanel;

import Client.ClientSideConnection;
import Client.listtener.StringListener;
import Client.logic.Ship;
import Client.logic.Shoot;
import Client.view.MainPanel;
import Client.view.shufflePanel.ShipPaint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class GameBoard extends JPanel implements MouseListener {
    public boolean click;
    public ClientSideConnection csc;
    public boolean endGame;
    private LinkedList<StringListener> stringListeners;
    private int[] boardInt;
    private int[] boardX;
    private int[] boardY;
    public int block;


    public GameBoard(MainPanel mainPanel, ClientSideConnection csc) {
        this.setLayout(new GridLayout(2, 1));
        this.csc = csc;
        endGame = false;
        this.setBackground(Color.ORANGE);
        boardInt = new int[1];
        boardInt[0] = 0;
        stringListeners = new LinkedList<>();
        addMouseListener(this);
    }

    public boolean isClick() {
        return click;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    public void addListener(StringListener stringListener) {
        stringListeners.add(stringListener);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (click) {
            stringListeners.get(0).stringEventOccurred((e.getX() / block - 1) + "[" + (e.getY() / block - 1));
        }
    }

    public void BoarderPaint(Graphics g) {
        for (int i = 0; i < 13; i++) {
            g.setColor(Color.BLACK);
            g.drawLine(block * i, 0, block * i, 12 * block);
            g.setColor(Color.BLACK);
            g.drawLine(0, block * i, block * 12, i * block);
        }
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, block, block * 12);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, block * 12, block);
        g.setColor(Color.BLACK);
        g.fillRect(11 * block, 0, block, block * 12);
        g.setColor(Color.BLACK);
        g.fillRect(0, 11 * block, 12 * block, block);
    }

    private void BoardPaint(Graphics g) {
        if (boardInt[0] != 0) {
            for (int number = 0; number < boardInt.length; number++) {
                if (boardInt[number] == 1) {
                    g.setColor(Color.BLUE);
                    g.fillRect((boardX[number] + 1) * block, (boardY[number] + 1) * block, block, block);
                }
                if (boardInt[number] == 2) {
                    g.setColor(Color.GREEN);
                    g.fillRect((boardX[number] + 1) * block, (boardY[number] + 1) * block, block, block);
                }
                if (boardInt[number] == 3) {
                    g.setColor(Color.RED);
                    g.fillRect((boardX[number] + 1) * block, (boardY[number] + 1) * block, block, block);
                }

            }
        }
    }

    public void Decode(String s) {
        String[] A = s.split("\\[");
        boardInt = new int[A.length];
        boardX = new int[A.length];
        boardY = new int[A.length];
        int number = 0;
        for (String a : A) {
            boardInt[number] = Integer.valueOf(a.charAt(0) - 48);
            boardX[number] = Integer.valueOf(a.charAt(1) - 48);
            boardY[number] = Integer.valueOf(a.charAt(2) - 48);
            number += 1;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BoardPaint(g);
        BoarderPaint(g);
    }


}
