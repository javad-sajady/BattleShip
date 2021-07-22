package server;

import server.logic.Game;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private ServerSocket ss;
    private List<ServerSideConnection> players;
    private List<Game> game;

    public Server() {
        players = new LinkedList<>();
        game = new LinkedList<>();
        try {
            ss = new ServerSocket(8000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptConnection() {
        System.out.println("try to accept sockets");
//timer
        Thread timer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (game.size() > 0) {
                        for (Game game1 : game) {
                            game1.setRemainTime();
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }

                }
            }
        });
        timer.start();

        try {
            while (players.size() < 10) {
                Socket s = ss.accept();
                int a = (int) (System.currentTimeMillis() % 1000);
                ServerSideConnection ssc = new ServerSideConnection(s, (int) (a * 10 + players.size()), this);
                players.add(ssc);
                System.out.println("player id :" + ssc.getPlayedId());
                Thread t = new Thread(ssc);
                t.start();
                System.out.println("number of players contain :" + players.size());
            }
            System.out.println("connection occurred");
        } catch (IOException e) {
            System.out.println("IOE error accept connection " + e);
        }
    }

    public List<ServerSideConnection> getPlayers() {
        return players;
    }

    public void setPlayers(List<ServerSideConnection> players) {
        this.players = players;
    }


    public List<Game> getGame() {
        return game;
    }

    public void setGame(List<Game> game) {
        this.game = game;
    }


    public static void main(String[] args) {
        Server gs = new Server();
        gs.acceptConnection();
    }

}
