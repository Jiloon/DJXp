package model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;

/*
  Represents a song object having a length, a bpm value, a key, a title, a genre, a set of contributing artists,
  playable audio, and its respective mp3 file
*/
public class Song {
    private double length;
    private int bpm;
    private String songKey;
    private String name;
    private String genre;
    private String[] artists;
    private Media audio;
    private File audioFile;

    // EFFECTS: Constructs song obj with a BPM of 0, an unknown key, its respective audio file accessed from the
    //          songs database, and pulls its audio. Also fills in length, genre, name, and artists fields from
    //          respective audio file's metadata
    public Song(String fileName) {
        bpm = 0;
        songKey = "Unknown";

        try {
            audioFile = new File("data/songs/" + fileName + ".mp3");
            audio = new Media(audioFile.toURI().toString());
            fillFromMetadata();
        } catch (MediaException | IOException e) {
            //System.out.println("No file of name \"" + fileName + "\" found");
            e.printStackTrace();
        } catch (TikaException e) {
            //System.out.println("Error opening MP3 metadata");
            e.printStackTrace();
        } catch (SAXException e) {
            //System.out.println("Parser encountered an error");
            e.printStackTrace();
        }
    }

    // EFFECTS: Constructs a song obj with the given BPM, the given key, its respective audio file accessed from the
    //          songs database, and pulls its audio. Also fills in length, genre, name, and artists fields from
    //          respective audio file's metadata
    public Song(String fileName, int givenBPM, String givenKey) {
        this(fileName);
        bpm = givenBPM;
        songKey = givenKey;
    }

    // MODIFIES: this
    // EFFECTS: Parses through the song's audio file's IDv3 header to acquire the respective metadata to fill in
    //          the song's length, genre, name, and artists fields
    protected void fillFromMetadata() throws IOException, TikaException, SAXException {
        InputStream input = new FileInputStream(audioFile);
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext context = new ParseContext();

        //parse through the MP3 IDv3 header to get the metadata info
        parser.parse(input, handler, metadata, context);
        input.close();

        length = Double.parseDouble(metadata.get("xmpDM:duration"));
        genre = metadata.get("xmpDM:genre");
        name = metadata.get("dc:title");
        artists = metadata.get("xmpDM:artist").split("/"); //IDv3 artist is one field, split by divider
    }

    public Media getAudio() {
        return audio;
    }

    public double getLength() {
        return length;
    }

    public int getBPM() {
        return bpm;
    }

    public String getKey() {
        return songKey;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String[] getArtists() {
        return artists;
    }
}
