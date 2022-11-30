package persistence;

import org.json.JSONObject;

// Something that can be written as JSON data
// (Modeled after the Writable Interface from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
public interface WritableToJson {
    // EFFECTS: returns this as JSON object
    JSONObject toJsonObject();

}
