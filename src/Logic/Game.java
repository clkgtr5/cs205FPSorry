package Logic;

import SQL.*;
import javafx.util.Pair;
import org.omg.PortableInterceptor.INACTIVE;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private int gameID = -1;
    private long startTimeStamp = System.currentTimeMillis();

    public Player currentPlayer;
    public Player winner;
    public Player human;

    public boolean whilePlaying;
    public int currentMove = 0;

    public Board gameBoard;
    public ArrayList<Pawn> everyPawn;
    public ArrayList<Player> allPlayers;
    public HumanPlayer you;

    /**
     * TODO
     *
     * @param playerColor
     * @param aiDifficulties
     */
    public Game(Color playerColor, ArrayList<Integer> aiDifficulties) {
        this.gameBoard = new Board();

        initGame(aiDifficulties, playerColor);
    }

    //example: 5;BLUE;-1:4:1:59,40,3;2:5:2:4,6; current turn; player color; difficulty : bounces : startList size : loc1, loc2, loc3

    /**
     * TODO
     *
     * @param playerColors
     * @param aiDifficulties
     * @param startLists
     * @param locations
     * @param bounces
     * @param currentMove
     */
    public Game(ArrayList<Color> playerColors, ArrayList<Integer> aiDifficulties, ArrayList<Integer> startLists, ArrayList<ArrayList<Integer>> locations, ArrayList<Integer> bounces, int currentMove, int gameID) {

        this.gameID = gameID;
        //This block creates all the players
        gameBoard = new Board();

        allPlayers = new ArrayList<>();
        everyPawn = new ArrayList<>();
        allPlayers.add(new HumanPlayer(generateName(), playerColors.get(0), gameBoard, this));
        human = allPlayers.get(0);

        ArrayList<Pair<Boolean, Boolean>> aiPairs = new ArrayList<>();
        for (Integer tmp : aiDifficulties) {
            if (tmp != -1) {
                aiPairs.add(setOptionsFromInt(tmp));
            }
        }

        for (int ii = 0; ii < aiPairs.size(); ii++) {
            allPlayers.add(new AI(playerColors.get(ii+1), gameBoard, aiPairs.get(ii).getKey(), aiPairs.get(ii).getValue(), generateName()));
        }
        this.currentMove = currentMove;


        //for each player
        for (int i = 0; i < startLists.size(); i++) {
            //remove already created pawns
            allPlayers.get(i).startPawnList.clear();
            //set each player's bounces
            allPlayers.get(i).bounces = bounces.get(i);
            //add that many new pawns for each player's # of pawns in start
            for (int j = 0; j < startLists.get(i); j++) {
                Pawn newPawn = new Pawn(gameBoard, allPlayers.get(i));
                allPlayers.get(i).startPawnList.add(newPawn);
                everyPawn.add(newPawn);
            }

            //iterate through the given location list for each player and make that many pawns in those locations and add them to the movable list
            for (int k = 0; k < locations.get(i).size(); k++) {
                if (allPlayers.get(i).color == Color.BLUE && locations.get(i).get(k) < 0) {
                    Pawn newPawn = new Pawn(gameBoard, allPlayers.get(i), gameBoard.blueSafeZone[-locations.get(i).get(k)]);
                    newPawn.setHasReachedSafeZone(true);
                    allPlayers.get(i).movablePawnList.add(newPawn);
                    everyPawn.add(newPawn);
                    continue;
                }
                if (allPlayers.get(i).color == Color.RED && locations.get(i).get(k) < 0) {
                    Pawn newPawn = new Pawn(gameBoard, allPlayers.get(i), gameBoard.redSafeZone[-locations.get(i).get(k)]);
                    newPawn.setHasReachedSafeZone(true);
                    allPlayers.get(i).movablePawnList.add(newPawn);
                    everyPawn.add(newPawn);
                    continue;
                }
                if (allPlayers.get(i).color == Color.GREEN && locations.get(i).get(k) < 0) {
                    Pawn newPawn = new Pawn(gameBoard, allPlayers.get(i), gameBoard.greenSafeZone[-locations.get(i).get(k)]);
                    newPawn.setHasReachedSafeZone(true);
                    allPlayers.get(i).movablePawnList.add(newPawn);
                    everyPawn.add(newPawn);
                    continue;
                }
                if (allPlayers.get(i).color == Color.YELLOW && locations.get(i).get(k) < 0) {
                    Pawn newPawn = new Pawn(gameBoard, allPlayers.get(i), gameBoard.yellowSafeZone[-locations.get(i).get(k)]);
                    newPawn.setHasReachedSafeZone(true);
                    allPlayers.get(i).movablePawnList.add(newPawn);
                    everyPawn.add(newPawn);
                    continue;
                }
                Pawn newPawn = new Pawn(gameBoard, allPlayers.get(i), gameBoard.everyBlock.get(locations.get(i).get(k)));
                allPlayers.get(i).movablePawnList.add(newPawn);
                everyPawn.add(newPawn);
            }
            //for the remaining pawns, add them to the finished list
            for (int l = 0; l < (4 - (allPlayers.get(i).startPawnList.size() + (allPlayers.get(i).movablePawnList.size()))); l++) {
                allPlayers.get(i).finishedPawnList.add(new Pawn(gameBoard, allPlayers.get(i), gameBoard.getGoalLocation(allPlayers.get(i).color)));
            }
        }

        System.out.print("difficulties size : " + aiDifficulties.size());

    }

    public void initGame(ArrayList<Integer> aiDifficulties, Color playerColor) {

        //get # of AI players and stats

        int AI_PLAYERS = aiDifficulties.size(); //change this once you meet with junxiao TESTING ONLY TESTING ONLY

        //USE THIS FOR TESTING ONLY


        human = new HumanPlayer(generateName(), playerColor, gameBoard, this);


        String name1 = generateName();
        String name2 = generateName();
        String name3 = generateName();

        while (name2 == name1) {
            name2 = generateName();
        }
        while (name3 == name2 || name3 == name1) {
            name3 = generateName();
        }

        Color yourColor = playerColor;
        Color firstColor = generateColor();
        Color secondColor = generateColor();
        Color thirdColor = generateColor();

        while (firstColor == playerColor) {
            firstColor = generateColor();
        }
        while (secondColor == firstColor || secondColor == playerColor) {
            secondColor = generateColor();
        }
        while (thirdColor == firstColor || thirdColor == secondColor || thirdColor == playerColor) {
            thirdColor = generateColor();
        }

        allPlayers = new ArrayList<>();
        allPlayers.add(human);
        ArrayList<Pair<Boolean, Boolean>> aiPairs = new ArrayList<>();
        for (Integer tmp : aiDifficulties) {
            aiPairs.add(setOptionsFromInt(tmp));
        }

        switch (AI_PLAYERS) {
            case 3:
                allPlayers.add(new AI(firstColor, gameBoard, aiPairs.get(2).getKey(), aiPairs.get(2).getValue(), name1));
            case 2:
                boolean secondSmart = true, secondCruel = false;
                allPlayers.add(new AI(secondColor, gameBoard, aiPairs.get(1).getKey(), aiPairs.get(1).getValue(), name2));
            case 1:
                boolean thirdSmart = true, thirdCruel = false;
                allPlayers.add(new AI(thirdColor, gameBoard, aiPairs.get(0).getKey(), aiPairs.get(0).getValue(), name3));
        }

        everyPawn = new ArrayList<>();

        for (Player p : allPlayers) {
            everyPawn.addAll(p.startPawnList);
            everyPawn.addAll(p.movablePawnList);
            everyPawn.addAll(p.finishedPawnList);
        }

        currentMove = 0;

        System.out.println("*************************************************");
        System.out.println("*****************GAME BEGIN**********************");
        System.out.println("*************************************************");

    }


    public void saveGame() {
        insertGameStats();
        String saveState = "";
        saveState += Integer.toString(currentMove) + ";";
        for (Player p : allPlayers) {
            saveState += p.getColor().toString() + ":";
            saveState += Integer.toString(p.difficulty) + ":";
            saveState += Integer.toString(p.bounces) + ":";
            saveState += Integer.toString(p.startPawnList.size()) + ":";
            if (p.movablePawnList.size() == 0) {
                saveState = saveState.substring(0,saveState.length()-1);
            }
            for (int i = 0; i < p.movablePawnList.size(); i++) {
                boolean repeat = false;
                for (int j = 0; j < i; j++) {
                    if (p.movablePawnList.get(i) == p.movablePawnList.get(j)) {
                        repeat = true;
                    }
                }
                if (!repeat) {
                    saveState += Integer.toString(p.movablePawnList.get(i).getCurrentBlock().id) + ",";
                }
            }
            if (p.movablePawnList.size() != 0) {
                saveState = saveState.substring(0, saveState.length() - 1);
            }
            saveState += ";";
        }
        saveState = saveState.substring(0,saveState.length()-1);
        System.out.print("Save state string: ");
        System.out.println(saveState);
        ConnectDB.saveGameData(gameID, saveState);
    }
    //example: 5;BLUE:-1:4:1:59,40,3;YELLOW:2:5:2:4,6;
    //example explained: currentTurn; playerColor; then, for each player, difficulty: bounces: # pawns in start: pawn1Location, pawn2location, pawn3location;

    public static Game loadFromState() {
        String gameData[] = ConnectDB.loadGameData();
        return loadFromState(Integer.parseInt(gameData[0]), gameData[1]);
    }

    /**
     * unpacks a save state string then creates a game object from said string
     *
     * @param inState save state string
     * @return a new game
     */
    private static Game loadFromState(int gameID, String inState) {
        String[] gameParams = inState.split(";");
        for (String param : gameParams) {
            System.out.println(param);
        }
        int currentMove = Integer.parseInt(gameParams[0]);
        int numPlayers = gameParams.length - 1;
        ArrayList<Color> colors = new ArrayList<>(numPlayers);
        ArrayList<Integer> difficulties = new ArrayList<>(numPlayers - 1);
        ArrayList<Integer> bounces = new ArrayList<>(numPlayers);
        ArrayList<Integer> numPawnsStart = new ArrayList<>(numPlayers);
        ArrayList<ArrayList<Integer>> allPawnLocations = new ArrayList<>(numPlayers);
        for (int i = 1; i < gameParams.length; i++) {
            String playerParams[] = gameParams[i].split(":");
            Color playerColor;
            switch (playerParams[0]) {
                case "RED":
                    playerColor = Color.RED;
                    break;
                case "GREEN":
                    playerColor = Color.GREEN;
                    break;
                case "BLUE":
                    playerColor = Color.BLUE;
                    break;
                case "YELLOW":
                    playerColor = Color.YELLOW;
                    break;
                case "NULL":
                default:
                    playerColor = Color.NULL;
                    break;
            }
            colors.add(playerColor);
            difficulties.add(Integer.parseInt(playerParams[1]));
            bounces.add(Integer.parseInt(playerParams[2]));
            numPawnsStart.add(Integer.parseInt(playerParams[3]));
            if (Integer.parseInt(playerParams[3]) == 4) {
                allPawnLocations.add(new ArrayList<>(0));
            } else {
                String pawnIndexes[] = playerParams[4].split(",");
                ArrayList<Integer> pawnLocations = new ArrayList<>(pawnIndexes.length);
                for (String index : pawnIndexes) {
                    pawnLocations.add(Integer.parseInt(index));
                }
                allPawnLocations.add(pawnLocations);
            }
        }
        return new Game(colors, difficulties, numPawnsStart, allPawnLocations, bounces, currentMove, gameID);
    }

    /**
     * generates a random russian name from a list
     *
     * @return a name
     */
    public static String generateName() {
        ArrayList<String> nameList = new ArrayList<>();
        nameList.add("Alexei");
        nameList.add("Artyom");
        nameList.add("Alyosha");
        nameList.add("Anatoly");
        nameList.add("Boris");
        nameList.add("Vasily");
        nameList.add("Vladislav");
        nameList.add("Vitaly");
        nameList.add("Vladimir");
        nameList.add("Dmitry");
        nameList.add("Ivan");
        nameList.add("Leonid");
        nameList.add("Mikhail");
        nameList.add("Georgy");
        nameList.add("Konstantin");
        nameList.add("Nikita");
        nameList.add("Rasputin");
        nameList.add("Pyotr");
        nameList.add("Sergei");
        nameList.add("Stanislav");
        nameList.add("Yuri");
        nameList.add("Vladimir Putin himself");
        nameList.add("Guccifer2.0");
        nameList.add("Fancy Bear");
        nameList.add("Julian Assange");
        nameList.add("Anonymous");

        int nameIndex = (int) (Math.random() * nameList.size());

        return nameList.get(nameIndex);
    }

    /**
     * generates a random color
     *
     * @return a color
     */
    public static Color generateColor() {
        int cIndex = (int) (Math.random() * 4);
        switch (cIndex) {
            case 0:
                return Color.RED;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.YELLOW;
            default:
                return Color.NULL;
        }
    }

    /**
     * quits the game
     */
    public void quitGame() {
        whilePlaying = false;
        this.saveGame();
    }

    /**
     * This method gets all of the stats for the game and inserts them into the database
     */
    public void insertGameStats() {
        Player human = allPlayers.get(0);
        AI AI1 = (AI) allPlayers.get(1);
        AI AI2 = (allPlayers.size() <= 2) ? null : (AI) allPlayers.get(2);
        AI AI3 = (allPlayers.size() <= 3) ? null : (AI) allPlayers.get(3);
        String playerName = human.getName();
        int playtime = (int) (System.currentTimeMillis() - startTimeStamp) / 60000;
        startTimeStamp = System.currentTimeMillis();
        Color playerColor = human.getColor();
        Color winnerColor = (winner == null) ? Color.NULL : winner.color;
        String AI1Diff = AI1.demeanor;
        String AI2Diff = (AI2 == null) ? "NULL" : AI2.demeanor;
        String AI3Diff = (AI3 == null) ? "NULL" : AI3.demeanor;
        int playerBounce = human.bounces;
        int AI1Bounce = AI1.bounces;
        int AI2Bounce = (AI2 == null) ? 0 : AI2.bounces;
        int AI3Bounce = (AI3 == null) ? 0 : AI3.bounces;
        int playerStart = human.getPawnsInStart();
        int AI1Start = AI1.getPawnsInStart();
        int AI2Start = (AI2 == null) ? 0 : AI2.getPawnsInStart();
        int AI3Start = (AI3 == null) ? 0 : AI3.getPawnsInStart();
        int playerHome = human.getPawnsInHome();
        int AI1Home = AI1.getPawnsInHome();
        int AI2Home = (AI2 == null) ? 0 : AI2.getPawnsInHome();
        int AI3Home = (AI3 == null) ? 0 : AI3.getPawnsInHome();
        if (gameID == -1) {
            gameID = ConnectDB.insertGameData(playerName, playtime, currentMove, playerColor, winnerColor,
                    AI1Diff, AI2Diff, AI3Diff, playerBounce, AI1Bounce, AI2Bounce, AI3Bounce,
                    playerStart, AI1Start, AI2Start, AI3Start, playerHome, AI1Home, AI2Home, AI3Home);
        } else {
            System.out.println("gameID: " + gameID);
            ConnectDB.updateGameData(gameID, playtime, currentMove, winnerColor, playerBounce,
                    AI1Bounce, AI2Bounce, AI3Bounce, playerStart, AI1Start, AI2Start, AI3Start,
                    playerHome, AI1Home, AI2Home, AI3Home);
        }
    }

    /**
     * the new play() function. Called on click of the next turn button in the gamewindow
     */
    public void nextTurn() {
        clearHighlightAndSelect();
        currentMove++;
        currentPlayer = allPlayers.get(currentMove % allPlayers.size());

        //if current player is an AI player, do all the stuff automatically
        if (currentPlayer.getClass() == (new AI()).getClass()) {
            currentPlayer.drawStep();

            if (currentPlayer.getPawnsInHome() == 4) {
                winner = currentPlayer;
            }

            this.nextTurn();
        }


    }

    /**
     * prints out game information
     */
    public void printData() {
        System.out.print("Turn " + currentMove + "\n");
        System.out.print("All pawns: ");

        String safe = " ";
        for (Pawn p : everyPawn) {

            System.out.print(p.getCurrentBlock().id + safe);
        }
        System.out.print("\nOuterRing: ");
        //to show where all the pawns are of course
        for (int i = 0; i < 60; i++) {
            if (gameBoard.outerRing[i].getPawn() != null) {
                System.out.print(i + ": " + gameBoard.outerRing[i].getPawn().getColor().toString().toLowerCase() + "; ");
            }
        }
        System.out.println("\n*****************");
    }

    /**
     * clears all highlights and selects
     */
    public void clearHighlightAndSelect() {
        for (Block b : gameBoard.everyBlock) {
            b.highlighted = false;
            b.selected = false;
        }
        for (Pawn p : everyPawn) {
            p.highlighted = false;
            p.selected = false;
        }
    }

    /**
     * returns a HashMap<boolean smart, boolean cruel> from the given difficulty int
     *
     * @param optInt - int given as difficulty representation
     * @return that hashmap
     */
    public Pair<Boolean, Boolean> setOptionsFromInt(int optInt) {
        Pair<Boolean, Boolean> optionsPair = new Pair<>(false, false);
        switch (optInt) {
            case 0:
                optionsPair = new Pair<>(false, false);
                break;
            case 1:
                optionsPair = new Pair<>(false, true);
                break;
            case 2:
                optionsPair = new Pair<>(true, false);
                break;
            case 3:
                optionsPair = new Pair<>(true, true);
                break;
        }
        return optionsPair;
    }
}


