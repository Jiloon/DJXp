package ui;

import model.Playback;
import model.Set;
import model.Song;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class GraphicalUI implements ActionListener {
    static final int FRAME_WIDTH = 1200;
    static final int FRAME_HEIGHT = 675;
    static final int BAR_WIDTH = FRAME_WIDTH;
    static final int BAR_HEIGHT = FRAME_HEIGHT * 13 / 60;

    private static JFrame frame;
    private JLabel bkg;
    private JPanel topBar;
    private JPanel leftMixer;
    private JPanel rightMixer;
    private JPanel bottomBar;
    private JLabel setName;
    private JButton nextSet;
    private JButton prevSet;
    private JButton save;
    private JButton load;
    private JLabel songsLeft;
    private JButton play;
    private JButton skip;
    private JButton viewSets;
    private JButton addSet;
    private JButton removeSet;
    private JButton viewSongs;
    private JButton addSong;
    private JButton removeSong;
    private JButton initializeSong;
    private JSlider leftPitch;
    private JSlider leftVolume;
    private JSlider leftSpeed;
    private JLabel leftName;
    private JLabel leftArtists;
    private JLabel leftKey;
    private JLabel leftBpm;
    private JLabel leftGenre;
    private JSlider rightPitch;
    private JSlider rightVolume;
    private JSlider rightSpeed;
    private JLabel rightName;
    private JLabel rightArtists;
    private JLabel rightKey;
    private JLabel rightBpm;
    private JLabel rightGenre;
    private Timer timer;

    private Playback player;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: constructs a gui object, initializes all swing components, creates a new player and loads songs from
    //          song pool
    GraphicalUI() {
        setupFrame();
        setupBackground();
        setupTopBar();
        setupBottomBar();
        setupLeftMixer();
        setupRightMixer();
        attachToFrame();

        player = new Playback();
        jsonReader = new JsonReader("songPool");
        try {
            player.setPoolOfSongs(jsonReader.getSongsFromFile());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File Not Found");
        }
    }

    // MODIFIES: this
    // EFFECTS: converts next song modifiable properties into current song and resets next song properties
    private void transitionSliders() {
        leftPitch.setValue(rightPitch.getValue());
        leftVolume.setValue(rightVolume.getValue());
        leftSpeed.setValue(rightSpeed.getValue());
        rightPitch.setValue(100);
        rightVolume.setValue(100);
        rightSpeed.setValue(100);
    }

    // MODIFIES: this
    // EFFECTS: initializes pitch slider on right mixer with position, size, graphics. No current functionality
    private void setupRightPitch() {
        rightPitch = new JSlider(SwingConstants.HORIZONTAL, 0, 200, 100);
        rightPitch.setSize(BAR_HEIGHT * 9 / 10, BAR_HEIGHT / 5);
        rightPitch.setLocation(BAR_WIDTH * 7 / 480, FRAME_HEIGHT * 19 / 80);
        rightPitch.setOpaque(false);
        rightPitch.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int value = rightPitch.getValue();
                        //player.setNextPitch((double) value / 100);
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: initializes volume slider on right mixer with position, size, graphic properties, adjusts volume by val
    private void setupRightVolume() {
        rightVolume = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 100);
        rightVolume.setSize(BAR_HEIGHT * 9 / 10, BAR_HEIGHT / 5);
        rightVolume.setLocation(BAR_WIDTH * 7 / 480, FRAME_HEIGHT * 57 / 160);
        rightVolume.setOpaque(false);
        rightVolume.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int value = rightVolume.getValue();
                        player.setNextVolume((double) value / 100);
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: initializes speed slider on right mixer with position, size, graphic properties, adjusts speed by val
    private void setupRightSpeed() {
        rightSpeed = new JSlider(SwingConstants.HORIZONTAL, 0, 200, 100);
        rightSpeed.setSize(BAR_HEIGHT * 9 / 10, BAR_HEIGHT / 5);
        rightSpeed.setLocation(BAR_WIDTH * 7 / 480, FRAME_HEIGHT * 38 / 80);
        rightSpeed.setOpaque(false);
        rightSpeed.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int value = rightSpeed.getValue();
                        player.setNextSpeed((double) value / 100);
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: initializes song name text box on right mixer with position size and graphic properties
    private void setupRightName() {
        rightName = new JLabel("Name");
        rightName.setForeground(Color.WHITE);
        rightName.setFont(new Font("LEMON MILK Bold", Font.PLAIN, 55));
        rightName.setSize(BAR_HEIGHT * 26 / 10, BAR_HEIGHT * 31 / 90);
        rightName.setLocation(BAR_WIDTH * 7 / 480, FRAME_HEIGHT * 5 / 160);
        rightName.setVerticalAlignment(SwingConstants.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes artists text box on right mixer with position size and graphic properties
    private void setupRightArtists() {
        rightArtists = new JLabel("Artists");
        rightArtists.setForeground(Color.WHITE);
        rightArtists.setFont(new Font("Kiona", Font.PLAIN, 40));
        rightArtists.setSize(BAR_HEIGHT * 26 / 10, BAR_HEIGHT / 3);
        rightArtists.setLocation(BAR_WIDTH * 7 / 480, FRAME_HEIGHT * 8 / 80);
        rightArtists.setVerticalAlignment(SwingConstants.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes song key text box on right mixer with position size and graphic properties
    private void setupRightKey() {
        rightKey = new JLabel("Key");
        rightKey.setForeground(Color.WHITE);
        rightKey.setFont(new Font("Kiona", Font.PLAIN, 40));
        rightKey.setSize(BAR_HEIGHT * 11 / 10, BAR_HEIGHT / 3);
        rightKey.setLocation(BAR_WIDTH * 43 / 120, FRAME_HEIGHT * 7 / 80);
        rightKey.setVerticalAlignment(SwingConstants.CENTER);
        rightKey.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes song bpm text box on right mixer with position size and graphic properties
    private void setupRightBpm() {
        rightBpm = new JLabel("BPM");
        rightBpm.setForeground(Color.WHITE);
        rightBpm.setFont(new Font("Kiona", Font.PLAIN, 40));
        rightBpm.setSize(BAR_HEIGHT * 11 / 10, BAR_HEIGHT / 3);
        rightBpm.setLocation(BAR_WIDTH * 43 / 120, FRAME_HEIGHT * 17 / 80);
        rightBpm.setVerticalAlignment(SwingConstants.CENTER);
        rightBpm.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes song genre text box on right mixer with position size and graphic properties
    private void setupRightGenre() {
        rightGenre = new JLabel("Genre");
        rightGenre.setForeground(Color.WHITE);
        rightGenre.setFont(new Font("Kiona", Font.PLAIN, 40));
        rightGenre.setSize(BAR_HEIGHT * 11 / 10, BAR_HEIGHT * 2 / 3);
        rightGenre.setLocation(BAR_WIDTH * 43 / 120, FRAME_HEIGHT * 26 / 80);
        rightGenre.setVerticalAlignment(SwingConstants.CENTER);
        rightGenre.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: handles the set up for the components of the right mixer
    private void rightMixerComponentHandler() {
        setupRightPitch();
        setupRightVolume();
        setupRightSpeed();
        setupRightName();
        setupRightArtists();
        setupRightKey();
        setupRightBpm();
        setupRightGenre();
    }

    // MODIFIES: this
    // EFFECTS: initializes right mixer panel with position, size, graphic properties & components
    private void setupRightMixer() {
        rightMixerComponentHandler();

        rightMixer = new JPanel();
        rightMixer.setLayout(null);
        rightMixer.setOpaque(false);
        rightMixer.add(rightPitch);
        rightMixer.add(rightVolume);
        rightMixer.add(rightSpeed);
        rightMixer.add(rightName);
        rightMixer.add(rightArtists);
        rightMixer.add(rightKey);
        rightMixer.add(rightBpm);
        rightMixer.add(rightGenre);
        rightMixer.setBounds(BAR_WIDTH / 2, BAR_HEIGHT, BAR_WIDTH / 2, FRAME_HEIGHT - 2 * BAR_HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS: initializes pitch slider on left mixer with position, size, graphic properties. No current functionality
    private void setupLeftPitch() {
        leftPitch = new JSlider(SwingConstants.HORIZONTAL, 0, 200, 100);
        leftPitch.setSize(BAR_HEIGHT * 9 / 10, BAR_HEIGHT / 5);
        leftPitch.setLocation(BAR_WIDTH * 7 / 480, FRAME_HEIGHT * 19 / 80);
        leftPitch.setOpaque(false);
        leftPitch.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int value = leftPitch.getValue();
                        //player.setPitch((double) value / 100);
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: initializes volume slider on left mixer with position, size, graphic properties, adjusts volume by val
    private void setupLeftVolume() {
        leftVolume = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 100);
        leftVolume.setSize(BAR_HEIGHT * 9 / 10, BAR_HEIGHT / 5);
        leftVolume.setLocation(BAR_WIDTH * 7 / 480, FRAME_HEIGHT * 57 / 160);
        leftVolume.setOpaque(false);
        leftVolume.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int value = leftVolume.getValue();
                        player.setVolume((double) value / 100);
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: initializes speed slider on left mixer with position, size, graphic properties, adjusts speed by val
    private void setupLeftSpeed() {
        leftSpeed = new JSlider(SwingConstants.HORIZONTAL, 0, 200, 100);
        leftSpeed.setSize(BAR_HEIGHT * 9 / 10, BAR_HEIGHT / 5);
        leftSpeed.setLocation(BAR_WIDTH * 7 / 480, FRAME_HEIGHT * 38 / 80);
        leftSpeed.setOpaque(false);
        leftSpeed.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int value = leftSpeed.getValue();
                        player.setSpeed((double) value / 100);
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: initializes song name text box on left mixer with position size and graphic properties
    private void setupLeftName() {
        leftName = new JLabel("Name");
        leftName.setForeground(Color.WHITE);
        leftName.setFont(new Font("LEMON MILK Bold", Font.PLAIN, 55));
        leftName.setSize(BAR_HEIGHT * 26 / 10, BAR_HEIGHT * 31 / 90);
        leftName.setLocation(BAR_WIDTH * 7 / 480, FRAME_HEIGHT * 5 / 160);
        leftName.setVerticalAlignment(SwingConstants.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes artists text box on left mixer with position size and graphic properties
    private void setupLeftArtists() {
        leftArtists = new JLabel("Artists");
        leftArtists.setForeground(Color.WHITE);
        leftArtists.setFont(new Font("Kiona", Font.PLAIN, 40));
        leftArtists.setSize(BAR_HEIGHT * 26 / 10, BAR_HEIGHT / 3);
        leftArtists.setLocation(BAR_WIDTH * 7 / 480, FRAME_HEIGHT * 8 / 80);
        leftArtists.setVerticalAlignment(SwingConstants.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes song key text box on left mixer with position size and graphic properties
    private void setupLeftKey() {
        leftKey = new JLabel("Key");
        leftKey.setForeground(Color.WHITE);
        leftKey.setFont(new Font("Kiona", Font.PLAIN, 40));
        leftKey.setSize(BAR_HEIGHT * 11 / 10, BAR_HEIGHT / 3);
        leftKey.setLocation(BAR_WIDTH * 43 / 120, FRAME_HEIGHT * 7 / 80);
        leftKey.setVerticalAlignment(SwingConstants.CENTER);
        leftKey.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes song bpm text box on left mixer with position size and graphic properties
    private void setupLeftBpm() {
        leftBpm = new JLabel("BPM");
        leftBpm.setForeground(Color.WHITE);
        leftBpm.setFont(new Font("Kiona", Font.PLAIN, 40));
        leftBpm.setSize(BAR_HEIGHT * 11 / 10, BAR_HEIGHT / 3);
        leftBpm.setLocation(BAR_WIDTH * 43 / 120, FRAME_HEIGHT * 17 / 80);
        leftBpm.setVerticalAlignment(SwingConstants.CENTER);
        leftBpm.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes song genre text box on left mixer with position size and graphic properties
    private void setupLeftGenre() {
        leftGenre = new JLabel("Genre");
        leftGenre.setForeground(Color.WHITE);
        leftGenre.setFont(new Font("Kiona", Font.PLAIN, 40));
        leftGenre.setSize(BAR_HEIGHT * 11 / 10, BAR_HEIGHT * 2 / 3);
        leftGenre.setLocation(BAR_WIDTH * 43 / 120, FRAME_HEIGHT * 26 / 80);
        leftGenre.setVerticalAlignment(SwingConstants.CENTER);
        leftGenre.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: updates left mixer components according to their proper values
    private void updateLeftMixer() {
        leftKey.setText(player.getCurrentSongKey());
        leftBpm.setText(player.getCurrentSongBPM() + " BPM");
        if (player.getCurrentSongGenre() != null) {
            leftGenre.setText(player.getCurrentSongGenre().replaceAll(" ", "\n"));
        } else {
            leftGenre.setText("Unknown");
        }

        String[] artists = player.getCurrentSongArtists();
        String artistsDisplay = artists[0];
        for (int i = 1; i < artists.length; i++) {
            artistsDisplay = artistsDisplay + ", " + artists[i];
        }
        leftArtists.setText(artistsDisplay);
        leftName.setText(player.getCurrentSongName());
    }

    // MODIFIES: this
    // EFFECTS: resets left mixer component values to their base
    private void resetLeftMixer() {
        leftKey.setText("Key");
        leftBpm.setText("BPM");
        leftGenre.setText("Genre");
        leftArtists.setText("Artists");
        leftName.setText("Name");
    }

    // MODIFIES: this
    // EFFECTS: updates right mixer components according to their proper values
    private void updateRightMixer() {
        rightKey.setText(player.getNextSongKey());
        rightBpm.setText(player.getNextSongBPM() + " BPM");
        if (player.getNextSongGenre() != null) {
            rightGenre.setText(player.getNextSongGenre().replaceAll(" ", "\n"));
        } else {
            rightGenre.setText("Unknown");
        }

        String[] artists = player.getNextSongArtists();
        String artistsDisplay = artists[0];
        for (int i = 1; i < artists.length; i++) {
            artistsDisplay = artistsDisplay + ", " + artists[i];
        }
        rightArtists.setText(artistsDisplay);
        rightName.setText(player.getNextSongName());
    }

    // MODIFIES: this
    // EFFECTS: resets right mixer component values to their base
    private void resetRightMixer() {
        rightKey.setText("Key");
        rightBpm.setText("BPM");
        rightGenre.setText("Genre");
        rightArtists.setText("Artists");
        rightName.setText("Name");
    }

    // MODIFIES: this
    // EFFECTS: updates the mixers or resets them if nothing is queue-able.
    private void updateMixers() {
        if (!player.getCurrentSetSongs().isEmpty()) {
            updateLeftMixer();
            updateRightMixer();
        } else {
            resetLeftMixer();
            resetRightMixer();
        }
    }

    // MODIFIES: this
    // EFFECTS: handles the set up for the components of the left mixer
    private void leftMixerComponentHandler() {
        setupLeftPitch();
        setupLeftVolume();
        setupLeftSpeed();
        setupLeftName();
        setupLeftArtists();
        setupLeftKey();
        setupLeftBpm();
        setupLeftGenre();
    }

    // MODIFIES: this
    // EFFECTS: initializes left mixer panel with position, size, graphic properties & components
    private void setupLeftMixer() {
        leftMixerComponentHandler();

        leftMixer = new JPanel();
        leftMixer.setLayout(null);
        leftMixer.setOpaque(false);
        leftMixer.add(leftPitch);
        leftMixer.add(leftVolume);
        leftMixer.add(leftSpeed);
        leftMixer.add(leftName);
        leftMixer.add(leftArtists);
        leftMixer.add(leftKey);
        leftMixer.add(leftBpm);
        leftMixer.add(leftGenre);
        leftMixer.setBounds(0, BAR_HEIGHT, BAR_WIDTH / 2, FRAME_HEIGHT - 2 * BAR_HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS: initializes initialize song button with its icon, position, size, graphic properties and click effect
    private void setupInitializeSong() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("./data/images/buttonNextSet.png"));
            initializeSong = new JButton(new ImageIcon(
                    img.getScaledInstance(BAR_HEIGHT * 6 / 30, BAR_HEIGHT * 5 / 30, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initializeSong.setOpaque(false);
        initializeSong.setBorderPainted(false);
        initializeSong.setContentAreaFilled(false);
        initializeSong.setSize(BAR_HEIGHT / 5, BAR_HEIGHT / 6);
        initializeSong.setLocation(BAR_WIDTH * 106 / 112, BAR_HEIGHT * 12 / 50);
        initializeSong.addActionListener(
                new AbstractAction("initialize song") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionInitializeSong();
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: takes user input to initialize a new song object and write it into the pool
    private void actionInitializeSong() {
        String[] songInfo = new String[3];
        songInfo[0] = JOptionPane.showInputDialog("Enter file name");
        songInfo[1] = JOptionPane.showInputDialog("Enter BPM");
        songInfo[2] = JOptionPane.showInputDialog("Enter Key");

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
    // EFFECTS: initializes remove song button with its icon, position, size, graphic properties and click effect
    private void setupRemoveSong() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("./data/images/buttonNextSet.png"));
            removeSong = new JButton(new ImageIcon(
                    img.getScaledInstance(BAR_HEIGHT * 5 / 30, BAR_HEIGHT * 4 / 30, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        removeSong.setOpaque(false);
        removeSong.setBorderPainted(false);
        removeSong.setContentAreaFilled(false);
        removeSong.setSize(BAR_HEIGHT / 5, BAR_HEIGHT / 6);
        removeSong.setLocation(BAR_WIDTH * 100 / 112, BAR_HEIGHT * 18 / 50);
        removeSong.addActionListener(
                new AbstractAction("remove song") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionRemoveSong();
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: takes user input to remove a specified song from the current set
    private void actionRemoveSong() {
        String[] songList = new String[player.getCurrentSetSongs().size()];
        for (int i = 0; i < player.getCurrentSetSongs().size(); i++) {
            songList[i] = player.getCurrentSetSongs().get(i).getName();
        }
        String songName = (String) JOptionPane.showInputDialog(bottomBar, "Remove which song?", "Choose",
                JOptionPane.QUESTION_MESSAGE, null, songList, songList[0]);

        player.removeSongFromSet(songName, player.getCurrentSetTitle());
    }

    // MODIFIES: this
    // EFFECTS: initializes add song button with its icon, position, size, graphic properties and click effect
    private void setupAddSong() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("./data/images/buttonPrevSet.png"));
            addSong = new JButton(new ImageIcon(
                    img.getScaledInstance(BAR_HEIGHT * 5 / 30, BAR_HEIGHT * 4 / 30, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addSong.setOpaque(false);
        addSong.setBorderPainted(false);
        addSong.setContentAreaFilled(false);
        addSong.setSize(BAR_HEIGHT / 5, BAR_HEIGHT / 6);
        addSong.setLocation(BAR_WIDTH * 100 / 112, BAR_HEIGHT * 10 / 50);
        addSong.addActionListener(
                new AbstractAction("add song") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionAddSong();
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: takes user input to add a specified song to the current
    private void actionAddSong() {
        String[] songList = new String[player.getSongs().size()];
        for (int i = 0; i < player.getSongs().size(); i++) {
            songList[i] = player.getSongs().get(i).getName();
        }
        String songName = (String) JOptionPane.showInputDialog(bottomBar, "Add which song?", "Choose",
                JOptionPane.QUESTION_MESSAGE, null, songList, songList[0]);

        player.addSongToSet(songName, player.getCurrentSetTitle());
    }

    // MODIFIES: this
    // EFFECTS: initializes view songs button with its icon, position, size, graphic properties and click effect
    private void setupViewSongs() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("./data/images/buttonSongs.png"));
            viewSongs = new JButton(new ImageIcon(
                    img.getScaledInstance(BAR_HEIGHT * 31 / 32, BAR_HEIGHT * 8 / 32, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewSongs.setOpaque(false);
        viewSongs.setBorderPainted(false);
        viewSongs.setContentAreaFilled(false);
        viewSongs.setSize(BAR_HEIGHT * 31 / 32, BAR_HEIGHT * 8 / 32);
        viewSongs.setLocation(BAR_WIDTH * 43 / 56, BAR_HEIGHT * 12 / 50);
        viewSongs.addActionListener(
                new AbstractAction("view songs") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionViewSongs();
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: pops up a table displaying all the songs and their data in the current set
    private void actionViewSongs() {
        String[] titles = {"Song Name", "Artists", "BPM", "Key", "Genre", "Length"};
        String[][] data = new String[player.getCurrentSetSongs().size()][6];
        for (Song s : player.getCurrentSetSongs()) {
            data[player.getCurrentSetSongs().indexOf(s)][0] = s.getName();
            data[player.getCurrentSetSongs().indexOf(s)][1] = Arrays.toString(s.getArtists());
            data[player.getCurrentSetSongs().indexOf(s)][2] = String.valueOf(s.getBPM());
            data[player.getCurrentSetSongs().indexOf(s)][3] = s.getKey();
            data[player.getCurrentSetSongs().indexOf(s)][4] = s.getGenre();
            data[player.getCurrentSetSongs().indexOf(s)][5] = (int) Math.floor(s.getLength() / 60)
                    + ":" + Math.round(s.getLength() % 60);
        }
        JDialog popup = new JDialog();
        JTable setTable = new JTable(data, titles);
        popup.add(setTable);
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.setVisible(true);
        popup.pack();
    }

    // MODIFIES: this
    // EFFECTS: initializes remove set button with its icon, position, size, graphic properties and click effect
    private void setupRemoveSet() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("./data/images/buttonNextSet.png"));
            removeSet = new JButton(new ImageIcon(
                    img.getScaledInstance(BAR_HEIGHT * 5 / 30, BAR_HEIGHT * 4 / 30, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        removeSet.setOpaque(false);
        removeSet.setBorderPainted(false);
        removeSet.setContentAreaFilled(false);
        removeSet.setSize(BAR_HEIGHT / 5, BAR_HEIGHT / 6);
        removeSet.setLocation(BAR_WIDTH * 79 / 112, BAR_HEIGHT * 18 / 50);
        removeSet.addActionListener(
                new AbstractAction("remove set") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionRemoveSet();
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: takes in user input to remove a specified set
    private void actionRemoveSet() {
        String input = "";
        input = JOptionPane.showInputDialog("Enter the name of the set you want to delete");

        player.removeSet(input);
    }

    // MODIFIES: this
    // EFFECTS: initializes add set button with its icon, position, size, graphic properties and click effect
    private void setupAddSet() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("./data/images/buttonPrevSet.png"));
            addSet = new JButton(new ImageIcon(
                    img.getScaledInstance(BAR_HEIGHT * 5 / 30, BAR_HEIGHT * 4 / 30, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addSet.setOpaque(false);
        addSet.setBorderPainted(false);
        addSet.setContentAreaFilled(false);
        addSet.setSize(BAR_HEIGHT / 5, BAR_HEIGHT / 6);
        addSet.setLocation(BAR_WIDTH * 79 / 112, BAR_HEIGHT * 10 / 50);
        addSet.addActionListener(
                new AbstractAction("add set") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionAddSet();
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: takes in user input to produce a new set
    private void actionAddSet() {
        String[] setInfo = new String[2];
        setInfo[0] = JOptionPane.showInputDialog("Enter set name");
        setInfo[1] = JOptionPane.showInputDialog("Enter set genre");

        player.makeNewSet(setInfo[0], setInfo[1]);
        actionNextSet(0);
    }

    // MODIFIES: this
    // EFFECTS: initializes view sets button with its icon, position, size, graphic properties and click effect
    private void setupViewSets() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("./data/images/buttonSets.png"));
            viewSets = new JButton(new ImageIcon(
                    img.getScaledInstance(BAR_HEIGHT * 24 / 32, BAR_HEIGHT * 8 / 32, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewSets.setOpaque(false);
        viewSets.setBorderPainted(false);
        viewSets.setContentAreaFilled(false);
        viewSets.setSize(BAR_HEIGHT * 24 / 32, BAR_HEIGHT * 8 / 32);
        viewSets.setLocation(BAR_WIDTH * 17 / 28, BAR_HEIGHT * 12 / 50);
        viewSets.addActionListener(
                new AbstractAction("view sets") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionViewSets();
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: pops up a table displaying the list of sets & subsequent data
    private void actionViewSets() {
        String[] titles = {"Set Name", "Set Genre", "# of Songs"};
        Object[][] data = new Object[player.getAllSets().size()][3];
        for (Set s : player.getAllSets()) {
            data[player.getAllSets().indexOf(s)][0] = s.getName();
            data[player.getAllSets().indexOf(s)][1] = s.getGenre();
            data[player.getAllSets().indexOf(s)][2] = s.getSongs().size();
        }
        JDialog popup = new JDialog();
        JTable setTable = new JTable(data, titles);
        popup.add(setTable);
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.setVisible(true);
        popup.pack();
    }

    // MODIFIES: this
    // EFFECTS: initializes skip button with its icon, position, size, graphic properties and click effect
    private void setupSkip() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("./data/images/buttonSkip.png"));
            skip = new JButton(new ImageIcon(
                    img.getScaledInstance(BAR_HEIGHT * 17 / 32, BAR_HEIGHT * 10 / 32, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        skip.setOpaque(false);
        skip.setBorderPainted(false);
        skip.setContentAreaFilled(false);
        skip.setSize(BAR_HEIGHT * 17 / 32, BAR_HEIGHT * 10 / 32);
        skip.setLocation(BAR_WIDTH * 13 / 28, BAR_HEIGHT * 11 / 50);
        skip.addActionListener(
                new AbstractAction("skip") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        skip();
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: handles the player's skip to the next song and updates graphics
    private void skip() {
        player.handleSongEnd();
        updateRemaining();
        updatePlayButton();
        updateMixers();
        transitionSliders();
    }

    // MODIFIES: this
    // EFFECTS: initializes play button with its icon, position, size, graphic properties and click effect
    private void setupPlay() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("./data/images/buttonPlay.png"));
            play = new JButton(new ImageIcon(
                    img.getScaledInstance(BAR_HEIGHT * 4 / 8, BAR_HEIGHT * 4 / 8, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        play.setOpaque(false);
        play.setBorderPainted(false);
        play.setContentAreaFilled(false);
        play.setSize(BAR_HEIGHT * 4 / 8, BAR_HEIGHT * 4 / 8);
        play.setLocation(BAR_WIDTH * 10 / 28, BAR_HEIGHT / 8);
        play.addActionListener(
                new AbstractAction("play") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionPlay();
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: toggles the player's status & updates play button graphics
    private void actionPlay() {
        player.togglePlay();
        updatePlayButton();
        updateMixers();
    }

    // MODIFIES: this
    // EFFECTS: updates play button graphics based on player status
    private void updatePlayButton() {
        BufferedImage img;
        try {
            if (player.getPlayerStatus().equals("playing")) {
                img = ImageIO.read(new File("./data/images/buttonPause.png"));
            } else {
                img = ImageIO.read(new File("./data/images/buttonPlay.png"));
            }
            play.setIcon(new ImageIcon(
                    img.getScaledInstance(BAR_HEIGHT * 4 / 8, BAR_HEIGHT * 4 / 8, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes remaining song text box with its icon, position, size, graphic properties
    private void setupSongsLeft() {
        songsLeft = new JLabel("0 songs @ 0:00");
        songsLeft.setForeground(Color.WHITE);
        songsLeft.setFont(new Font("Kiona", Font.PLAIN, 30));
        songsLeft.setSize(BAR_WIDTH / 3, FRAME_HEIGHT * 3 / 30);
        songsLeft.setLocation(BAR_WIDTH / 15, BAR_HEIGHT / 6);

        timer = new Timer(100, this);
        timer.setInitialDelay(5000);
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: runs all the component set up for the bottom bar panel
    private void bottomBarComponentHandler() {
        setupSongsLeft();
        setupPlay();
        setupSkip();
        setupViewSets();
        setupAddSet();
        setupRemoveSet();
        setupViewSongs();
        setupAddSong();
        setupRemoveSong();
        setupInitializeSong();
    }

    // MODIFIES: this
    // EFFECTS: initializes bottom bar panel with position, size, graphic properties & components
    private void setupBottomBar() {
        bottomBarComponentHandler();

        bottomBar = new JPanel();
        bottomBar.setLayout(null);
        bottomBar.setOpaque(false);
        bottomBar.add(songsLeft);
        bottomBar.add(play);
        bottomBar.add(skip);
        bottomBar.add(viewSets);
        bottomBar.add(addSet);
        bottomBar.add(removeSet);
        bottomBar.add(viewSongs);
        bottomBar.add(addSong);
        bottomBar.add(removeSong);
        bottomBar.add(initializeSong);
        bottomBar.setBounds(0, FRAME_HEIGHT - BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS: initializes set name text box with its text, position, size, graphic properties
    private void setupSetName() {
        setName = new JLabel("SET NAME");
        setName.setForeground(Color.WHITE);
        setName.setFont(new Font("LEMON MILK", Font.PLAIN, 60));
        setName.setSize(BAR_WIDTH / 3, FRAME_HEIGHT * 3 / 30);
        setName.setLocation(BAR_WIDTH * 2 / 7, FRAME_HEIGHT / 15);
    }

    // MODIFIES: this
    // EFFECTS: initializes previous set button with its icon, position, size, graphic properties and click effect
    private void setupPrevSet() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("./data/images/buttonPrevSet.png"));
            prevSet = new JButton(new ImageIcon(
                    img.getScaledInstance(BAR_HEIGHT / 5, BAR_HEIGHT / 6, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        prevSet.setOpaque(false);
        prevSet.setBorderPainted(false);
        prevSet.setContentAreaFilled(false);
        prevSet.setSize(BAR_HEIGHT / 5, BAR_HEIGHT / 6);
        prevSet.setLocation(BAR_WIDTH * 20 / 28, BAR_HEIGHT / 2);
        prevSet.addActionListener(
                new AbstractAction("previous set") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (player.getAllSets().size() > 0) {
                            actionNextSet(-1);
                        }
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: selects the next set for the player and updates set name text and play button
    private void actionNextSet(int nextSet) {
        player.selectSet(nextSet);
        setName.setText(player.getCurrentSetTitle());
        updatePlayButton();
        updateMixers();
    }

    // MODIFIES: this
    // EFFECTS: initializes next set button with its icon, position, size, graphic properties and click effect
    private void setupNextSet() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("./data/images/buttonNextSet.png"));
            nextSet = new JButton(new ImageIcon(
                    img.getScaledInstance(BAR_HEIGHT / 5, BAR_HEIGHT / 6, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        nextSet.setOpaque(false);
        nextSet.setBorderPainted(false);
        nextSet.setContentAreaFilled(false);
        nextSet.setSize(BAR_HEIGHT / 5, BAR_HEIGHT / 6);
        nextSet.setLocation(BAR_WIDTH * 21 / 28, BAR_HEIGHT / 2);
        nextSet.addActionListener(
                new AbstractAction("next set") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (player.getAllSets().size() > 0) {
                            actionNextSet(1);
                        }
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: initializes save button with its icon, position, size, graphic properties and click effect
    private void setupSave() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("./data/images/buttonSave.png"));
            save = new JButton(new ImageIcon(
                    img.getScaledInstance(BAR_HEIGHT / 2, BAR_HEIGHT / 2, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        save.setOpaque(false);
        save.setBorderPainted(false);
        save.setContentAreaFilled(false);
        save.setSize(BAR_HEIGHT / 2, BAR_HEIGHT / 2);
        save.setLocation(BAR_WIDTH * 49 / 60, BAR_HEIGHT / 4);
        save.addActionListener(
                new AbstractAction("save sets") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionSave();
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: writes current sets to file
    private void actionSave() {
        String fileName = JOptionPane.showInputDialog("Enter file name");

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
    // EFFECTS: initializes load button with its icon, position, size, graphic properties and click effect
    private void setupLoad() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("./data/images/buttonLoad.png"));
            load = new JButton(new ImageIcon(
                    img.getScaledInstance(BAR_HEIGHT / 2, BAR_HEIGHT / 2, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        load.setOpaque(false);
        load.setBorderPainted(false);
        load.setContentAreaFilled(false);
        load.setSize(BAR_HEIGHT / 2, BAR_HEIGHT / 2);
        load.setLocation(BAR_WIDTH * 54 / 60, BAR_HEIGHT / 4);
        load.addActionListener(
                new AbstractAction("load sets") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionLoad();
                    }
                }
        );
    }

    // MODIFIES: this
    // EFFECTS: loads sets from file
    private void actionLoad() {
        String fileName = "";
        JFileChooser chosenFile = new JFileChooser();
        int chosen = chosenFile.showOpenDialog(topBar);

        if (chosen == JFileChooser.APPROVE_OPTION) {
            fileName = chosenFile.getSelectedFile().getName();
            fileName = fileName.substring(0, fileName.length() - 5);
        }

        try {
            jsonReader = new JsonReader(fileName);
            player.setListOfSets(jsonReader.getSetsFromFile(player.getSongs()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File Not Found");
        }

        player.selectSet(0);
        updateRemaining();
        updateMixers();
        setName.setText(player.getCurrentSetTitle());
    }

    // MODIFIES: this
    // EFFECTS: initializes top bar panel with its components, position, size, graphic properties
    private void setupTopBar() {
        setupSetName();
        setupPrevSet();
        setupNextSet();
        setupSave();
        setupLoad();

        topBar = new JPanel();
        topBar.setLayout(null);
        topBar.setOpaque(false);
        topBar.add(setName);
        topBar.add(prevSet);
        topBar.add(nextSet);
        topBar.add(save);
        topBar.add(load);
        topBar.setBounds(0, 0, BAR_WIDTH, BAR_HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS: initializes background
    private void setupBackground() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("./data/images/background.jpg"));
            bkg = new JLabel(new ImageIcon(img.getScaledInstance(FRAME_WIDTH, FRAME_HEIGHT, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bkg.setLocation(0, 0);
        bkg.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS: adds gui components onto the frame and sets up frame's graphical properties
    private void attachToFrame() {
        frame.add(leftMixer);
        frame.add(rightMixer);
        frame.add(bottomBar);
        frame.add(topBar);
        frame.add(bkg);
        frame.setVisible(true);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS: initializes frame and sets proper closing function
    private void setupFrame() {
        frame = new JFrame("DJXp");
        frame.setLayout(null);
        frame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
        frame.getContentPane().setBackground(Color.BLACK);
    }

    // MODIFIES: this
    // EFFECTS: handles the proper graphic display of the remaining songs
    private void updateRemaining() {
        songsLeft.setText(player.getRemainingSetSongs() + " songs @ "
                + (int) Math.floor(player.getRemainingSetTime() / 60)
                + ":" + Math.round(player.getRemainingSetTime() % 60));
    }

    // MODIFIES: this
    // EFFECTS: runs every tenth of a second on a timer to update the remaining songs text and checks for song ends
    @Override
    public void actionPerformed(ActionEvent e) {
        if (player.getPlayerStatus().equals("playing")) {
            updateRemaining();
        }
        if (player.isEnd()) {
            player.handleSongEnd();
            updatePlayButton();
            updateMixers();
            transitionSliders();
        }
    }
}
