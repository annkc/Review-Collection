package persistence;

import model.ReviewCollection;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of a review collection to file
// (Modeled after the JsonWriter class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//  and adjusted to write ReviewCollections instead)
public class JsonWriter {

    private static final int NUM_TAB_SPACES = 4; // the number of spaces that a tab is made up of

    private PrintWriter writer; // the writer that writes the review collection to file
    private String destinationFilePath; // the path of the file to write to

    // EFFECTS: sets the destination of the file the writer is to write to
    //          to destinationFilePath
    public JsonWriter(String destinationFilePath) {
        this.destinationFilePath = destinationFilePath;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer
    //          throws FileNotFoundException if the file that is the destination
    //          that the writer is supposed to write to is not found
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destinationFilePath));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of reviewCollection to file
    public void write(ReviewCollection reviewCollection) {
        JSONObject reviewCollectionJson = reviewCollection.toJsonObject();
        writeStringToFile(reviewCollectionJson.toString(NUM_TAB_SPACES));
    }

    // MODIFIES: this
    // EFFECTS: writes jsonString to file
    private void writeStringToFile(String jsonString) {
        writer.print(jsonString);
    }
}
