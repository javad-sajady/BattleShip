package Client.logic;

public class Account {
    public int Lost;
    public int Win;
    private String Pass;
    public String Name;
    public boolean Online;

    public Account(String name, String pass) {
        Name = name;
        Pass = pass;
        Win = 0;
        Lost = 0;
    }

    public int getLost() {
        return Lost;
    }

    public void addLost() {
        Lost += 1;
    }

    public int getWin() {
        return Win;
    }

    public void addWin() {
        Win += 1;
    }

    public String getPass() {
        return Pass;
    }

    public String getName() {
        return Name;
    }

    public void SetInf(String name, String pass, int lost, int win) {
        Lost = lost;
        Win = win;
        Pass = pass;
        Name = name;
    }

    public void SetInf(Account account){
        Name=account.getName();
        Pass=account.Pass;
        Win=account.getWin();
        Lost=account.getLost();
    }

    public Account stringToAccount(String t){
        String [] A=t.split("\\[");
        Account account=new Account("","");
        account.SetInf(A[0],A[1],Integer.valueOf(A[2]),Integer.valueOf(A[3]));
        return account;
    }

    public boolean isOnline() {
        return Online;
    }

    public void setOnline(boolean online) {
        Online = online;
    }

    @Override
    public String toString() {
        return "Name=" + Name+", Win=" + Win + ", Lost=" + Lost +", Score="+(Win-Lost) ;

    }
}
