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
import java.util.ArrayList;

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
    private ArrayList<String> artists;
    private Media audio;
    private File audioFile;
    private double speed;
    private int volume;

    public Song(String fileName) throws NullPointerException {
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
        speed = 1.0;
        volume = 0;
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
        bpm = 0;
        songKey = "";
        genre = metadata.get("xmpDM:genre");
        name = metadata.get("dc:title");
        artists = new ArrayList<>();
        artists.add(metadata.get("xmpDM:artist"));

        /*
        for (String temp : metadata.names()) {
            System.out.println(temp);
        }
        */

        System.out.println(genre + name + artists);
    }

    public Media getAudio() {
        return audio;
    }
}
