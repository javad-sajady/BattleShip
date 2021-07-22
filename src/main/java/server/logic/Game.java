package server.logic;

import java.util.LinkedList;
import java.util.List;

public class Game {
    private Account played1;
    private Account played2;
    private Integer turn;
    private Integer Score1;
    private Integer Score2;
    private Integer ready;
    private List<Ship> player1ShipList;
    private List<Ship> player2ShipList;
    private int remainTime;
    private int[][] Map1;
    private int[][] Map2;
    private List<Shoot> player1ShootList;
    private List<Shoot> player2ShootList;

    public Game() {
        turn = 1;
        Score1 = 0;
        Score2 = 0;
        ready = 0;
        Map1 = new int[10][10];
        Map2 = new int[10][10];
        remainTime = 25;
        player1ShootList = new LinkedList<>();
        player2ShootList = new LinkedList<>();
        player2ShipList=new LinkedList<>();
        player1ShipList=new LinkedList<>();
    }

    public Account getPlayed1() {
        return played1;
    }

    public Account getPlayed2() {
        return played2;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn() {
        turn += 1;
        remainTime = 25;
    }

    public Integer getScore1() {
        return Score1;
    }

    public Integer getScore2() {
        return Score2;
    }

    public void setPlayed1(Account played1) {
        this.played1 = played1;
    }

    public void setPlayed2(Account played2) {
        this.played2 = played2;
    }

    public Boolean getReady() {
        if (ready == 2) {
            return true;
        } else {
            return false;
        }
    }

    public void setReady() {
        ready += 1;
    }

    public List<Ship> getPlayer1ShipList() {
        return player1ShipList;
    }

    public void setPlayer1ShipList(List<Ship> player1ShipList) {
        for (Ship ship : this.player1ShipList) {
            for (int x = 0; x < ship.getX(); x++) {
                for (int y = 0; y < ship.getY(); y++) {
                    Map1[ship.getLocationX() + x][ship.getLocationY() + y] = 0;
                }
            }
        }
        this.player1ShipList = player1ShipList;
        for (Ship ship : player1ShipList) {
            for (int x = 0; x < ship.getX(); x++) {
                for (int y = 0; y < ship.getY(); y++) {
                    Map1[ship.getLocationX() + x][ship.getLocationY() + y] = 2;
                }
            }
        }
    }

    public List<Ship> getPlayer2ShipList() {
        return player2ShipList;
    }

    public void setPlayer2ShipList(List<Ship> player2ShipList) {
        for (Ship ship : this.player2ShipList) {
            for (int x = 0; x < ship.getX(); x++) {
                for (int y = 0; y < ship.getY(); y++) {
                    Map2[ship.getLocationX() + x][ship.getLocationY() + y] = 0;
                }
            }
        }
        this.player2ShipList = player2ShipList;
        for (Ship ship : player2ShipList) {
            for (int x = 0; x < ship.getX(); x++) {
                for (int y = 0; y < ship.getY(); y++) {
                    Map2[ship.getLocationX() + x][ship.getLocationY() + y] = 2;
                }
            }
        }
    }

    public void addPlayer1Shoot(Shoot shoot) {
        boolean allow = true;
        for (Shoot shoot1 : player1ShootList) {
            if (shoot1.getX() == shoot.getX()) {
                if (shoot1.getY() == shoot.getY()) {
                    allow = false;
                }
            }
        }
        if (allow) {
            Map2[shoot.getX()][shoot.getY()] += 1;
            if (Map2[shoot.getX()][shoot.getY()] == 3) {
                remainTime = 25;
                shoot.setResult(true);
                player1ShootList.add(shoot);
                Score1 += 1;
                List<Shoot> shootList = Rain(player2ShipList, shoot,Map2);
                for(Shoot shoot1 :shootList){
                    boolean append=true;
                    for(Shoot shoot2 :player1ShootList){
                        if(shoot1.getX()==shoot2.getX()){
                            if(shoot1.getY()==shoot2.getY()){
                                append=false;
                            }
                        }
                    }
                    if(append){
                        player1ShootList.add(shoot1);
                        Map2[shoot1.getX()][shoot1.getY()] = 1;
                    }
                }
            }else{
                setTurn();
            }
        }
    }

    public void addPlayer2Shoot(Shoot shoot) {
        boolean allow = true;
        for (Shoot shoot1 : player2ShootList) {
            if (shoot1.getX() == shoot.getX()) {
                if (shoot1.getY() == shoot.getY()) {
                    allow = false;
                }
            }
        }
        if (allow) {
            Map1[shoot.getX()][shoot.getY()] += 1;
            if (Map1[shoot.getX()][shoot.getY()] == 3) {
                remainTime = 25;
                shoot.setResult(true);
                player2ShootList.add(shoot);
                Score2 += 1;
                List<Shoot> shootList = Rain(player1ShipList, shoot,Map1);
                for(Shoot shoot1 :shootList){
                    boolean append=true;
                    for(Shoot shoot2 :player2ShootList){
                        if(shoot1.getX()==shoot2.getX()){
                            if(shoot1.getY()==shoot2.getY()){
                                append=false;
                            }
                        }
                    }
                    if(append){
                        player2ShootList.add(shoot1);
                        Map1[shoot1.getX()][shoot1.getY()] = 1;
                    }
                }
            }else{
                setTurn();
            }
        }
    }

    public List<Shoot> Rain(List<Ship> shipList, Shoot shoot,int[][] map) {
        boolean rain = false;
        Ship finalShip = null;
        for (Ship ship : shipList) {
            boolean contain = false;
            boolean drown = true;
            if (ship.getX() == 1) {
                for (int y = 0; y < ship.getY(); y++) {
                    if (map[ship.getLocationX()][ship.getLocationY() + y] == 2) {
                        drown = false;
                        break;
                    }
                    if (ship.getLocationX() == shoot.getX() && ship.getLocationY() + y == shoot.getY()) {
                        contain = true;
                    }
                }
            } else {
                for (int x = 0; x < ship.getX(); x++) {
                    if (map[ship.getLocationX() + x][ship.getLocationY()] == 2) {
                        drown = false;
                        break;
                    }
                    if (ship.getLocationX() + x == shoot.getX() && ship.getLocationY() == shoot.getY()) {
                        contain = true;
                    }
                }
            }
            if (drown && contain) {
                rain = true;
                finalShip = ship;
                break;
            }
        }
        List<Shoot> newShootList = new LinkedList<>();
        if (rain) {
            for (int i = Math.max(finalShip.getLocationX() - 1, 0); i <= Math.min(finalShip.getX() + finalShip.getLocationX(), 9); i++) {
                for (int j = Math.max(finalShip.getLocationY() - 1, 0); j <= Math.min(finalShip.getY() + finalShip.getLocationY() , 9); j++) {
                    newShootList.add(new Shoot(i, j));
                }
            }
        }
        return newShootList;
    }

    public Boolean isFinished() {
        if (Score1 < 20 && Score2 < 20) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "played1=" + played1 +
                ", played2=" + played2 +
                ", turn=" + turn +
                ", Score1=" + Score1 +
                ", Score2=" + Score2 +
                ", ready=" + ready +
                ", player1ShipList=" + player1ShipList +
                ", player2ShipList=" + player2ShipList +
                '}';
    }

    public String inf1() {
        String s = played1.getName() + "[";
        if (getTurn() % 2 == 1) {
            s += "true" + "[" + remainTime + "[";
        } else {
            s += "false[25[";
        }
        s += getScore1() + "]";
        return s;
    }

    public String inf2() {
        String s = played2.getName() + "[";
        if (getTurn() % 2 == 0) {
            s += "true" + "[" + remainTime + "[";
        } else {
            s += "false[25[";
        }
        s += getScore2() + "]";
        return s;
    }


    public String FirsBoard() {
        String s = inf1();
        s += inf2();
        s += FirstBoardBool(true);
        s += SecondBoardBool(false);
        return s;
    }

    public String SecondBoard() {
        String s = inf2();
        s += inf1();
        s += SecondBoardBool(true);
        s += FirstBoardBool(false);
        return s;
    }

    public void setRemainTime() {
        remainTime -= 1;
        if (remainTime == 0) {
            setTurn();
        }
    }

    public String FirstBoardBool(Boolean b) {
        String s = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (Map1[i][j] != 0) {
                    if (Map1[i][j] == 2) {
                        if (b) {
                            s += 2 + "" + i + "" + j + "[";
                        }
                    } else {
                        s += Map1[i][j] + "" + i + "" + j + "[";
                    }
                }
            }
        }
        if (s.length() == 0) {
            s = "000[";
        }
        s += "]";
        return s;
    }

    public String SecondBoardBool(Boolean b) {
        String s = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (Map2[i][j] != 0) {
                    if (Map2[i][j] == 2) {
                        if (b) {
                            s += 2 + "" + i + "" + j + "[";
                        }
                    } else {
                        s += Map2[i][j] + "" + i + "" + j + "[";
                    }
                }
            }
        }
        if (s.length() == 0) {
            s = "000[";
        }
        s += "]";
        return s;
    }

    public void RemainTimeStart() {
        remainTime = 25;
    }

    public String StreamBoard() {
        String s = inf1();
        s += inf2();
        s += FirstBoardBool(false);
        s += SecondBoardBool(false);
        return s;
    }
}
