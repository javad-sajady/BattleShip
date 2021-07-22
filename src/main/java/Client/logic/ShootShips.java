package Client.logic;

import server.Constant;

import java.util.LinkedList;
import java.util.List;

public class ShootShips {
    public List<Shoot> shootList;
    public List<Ship> shipList;
    public int score;

    public ShootShips(List<Ship> shipList) {
        this.shipList = new LinkedList<>();
        shootList = new LinkedList<>();
        score = 0;
        for (Ship ship1 : shipList) {
            this.shipList.add(ship1);
        }
    }

    public List<Shoot> getShootList() {
        return shootList;
    }

    public List<Ship> getShipList() {
        return shipList;
    }

    public void addShoot(Shoot shoot) {
        shootList.add(shoot);
        for (Ship ship : shipList) {
            DestroyedShip(ship);
        }
    }

    public void DestroyedShip(Ship ship) {
        int health = ship.getX() + ship.getY() - 1;
        for (Shoot shoot : shootList) {
            if (shoot.getX() >= ship.getLocationX() && shoot.getX() <= ship.getX() + ship.getLocationX() - 1) {
                if (shoot.getY() >= ship.getLocationY() && shoot.getY() <= ship.getY() + ship.getLocationY() - 1) {
                    health -= 1;
                }
            }
        }
        if (health == 0) {
            ShootRain(ship);
            score += (ship.getX() + ship.getY() - 1);
        }
    }

    public void ShootRain(Ship ship) {
        for (int i = Math.max(ship.getLocationX() - 1, 0); i < Math.min(ship.getLocationX() + ship.getX() + 1, Constant.getPageSize()); i++) {
            for (int j = Math.max(ship.getLocationY() - 1, 0); j < Math.min(ship.getLocationY() + ship.getY() + 1, Constant.getPageSize()); j++) {
                if (!shootList.contains(new Shoot(i, j))) {
                    shootList.add(new Shoot(i, j));
                }
            }
        }
    }

    public int getScore() {
        return score;
    }

    public boolean getResult(Shoot shoot) {
        for (Ship ship : shipList) {
            if (shoot.getX() >= ship.getLocationX() && shoot.getX() <= ship.getX() + ship.getLocationX() - 1) {
                if (shoot.getY() >= ship.getLocationY() && shoot.getY() <= ship.getY() + ship.getLocationY() - 1) {
                    return true;
                }
            }
        }
        return false;
    }

}
