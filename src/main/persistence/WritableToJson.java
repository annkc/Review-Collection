package persistence;

import org.json.JSONObject;

public interface WritableToJson {
    // EFFECTS: returns this as JSON object
    JSONObject toJsonObject();

}
