package server;

import server.logic.*;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ServerSideConnection implements Runnable {
    private Socket socket;
    private BufferedReader dataIn;
    private PrintWriter dataOut;
    private int playedId;
    private Server server;
    private Account account;
    private Game game;
    private Object playerList = new Object();
    private Object gameList = new Object();
    private int Life;
    private int gameNum;
    private boolean ready;

    public ServerSideConnection(Socket s, int id, Server server) {
        this.server = server;
        socket = s;
        playedId = id;
        try {
            dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            System.out.println("IOE Server Side Connection error " + e);
        }
    }

    @Override
    public void run() {
//        send id
        sendEvent(String.valueOf(playedId));
//        create or login
        String result = "0000000000000";
        String s = "";
        while (!result.substring(result.length() - 9, result.length()).equals("true[true")) {
            s = receiveEvent();
            result = action(s);
            sendEvent(result);
        }
//      set and send account
        try {
            account = FileManager.getName(s.split("\\[")[2]);
            sendEvent(account.getName() + "[" + account.getPass() + "[" + account.getLost() + "[" + account.getWin());
            System.out.println("we set the value of account for " + playedId);

        } catch (IOException e) {
            System.out.println("IOException in saving account at ssc " + e);
        }
//      other jobs
        while (true) {
            synchronized (s) {
                s = receiveEvent();
                result = action(s);
                sendEvent(result);
            }
        }

    }

    private String action(String s) {
        String[] A = s.split("\\[");
        String t = "";
        if (isUser((String) A[0])) {
            if (A[1].equals("Login")) {
                t = loginAction(A);
            }
            if (A[1].equals("Create")) {
                t = createAction(A);
            }
            if (A[1].equals("HighScore")) {
                t = highScore();
            }
            if (A[1].equals("Exit")) {
                t = exit(A);
            }
            if (A[1].equals("Game")) {
                ready = false;
                Life = 3;
                t = game(A);
            }
            if (A[1].equals("MakeShuffle")) {
                t = Shuffle(A);
            }
            if (A[1].equals("Shuffle")) {
                if (Life > 0) {
                    Life -= 1;
                    t = Shuffle(A);
                }
            }
            if (A[1].equals("Ready")) {
                if (!ready) {
                    t = Ready(A);
                    ready = true;
                }
            }
            if (A[1].equals("ShipList")) {
                t = "";
                if (game.getPlayed1().equals(account)) {
                    for (Ship ship : game.getPlayer1ShipList()) {
                        t += ship.toWrite() + "]";
                    }
                } else {
                    for (Ship ship : game.getPlayer2ShipList()) {
                        t += ship.toWrite() + "]";
                    }
                }
            }
            if (A[1].equals("Begin")) {
                Begin();
                t = "EndGame";
            }
            if (A[1].equals("Stream")) {
                t = Stream();
            }
            if (A[1].equals("StreamBegin")) {
                StreamBegin(Integer.valueOf(A[2]));
                t = "EndGame";
            }
        }
        return t;
    }

    private void StreamBegin(Integer a) {
        Game game;
        synchronized (gameList) {
            game = server.getGame().get(a);
        }
        String s = "";
        String t = "";
        while (!game.isFinished()) {
            s = receiveEvent();
            if (s.length() > 1) {
                String[] A = s.split("\\[");
                if (A[1].equals("Board")) {
                    t = game.StreamBoard();
                    sendEvent(t);
                }
            }
        }
//        just show the last blows ....

        s = receiveEvent();
        if (s.length() > 1) {
            String[] A = s.split("\\[");
            if (A[1].equals("Board")) {
                t = game.StreamBoard();
                sendEvent(t);
            }
        }


    }

    private String Stream() {
        List<Game> game = new LinkedList<>();
        synchronized (gameList) {
            game = server.getGame();
        }
        String s = "";
        int a = 0;
        for (Game game1 : game) {
            if (game1.getPlayed2() != null) {
                s += game1.getPlayed1().getName() + "-" + game1.getPlayed2().getName() + "*" + a + "[";
            }
            a += 1;
        }
        return s;
    }

    private void Begin() {
        boolean me = false;
        if (game.getPlayed1().equals(account)) {
            me = true;
        }
        String s = "";
        String t = "";
        game.RemainTimeStart();
        while (!game.isFinished()) {
            s = receiveEvent();
            if (s.length() > 1) {
                String[] A = s.split("\\[");
                if (A[1].equals("Board")) {
                    if (me) {
                        t = game.FirsBoard();
                    } else {
                        t = game.SecondBoard();
                    }
                    sendEvent(t);
                }
                if (A[1].equals("Click")) {
                    Shoot shoot = new Shoot(Integer.valueOf(A[2]), Integer.valueOf(A[3]));
                    if (game.getTurn() % 2 == 1) {
                        if (me) {
                            game.addPlayer1Shoot(shoot);
                        }

                    } else {
                        if (!me) {
                            game.addPlayer2Shoot(shoot);
                        }
                    }
                }
            }
        }
//        just show the last blows ....

        s = receiveEvent();
        if (s.length() > 1) {
            String[] A = s.split("\\[");
            if (A[1].equals("Board")) {
                if (me) {
                    t = game.FirsBoard();
                } else {
                    t = game.SecondBoard();
                }
                sendEvent(t);
            }
        }

//        save the result;
        boolean b;
        if (me) {
            if (game.getScore1() == 20) {
                b = true;
            } else {
                b = false;
            }
        } else {
            if (game.getScore2() == 20) {
                b = true;
            } else {
                b = false;
            }
        }
        try {
            new FileManager().saveGame(account.getName(), b);
        } catch (IOException e) {
            System.out.println("IOException in game saving " + e);
        }

    }

    private String Ready(String[] A) {
        game.setReady();
        List<ServerSideConnection> players = new LinkedList<>();
        synchronized (playerList) {
            players = server.getPlayers();
        }
        if (game.getReady()) {
            if (game.getPlayed1().equals(account)) {
                for (ServerSideConnection ssc : players) {
                    if (ssc.getAccount() != null) {
                        if (ssc.getAccount().equals(game.getPlayed2())) {
                            ssc.sendEvent("StartGame");
                        }
                    }
                }
            } else {
                for (ServerSideConnection ssc : players) {
                    if (ssc.getAccount() != null) {
                        if (ssc.getAccount().equals(game.getPlayed1())) {
                            ssc.sendEvent("StartGame");
                        }
                    }
                }
            }
            return "StartGame";
        } else {
            return "Wait";
        }
    }

    private String Shuffle(String[] A) {
        List<Ship> shipList = new Shuffle().Make();
        List<ServerSideConnection> players = new LinkedList<>();
        synchronized (playerList) {
            players = server.getPlayers();
        }
        if (game.getPlayed1().equals(account)) {
            game.setPlayer1ShipList(shipList);
            for (ServerSideConnection ssc : players) {
                if (ssc.getAccount() != null) {
                    if (ssc.getAccount().equals(game.getPlayed2())) {
                        ssc.game.setPlayer1ShipList(shipList);
                    }
                }
            }
        } else {
            game.setPlayer2ShipList(shipList);
            for (ServerSideConnection ssc : players) {
                if (ssc.getAccount() != null) {
                    if (ssc.getAccount().equals(game.getPlayed1())) {
                        ssc.game.setPlayer2ShipList(shipList);
                    }
                }
            }
        }

        String t = "";
        for (Ship ship : shipList) {
            t += ship.toWrite() + "]";
        }
        return t;
    }

    private String game(String[] A) {
        List<ServerSideConnection> players = new LinkedList<>();
        synchronized (playerList) {
            players = server.getPlayers();
        }
        synchronized (gameList) {
            List<Game> game = server.getGame();
            String t = "";
            if (game.size() != 0) {
                if (game.get(game.size() - 1).getPlayed2() == null) {
                    game.get(game.size() - 1).setPlayed2(getAccount());
                    for (ServerSideConnection ssc : players) {
                        if (ssc.getAccount() != null) {
                            if (ssc.getAccount().equals(game.get(game.size() - 1).getPlayed1())) {
                                ssc.game = game.get(game.size() - 1);
                                ssc.sendEvent(account.getName());
                                t = game.get(game.size() - 1).getPlayed1().getName();
                            }
                        }
                    }
                    this.game = game.get(game.size() - 1);
                    gameNum = game.size() - 1;
                    return t;
                } else {
                    Game game1 = new Game();
                    game1.setPlayed1(getAccount());
                    game.add(game1);
                    this.game = game.get(game.size() - 1);
                    gameNum = game.size() - 1;
                    return "Wait";
                }
            } else {
                Game game1 = new Game();
                game1.setPlayed1(getAccount());
                game.add(game1);
                this.game = game.get(game.size() - 1);
                gameNum = game.size() - 1;
                return "Wait";
            }
        }
    }

    private String exit(String[] A) {
        synchronized (playerList) {
            List<ServerSideConnection> players = server.getPlayers();
            for (ServerSideConnection ssc : players) {
                if (A[0].equals(String.valueOf(ssc.getPlayedId()))) {
                    players.remove(ssc);
                }
            }
            server.setPlayers(players);
        }
        return null;
    }

    private String highScore() {
        List<Account> accountList = new LinkedList<>();
        try {
            accountList = new FileManager().getAccount();
        } catch (IOException e) {
            System.out.println("IOException in reading accounts ");
        }
        String s = "";
        for (Account account : accountList) {
            s += account.getName() + "[" + account.getLost() + "[" + account.getWin() + "[" + isOnline(account.getName()) + "]";
        }
        return s;
    }

    private String createAction(String[] A) {
        boolean b = false;
        String t = A[0] + "[" + A[1] + "[";
        Account account = new Account("", "");
        try {
            account = FileManager.getName(String.valueOf(A[2]));
        } catch (IOException e) {
            System.out.println("IOE Exception in file manager" + e);
        }
        if (account.getName() == null) {
            t += "true" + "[";
            if (A[4].equals(A[3])) {
                t += "true";
                b = true;
            } else {
                t += "false";
            }
        } else {
            t += "false" + "[" + "true";
        }
        if (b) {
            try {
                new FileManager().SaveAccount(new Account(A[2], A[3]));
            } catch (IOException e) {
                System.out.println("IOException in create action save account " + e);
            }
        }
        return t;
    }

    private String loginAction(String[] A) {
        String t = A[0] + "[" + A[1] + "[";
        Account account = new Account("", "");
        try {
            account = FileManager.getName(String.valueOf(A[2]));
        } catch (IOException e) {
            System.out.println("IOE Exception in file manager" + e);
        }
        if (account.getName() != null) {
            t += "true" + "[";
            if (account.getPass().equals(A[3])) {
                t += "true";
            } else {
                t += "false";
            }
        } else {
            t += "false" + "[" + "true";
        }
        return t;
    }

    public Boolean isUser(String s) {
        boolean is_User = false;
        List<ServerSideConnection> players = new LinkedList<>();
        synchronized (playerList) {
            players = server.getPlayers();
        }
        for (ServerSideConnection ssc : players) {
            if (s.equals(String.valueOf(ssc.getPlayedId()))) {
                is_User = true;
            }
        }
        return is_User;

    }

    public boolean isOnline(String name) {
        boolean online = false;
        List<ServerSideConnection> players = new LinkedList<>();
        synchronized (playerList) {
            players = server.getPlayers();
        }
        for (ServerSideConnection ssc : players) {
            if (ssc.getAccount() != null) {
                if (name.equals(ssc.getAccount().getName())) {
                    online = true;
                }
            }
        }
        return online;
    }


    public void sendEvent(String s) {
        try {
//            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(s);
            oos.flush();
//            os.flush();
        } catch (IOException e) {
            System.out.println("IOE Exception from ssc sendEvent " + e);
        }
        System.out.println("we send " + s);
    }

    public String receiveEvent() {
        ServerWait.waitForClient(socket);
        String s = "";
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            s = (String) ois.readObject();
        } catch (IOException e) {
            System.out.println("IOE Exception from csc receiveEvent " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException from csc receiveEvent " + e);
        }
        System.out.println("we received " + s);
        return s;
    }


    public Socket getSocket() {
        return socket;
    }

    public int getPlayedId() {
        return playedId;
    }

    public Account getAccount() {
        return account;
    }


}
