package model;

import javafx.scene.media.Media;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.util.Arrays;

/*
  Represents a song object having a length, a bpm value, a key, a title, a set of contributing artists, playable audio,
  a playback speed, and a playback volume.
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

    public Song(String fileName) throws NullPointerException {
        bpm = 0;
        songKey = "Unknown";

        try {
            audioFile = new File("data/songs/" + fileName + ".mp3");
            audio = new Media(audioFile.toURI().toString());
            fillSongData();
        } catch (NullPointerException | IOException e) {
            System.out.println("No file of name \"" + fileName + "\" found");
            e.printStackTrace();
        } catch (TikaException e) {
            System.out.println("Error opening MP3 metadata");
        } catch (SAXException e) {
            System.out.println("Parser encountered an error");
            e.printStackTrace();
        }
    }

    public Song(String fileName, int givenBPM, String givenKey) {
        this(fileName);
        bpm = givenBPM;
        songKey = givenKey;
    }

    protected void fillSongData() throws IOException, TikaException, SAXException {
        InputStream input = new FileInputStream(audioFile);
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext context = new ParseContext();

        parser.parse(input, handler, metadata, context);
        input.close();

        length = Double.parseDouble(metadata.get("xmpDM:duration"));
        genre = metadata.get("xmpDM:genre");
        name = metadata.get("dc:title");
        //artists = metadata.get("xmpDM:artist").split("/");

        System.out.println(genre + name + Arrays.toString(artists));
    }

    public Media getAudio() {
        return audio;
    }

    public double getLength() {
        return length;
    }

    public int getBpm() {
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
