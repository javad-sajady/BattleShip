package Client.view.shufflePanel;

import Client.ClientSideConnection;
import Client.logic.Position;
import Client.logic.Ship;
import Client.logic.Ships;
import Client.logic.Shoot;
import Client.view.CentralPanel;
import Client.view.MainPanel;

import java.awt.*;
import java.util.LinkedList;

public class ShipPaint extends CentralPanel {
    private MainPanel mainPanel;
    private LinkedList<Ship> shipList;
    private LinkedList<Shoot> shootList;
    private ClientSideConnection csc;
    public int block;

    public ShipPaint(MainPanel mainPanel, ClientSideConnection csc) {
        super(mainPanel);
        shootList = new LinkedList<>();
        this.mainPanel = mainPanel;
        this.csc = csc;
        shipList = new LinkedList<>();
    }

    public void setShip(String s) {
        csc.sendEvent(s);
        String t = csc.receiveEvent();
        DecodeShip(t);
    }


    public void setBlock(int block) {
        this.block = block;
    }

    public void DecodeShip(String s) {
        if (s.length() > 0) {
            shipList = new LinkedList<>();
            String[] A = s.split("]");
            for (String a : A) {
                String[] B = a.split("\\[");
                Ship ship = new Ship(Integer.valueOf(B[0]), Integer.valueOf(B[1]), Position.valueOf(B[2]), Ships.valueOf(B[3]));
                shipList.add(ship);
            }
        }
        this.repaint();
    }

    protected void paintComponent(Graphics g) {
        this.setVisible(false);
        super.paintComponent(g);
        ShipPaint(g);
        ShootPaint(g);
        BoarderPaint(g);
        this.setVisible(true);
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

    public void ShipPaint(Graphics g) {
        if (shipList.size() > 0) {
            g.setColor(Color.green);
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    for (Ship ship : shipList) {
                        if (x >= ship.getLocationX() && x <= ship.getX() + ship.getLocationX() - 1) {
                            if (y >= ship.getLocationY() && y <= ship.getY() + ship.getLocationY() - 1) {
                                g.setColor(Color.green);
                                g.fillRect((x + 1) * block, (y + 1) * block, block, block);
                            }
                        }
                    }

                }
            }
        }
    }

    public void ShootPaint(Graphics g) {
        for (Shoot shoot : shootList) {
            if (shoot.isResult()) {
                g.setColor(Color.red);
            } else {
                g.setColor(Color.blue);
            }
            g.fillRect((1 + shoot.getX()) * block, (1 + shoot.getY()) * block, block, block);
        }
    }

    public void setShootList(String s) {
        if (s.length() > 0) {
            String[] A = s.split("]");
            shipList = new LinkedList<>();
            for (String a : A) {
                String[] B = a.split("\\[");
                Shoot shoot = new Shoot(Integer.valueOf(B[0]), Integer.valueOf(B[1]));
                shoot.setResult(Boolean.valueOf(B[2]));
                shootList.add(shoot);
            }
        }
        repaint();
    }

    public LinkedList<Shoot> getShootList() {
        return shootList;
    }

    public LinkedList<Ship> getShipList() {
        return shipList;
    }


}
