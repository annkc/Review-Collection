package persistence;

import org.json.JSONObject;

// Something that can be written as JSON data
// (from the Writable Interface in the JsonSerializationDemo project)
public interface WritableToJson {
    // EFFECTS: returns this as JSON object
    JSONObject toJsonObject();

}
