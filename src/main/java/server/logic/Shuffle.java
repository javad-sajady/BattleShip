package server.logic;

import server.Constant;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Shuffle {
    List<Ship> shipList;

    public Shuffle() {
        this.shipList = new LinkedList<>();
    }

    public List<Ship> Make() {
        while (shipList.size() == 0) {
            int t = LogicConstant.getShuffleTry();

            t = CreateShip(Ships.Battleship, t);
            t = CreateShip(Ships.Cruiser, t);
            t = CreateShip(Ships.Destroyer, t);
            t = CreateShip(Ships.Frigate, t);

            if (t == 0 && shipList.size() < 10) {
                shipList.clear();
            }
        }
        return getShipList();
    }

    public Integer random() {
        return (int) Math.floor(Math.random() * Constant.getPageSize());
    }

    public Position randomPos() {
        int position = (int) Math.floor(2 * Math.random());
        if (position == 0) {
            return Position.horizontal;
        } else {
            return Position.vertical;
        }
    }

    public boolean Crash(Ship ship1) {
        boolean ans = false;
        if (ship1.getX() + ship1.getLocationX() >= 10 || ship1.getY() + ship1.getLocationY() >= 10 || ship1.getLocationY() < 0 || ship1.getLocationX() < 0) {
            ans = true;
        }
        if (shipList.size() > 0 && !ans) {
            Rectangle rectangle1 = new Rectangle(ship1.getLocationX() - 1, ship1.getLocationY() - 1, ship1.getX()+2 , ship1.getY()+2 );
            System.out.println("1"+rectangle1.toString());
            for (Ship ship2 : shipList) {
                Rectangle rectangle2 = new Rectangle(ship2.getLocationX(), ship2.getLocationY(), ship2.getX(), ship2.getY());
                Rectangle intersection= rectangle2.intersection(rectangle1);
                if(intersection.getHeight()>0 && intersection.getWidth()>0){
                    ans=true;
                    break;
                }
            }
        }
        return ans;
    }

//    static class Point {
//        int x, y;
//        public Point(int x, int y) {
//            this.x = x;
//            this.y = y;
//        }
//    }
//
//    static boolean doOverlap(Point l1, Point r1, Point l2, Point r2) {
//        if (l1.x == r1.x || l1.y == r1.y || l2.x == r2.x || l2.y == r2.y) {
//            return false;
//        }
//        if (l1.x >= r2.x || l2.x >= r1.x) {
//            return false;
//        }
//        if (r1.y >= l2.y || r2.y >= l1.y) {
//            return false;
//        }
//        return true;
//    }


    public List<Ship> getShipList() {
        return shipList;
    }

    public int CreateShip(Ships ship, int t) {
        for (int i = 0; i < 5 - ship.getSize(); i++) {
            Ship ship1 = new Ship(random(), random(), randomPos(), ship);
            while (Crash(ship1) && t > 0) {
                ship1.setLocation(random(), random(), randomPos());
                t -= 1;
            }
            shipList.add(ship1);
        }
        return t;
    }
}
