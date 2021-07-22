package Client.logic;

import server.logic.Account;
import server.logic.Ship;

import java.util.List;

public class Game {
    private Account played1;
    private Account played2;
    private Integer turn;
    private Integer Score1;
    private Integer Score2;
    private Integer ready;
    private List<server.logic.Ship> player1ShipList;
    private List<server.logic.Ship> player2ShipList;

    public Game() {
        turn=0;
        Score1=0;
        Score2=0;
        ready=0;
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
        if(ready==2){
            return true;
        }
        else {
            return false;
        }
    }

    public void setReady() {
        ready+=1;
    }

    public List<server.logic.Ship> getPlayer1ShipList() {
        return player1ShipList;
    }

    public void setPlayer1ShipList(List<server.logic.Ship> player1ShipList) {
        this.player1ShipList = player1ShipList;
    }

    public List<server.logic.Ship> getPlayer2ShipList() {
        return player2ShipList;
    }

    public void setPlayer2ShipList(List<Ship> player2ShipList) {
        this.player2ShipList = player2ShipList;
    }

    @Override
    public String toString() {
        return "Game{" +
                "played1=" + played1 +
                ", played2=" + played2 +
                ", turn=" + turn +
                ", Score1=" + Score1 +
                ", Score2=" + Score2 +
                '}';
    }
}
