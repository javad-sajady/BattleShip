package server;

import server.logic.Account;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FileManager {

    public FileManager() throws IOException {
        new BufferedWriter(new FileWriter(Constant.getFileAddress() + "Inf.text", true)).close();
    }

    public static Account getName(String name) throws IOException {
        Account account = new Account(null, null);
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(Constant.getFileAddress() + "Inf.text"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            ArrayList<String> A = new ArrayList<String>(Arrays.asList(line.split("]]//]]")));
            if (name.equals(A.get(0))) {
                account.SetInf(A.get(0), A.get(1), Integer.valueOf(A.get(2)), Integer.valueOf(A.get(3)));
            }
        }
        reader.close();
        return account;
    }

    public void SaveAccount(Account account) throws IOException {
        String s = account.getName() + "]]//]]" +
                account.getPass() + "]]//]]" +
                account.getLost() + "]]//]]" +
                account.getWin();
        String fullText = "";
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(Constant.getFileAddress() + "Inf.text"));
        boolean b = false;
        String line = null;
        while ((line = reader.readLine()) != null) {
            ArrayList<String> A = new ArrayList<String>(Arrays.asList(line.split("]]//]]")));
            if (account.getName().equals(A.get(0))) {
                fullText += s + "\n";
                b = true;
            } else {
                fullText += line + "\n";
            }
        }
        if (!b) {
            fullText += s + "\n";
        }

        reader.close();
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(Constant.getFileAddress() + "Inf.text", false));
        writer.write(fullText);
        writer.close();
    }

    public List<Account> getAccount() throws IOException {
        List<Account> accountList = new LinkedList<>();
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(Constant.getFileAddress() + "Inf.text"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            ArrayList<String> A = new ArrayList<String>(Arrays.asList(line.split("]]//]]")));
            Account account = new Account(null, null);
            account.SetInf(A.get(0), A.get(1), Integer.valueOf(A.get(2)), Integer.valueOf(A.get(3)));
            accountList.add(account);
        }
        reader.close();
        return accountList;
    }

    public void saveGame(String name,boolean result) throws IOException {
        BufferedReader reader = null;
        String fullText="";
        reader = new BufferedReader(new FileReader(Constant.getFileAddress() + "Inf.text"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            ArrayList<String> A = new ArrayList<String>(Arrays.asList(line.split("]]//]]")));
            if(A.get(0).equals(name)){
                fullText+=name+"]]//]]"+A.get(1)+"]]//]]";
                int a=0;
                if(result){
                    a=Integer.valueOf(A.get(2))+1;
                    fullText+=a+"]]//]]"+A.get(3);
                }else{
                    a=Integer.valueOf(A.get(3))+1;
                    fullText+=A.get(2)+"]]//]]"+a;
                }
                fullText+="\n";
            }else{
                fullText+=line+"\n";
            }
        }
        reader.close();
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(Constant.getFileAddress() + "Inf.text", false));
        writer.write(fullText);
        writer.close();
    }
}