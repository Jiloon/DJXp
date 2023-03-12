package persistence;

import model.Set;
import model.Song;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import static persistence.JsonWriter.FOLDER_LOCATION;
import static persistence.JsonWriter.POOL_LOCATION;

public class JsonReader {
    private final String filePath;

    // EFFECTS: constructs json reader to reader json from the given file path
    public JsonReader(String givenFilePath) {
        filePath = FOLDER_LOCATION + givenFilePath + ".json";
    }

    // EFFECTS: reads json data from song pool file and returns appropriate song pool
    public ArrayList<Song> getSongsFromFile() throws IOException {
        ArrayList<Song> songList = new ArrayList<>();
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(POOL_LOCATION), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        String jsonData = contentBuilder.toString();

        JSONObject json = new JSONObject(jsonData);
        JSONArray jsonSongs = json.getJSONArray("pool");

        for (Object jsonSong : jsonSongs) {
            JSONObject songObject = (JSONObject) jsonSong;
            songList.add(constructSong(songObject));
        }

        return songList;
    }

    // EFFECTS: reads json data from given set file and returns appropriate set
    public ArrayList<Set> getSetsFromFile(ArrayList<Song> songPool) throws IOException {
        ArrayList<Set> setList = new ArrayList<>();
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        String jsonData = contentBuilder.toString();

        JSONObject json = new JSONObject(jsonData);
        JSONArray jsonSets = json.getJSONArray("sets");

        for (Object jsonSet : jsonSets) {
            JSONObject setObject = (JSONObject) jsonSet;
            setList.add(constructSet(setObject, songPool));
        }

        return setList;
    }

    // EFFECTS: constructs a set off of given json data and pulling from respective songs from the song pool
    private Set constructSet(JSONObject json, ArrayList<Song> songPool) {
        String name = json.getString("name");
        String genre = json.getString("genre");
        JSONArray jsonSongs = json.getJSONArray("songs");
        Set set = new Set(name, genre);

        for (Object jsonSong : jsonSongs) {
            JSONObject songObject = (JSONObject) jsonSong;
            for (Song s : songPool) {
                if (s.getFileName().equals(songObject.getString("file"))) {
                    set.addSongToSet(s);
                }
            }
        }

        return set;
    }

    // EFFECTS: constructs a song off of given json data
    private Song constructSong(JSONObject json) {
        String fileName = json.getString("file");
        int bpm = json.getInt("bpm");
        String key = json.getString("key");
        return new Song(fileName, bpm, key);
    }


}
