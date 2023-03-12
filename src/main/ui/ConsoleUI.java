package ui;

import model.Playback;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

// A basic stripped DJ application using a console UI
public class ConsoleUI {
    Playback player;
    Scanner scanner;
    JsonWriter jsonWriter;
    JsonReader jsonReader;

    // EFFECTS: constructs a new console UI, initializes the playback/audio handler object and the scanner, runs ui
    ConsoleUI() {
        player = new Playback();
        scanner = new Scanner(System.in);
        jsonReader = new JsonReader("songPool");
        try {
            player.setPoolOfSongs(jsonReader.getSongsFromFile());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File Not Found");
        }
        run();
    }

    // MODIFIES: this
    // EFFECTS: runs the program on a while loop, scans for user input and executes respective actions accordingly.
    //          when the current song playing runs to an end, also handles switching to the next in queue
    private void run() {
        boolean on = true;
        String input = "";

        while (on) {
            showKeybinds();
            input = scanner.nextLine();
            input = input.toLowerCase();

            if (input.equals("q")) {
                on = false;
            } else {
                if (player.isEnd()) {
                    player.handleSongEnd();
                }
                parseControls(input);
            }
        }
        System.exit(0);
    }

    // EFFECTS: prints out the list of keybinds to the user
    private void showKeybinds() {
        System.out.println("  : toggle play/pause");
        System.out.println("a : add new set");
        System.out.println("s : initialize new song");
        System.out.println("d : add song to set");
        System.out.println("f : remove song from set");
        System.out.println("g : delete set");
        System.out.println("h : edit set name");
        System.out.println("j : edit set genre");
        System.out.println("k : return remaining set length in time");
        System.out.println("l : return remaining set length in # of songs");
        System.out.println("z : move song position in set");
        System.out.println("x : get set song list");
        System.out.println("c : retrieve current song data");
        System.out.println("v : retrieve next song data");
        System.out.println("b : set current song volume");
        System.out.println("n : set current song speed");
        System.out.println("m : set next song volume");
        System.out.println(", : set next song speed");
        System.out.println(". : skip to the next song");
        System.out.println("p : select set to play");
        System.out.println("i : save sets to file");
        System.out.println("o : load sets from file");
    }

    // REQUIRES: a keyboard (this is a joke don't deduct points please)
    // EFFECTS: maps the key input to its respective action
    private void parseControls(String input) {
        switch (input) {
            case " ":
                play();
                break;
            case "a":
                addSet();
                break;
            case "s":
                initializeSong();
                break;
            case "d":
                addSongToSet();
                break;
            case "f":
                removeSongFromSet();
                break;
            case "g":
                deleteSet();
                break;
            default:
                parseControls2(input);
        }
    }

    // EFFECTS: part 2 of parseControls, too big of a method; issues with @SuppressWarning
    private void parseControls2(String input) {
        switch (input) {
            case "h":
                editSetName();
                break;
            case "j":
                editSetGenre();
                break;
            case "k":
                getSetLengthTime();
                break;
            case "l":
                getSetLengthSongs();
                break;
            case "z":
                moveSong();
                break;
            case "x":
                getSetSongList();
                break;
            default:
                parseControls3(input);
        }
    }

    // EFFECTS: part 3 of parseControls, too big of a method; issues with @SuppressWarning
    private void parseControls3(String input) {
        switch (input) {
            case "c":
                getCurrentSongData();
                break;
            case "v":
                getNextSongData();
                break;
            case "b":
                setSongVolume();
                break;
            case "n":
                setSongSpeed();
                break;
            case "m":
                setNextSongVolume();
                break;
            case ",":
                setNextSongSpeed();
                break;
            default:
                parseControls4(input);
        }
    }

    // EFFECTS: part 4 of parseControls, too big of a method; issues with @SuppressWarning
    private void parseControls4(String input) {
        switch (input) {
            case ".":
                player.handleSongEnd();
                break;
            case "p":
                chooseSet();
                break;
            case "i":
                saveSets();
                break;
            case "o":
                loadSets();
                break;
            default:
                System.out.println("ENTER AN ACTUAL THING DUMMY");
        }
    }

    // REQUIRES: at least one set to already exist
    // MODIFIES: this
    // EFFECTS: selects the current set according to user input
    private void chooseSet() {
        String input = "";
        System.out.println("\nEnter name of chosen set:\n");
        input = scanner.nextLine();
        player.selectSet(input);
    }

    // MODIFIES: this
    // EFFECTS: sets the next playback speed according to user specification
    private void setNextSongSpeed() {
        double input = 0;
        System.out.println("\nEnter next speed:\n");
        input = scanner.nextDouble();
        player.setNextSpeed(input);
    }

    // MODIFIES: this
    // EFFECTS: sets the next playback volume according to user input
    private void setNextSongVolume() {
        double input = 0;
        System.out.println("\nEnter next volume:\n");
        input = scanner.nextDouble();
        player.setNextVolume(input);
    }

    // MODIFIES: this
    // EFFECTS: toggles playback (play -> pause, pause -> play) of audio
    private void play() {
        try {
            player.togglePlay();
        } catch (NullPointerException e) {
            System.out.println("Nothing selected");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets current playback speed according to user input
    private void setSongSpeed() {
        double input = 0;
        System.out.println("\nEnter new speed:\n");
        input = scanner.nextDouble();
        player.setSpeed(input);
    }

    // MODIFIES: this
    // EFFECTS: sets current playback volume according to user input
    private void setSongVolume() {
        double input = 0;
        System.out.println("\nEnter the new volume:\n");
        input = scanner.nextDouble();
        player.setVolume(input);
    }

    // EFFECTS: prints out the next song in the queue's associated information
    private void getNextSongData() {
        System.out.println(player.getNextSongName() + " by " + Arrays.toString(player.getNextSongArtists()));
        System.out.println("BPM: " + player.getNextSongBPM());
        System.out.println("Key: " + player.getNextSongKey());
        System.out.println("Genre: " + player.getNextSongGenre());
        System.out.println("Duration: " + player.getNextSongLength());
        System.out.println("\nPlayback speed: " + player.getNextSpeed());
        System.out.println("Playback volume: " + player.getNextVolume());
    }

    // EFFECTS: prints out the current song's associated information
    private void getCurrentSongData() {
        System.out.println(player.getCurrentSongName() + " by " + Arrays.toString(player.getCurrentSongArtists()));
        System.out.println("BPM: " + player.getCurrentSongBPM());
        System.out.println("Key: " + player.getCurrentSongKey());
        System.out.println("Genre: " + player.getCurrentSongGenre());
        System.out.println("Duration: " + player.getCurrentSongLength());
        System.out.println("\nPlayback speed: " + player.getCurrentSpeed());
        System.out.println("Playback volume: " + player.getCurrentVolume());
    }

    // REQUIRES: at least one set to already exist
    // EFFECTS: prints out the list of songs in the current set
    private void getSetSongList() {
        System.out.println("\nSongs in \"" + player.getCurrentSetTitle()
                + "\" (" + player.getCurrentSetGenre() + "):\n");
        for (int i = 0; i < player.getSongs().size(); i++) {
            System.out.println(player.getSongs().get(i).getName() + "\n");
        }
    }

    // REQUIRES: the specified song to already be in the specified set, no duplicate songs or sets
    // MODIFIES: this
    // EFFECTS: removes specified song in specified set and adds it to specified position
    private void moveSong() {
        String[] input = new String[3];
        System.out.println("\nEnter the name of the set you want to edit:\n");
        input[0] = scanner.nextLine();
        System.out.println("\nEnter the name of the song you want to move:\n");
        input[1] = scanner.nextLine();
        System.out.println("\nEnter the new position of the song:\n");
        input[2] = scanner.nextLine();

        player.positionSong(input[1], input[0], Integer.parseInt(input[2]));
    }

    // REQUIRES: a set to have already been initialized and selected
    // EFFECTS: prints out the # of songs in the selected set
    private void getSetLengthSongs() {
        System.out.println(player.getRemainingSetSongs());
    }

    // REQUIRES: a set to have already been initialized and selected
    // EFFECTS: returns selected set length left in MM:SS
    private void getSetLengthTime() {
        System.out.println((int) Math.floor(player.getRemainingSetTime() / 60)
                + ":" + Math.round(player.getRemainingSetTime() % 60));
    }

    // REQUIRES: the specified set to already exist, no duplicate sets
    // MODIFIES: this
    // EFFECTS: modifies genre to specified user input
    private void editSetGenre() {
        String[] input = new String[2];
        System.out.println("\nEnter the name of the set you want to edit:\n");
        input[0] = scanner.nextLine();
        System.out.println("\nEnter the new set genre:\n");
        input[1] = scanner.nextLine();

        player.setGenreName(input[0], input[1]);
    }

    // REQUIRES: the specified set to already exist, no duplicate sets
    // MODIFIES: this
    // EFFECTS: renames the specified set to user input
    private void editSetName() {
        String[] input = new String[2];
        System.out.println("\nEnter the name of the set you want to edit:\n");
        input[0] = scanner.nextLine();
        System.out.println("\nEnter the new set name:\n");
        input[1] = scanner.nextLine();

        player.setSetName(input[0], input[1]);
    }

    // REQUIRES: the specified set to already exist, no duplicate sets
    // MODIFIES: this
    // EFFECTS: removes the specified set from the list of sets
    private void deleteSet() {
        String input = "";
        System.out.println("\nEnter the name of the set you want to delete:\n");
        input = scanner.nextLine();

        player.removeSet(input);
    }

    // REQUIRES: the specified song to be in the specified set, no duplicate songs or sets
    // MODIFIES: this
    // EFFECTS: removes specified song from specified set
    private void removeSongFromSet() {
        String[] input = new String[2];
        System.out.println("\nEnter the name of the set you want to remove from:\n");
        input[0] = scanner.nextLine();
        System.out.println("\nEnter the name of the song:\n");
        input[1] = scanner.nextLine();

        player.removeSongFromSet(input[1], input[0]);
    }

    // REQUIRES: the specified song & set to already have been initialized, no duplicate songs or sets
    // MODIFIES: this
    // EFFECTS: adds the specified song to the set
    private void addSongToSet() {
        String[] input = new String[2];
        System.out.println("\nEnter the name of the set you want to add to:\n");
        input[0] = scanner.nextLine();
        System.out.println("\nEnter the name of the song:\n");
        input[1] = scanner.nextLine();

        player.addSongToSet(input[1], input[0]);
    }

    // MODIFIES: this
    // EFFECTS: creates a new set with specified name and genre, adds to set list
    private void addSet() {
        String[] setInfo = new String[2];
        System.out.println("\nEnter set name:\n");
        setInfo[0] = scanner.nextLine();
        System.out.println("\nEnter set genre:\n");
        setInfo[1] = scanner.nextLine();

        player.makeNewSet(setInfo[0], setInfo[1]);
    }

    // REQUIRES: the respective file to be in the songs folder
    // MODIFIES: this
    // EFFECTS: creates a song object from respective file and user specification and adds it to the song pool
    private void initializeSong() {
        String[] songInfo = new String[3];
        System.out.println("\nEnter file name:\n");
        songInfo[0] = scanner.nextLine();
        System.out.println("\nEnter BPM:\n");
        songInfo[1] = scanner.nextLine();
        System.out.println("\nEnter Key\n");
        songInfo[2] = scanner.nextLine();

        player.addSong(songInfo[0], Integer.parseInt(songInfo[1]), songInfo[2]);
        try {
            jsonWriter = new JsonWriter("songPool.json");
            jsonWriter.writePool(player.getSongs());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File Not Found");
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the current song pool and set list to JSON files
    private void saveSets() {
        String fileName;
        System.out.println("\nEnter file name:\n");
        fileName = scanner.nextLine();

        try {
            jsonWriter = new JsonWriter(fileName);
            jsonWriter.open();
            jsonWriter.write(player.getAllSets());
            jsonWriter.close();
            System.out.println("\nSets saved successfully to " + fileName + "!\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File Not Found");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads set list from JSON file to program
    private void loadSets() {
        String fileName;
        System.out.println("\nEnter the name of the file you want to load from:\n");
        fileName = scanner.nextLine();

        try {
            jsonReader = new JsonReader(fileName);
            player.setListOfSets(jsonReader.getSetsFromFile(player.getSongs()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File Not Found");
        }
    }
}
