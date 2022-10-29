package persistence;

import org.json.JSONObject;

// (from the Writable Interface in the JsonSerializationDemo project)
public interface WritableToJson {
    // EFFECTS: returns this as JSON object
    JSONObject toJsonObject();

}
