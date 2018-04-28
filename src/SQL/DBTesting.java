package SQL;

import Logic.Color;

public class DBTesting {
    public static void main(String[] args) {
        int gameID = ConnectDB.insertGameData("Alex", 10, 12, Color.GREEN, Color.NULL, "Nice/Smart", "NULL", "NULL", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        System.out.println(ConnectDB.updateGameData(gameID, 11, 13, Color.RED, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3));
        System.out.println(gameID);
        System.out.println(ConnectDB.saveGameData(1, "zyxwvut"));
        System.out.println(ConnectDB.loadGameData(1));
        String stats[][] = ConnectDB.getPlayerStats("Alex");
        System.out.println("Stats: ");
        for (int i = 0; i < stats.length; i++) {
            System.out.println();
            for (int j = 0; j < stats[i].length; j++) {
                System.out.print(stats[i][j]);
                System.out.print("  ");
            }
        }
        String data[][] = ConnectDB.getGameInfo(28);
        System.out.println("Data: ");
        for (int i = 0; i < data.length; i++) {
            System.out.println();
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j]);
                System.out.print("  ");
            }
        }
        String info[][] = ConnectDB.getAllGameInfo();
        System.out.println("Info: ");
        for (int i = 0; i < info.length; i++) {
            System.out.println();
            for (int j = 0; j < info[i].length; j++) {
                System.out.print(info[i][j]);
                System.out.print("  ");
            }
        }
    }
}