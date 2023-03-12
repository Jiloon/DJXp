package persistence;

import model.Set;
import model.Song;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

// Represents a file writer that converts sets and songs into their JSON representations and stores them in a file
public class JsonWriter {
    private static final int INDENT = 4;
    public static final String FOLDER_LOCATION = "./data/";
    public static final String POOL_LOCATION = "./data/songPool.json";
    private PrintWriter printWriter;
    private final String filePath;

    // EFFECTS: constructs json writer to write json to the given file path
    public JsonWriter(String givenFileName) {
        filePath = FOLDER_LOCATION + givenFileName + ".json";
    }

    // MODIFIES: this
    // EFFECTS: opens file to write; throws FileNotFoundException if file can't be found
    public void open() throws FileNotFoundException {
        printWriter = new PrintWriter(filePath);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representations of the list of sets to the file
    public void write(ArrayList<Set> sets) {
        JSONObject json = new JSONObject();
        json.put("sets", setsToJson(sets));
        printWriter.print(json.toString(INDENT));
    }

    // MODIFIES: this
    // EFFECTS: closes writer/file
    public void close() {
        printWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: opens file and writes JSON representation of the song pool to file, then closes
    public void writePool(ArrayList<Song> songs) throws FileNotFoundException {
        printWriter = new PrintWriter(POOL_LOCATION);
        JSONObject json = new JSONObject();
        json.put("pool", songsToJson(songs));
        printWriter.print(json.toString(INDENT));
        printWriter.close();
    }

    // EFFECTS: returns the JSON representation of songs as a JSON array
    private JSONArray songsToJson(ArrayList<Song> songs) {
        JSONArray jsonArray = new JSONArray();

        for (Song song : songs) {
            jsonArray.put(songToJson(song));
        }

        return jsonArray;
    }

    // EFFECTS: returns the JSON representation of sets as a JSON array
    private JSONArray setsToJson(ArrayList<Set> sets) {
        JSONArray jsonArray = new JSONArray();

        for (Set set : sets) {
            jsonArray.put(setToJson(set));
        }

        return jsonArray;
    }

    // EFFECTS: returns JSON representation of song
    private JSONObject songToJson(Song song) {
        JSONObject json = new JSONObject();
        json.put("name", song.getName());
        json.put("bpm", song.getBPM());
        json.put("length", song.getLength());
        json.put("key", song.getKey());
        json.put("genre", song.getGenre());
        json.put("artists", song.getArtists());
        json.put("file", song.getFileName());
        return json;
    }

    // EFFECTS: returns JSON representation of set
    private JSONObject setToJson(Set set) {
        JSONObject json = new JSONObject();
        json.put("name", set.getName());
        json.put("genre", set.getGenre());
        json.put("songs", songsToJson(set.getSongs()));
        return json;
    }
}
