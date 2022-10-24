package persistence;

import model.Review;
import model.ReviewCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads JSON data on file for review collection
// (Referenced the JsonReader Class in the JsonSerializationDemo project)
public class JsonReader {

    private String sourceFilePath;

    // EFFECTS: sets the path of the source file to sourceFilePath
    public JsonReader(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    // EFFECTS: reads review collection from file, then returns the review collection
    //          throws IOException if there is an error during the process of reading
    public ReviewCollection read() throws IOException {
        String readJsonData = readFile(sourceFilePath);
        JSONObject readJsonObject = new JSONObject(readJsonData);
        return parseReviewCollection(readJsonObject);
    }

    // EFFECTS: reads source file as string, then returns the string
    private String readFile(String source) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses review collection from jsonObject, then returns the review collection
    private ReviewCollection parseReviewCollection(JSONObject jsonObject) {
        ReviewCollection collection = new ReviewCollection();
        addReviews(collection, jsonObject);
        return collection;
    }

    // MODIFIES: collection
    // EFFECTS: parses reviews from jsonObject, then adds the reviews to collection
    private void addReviews(ReviewCollection collection, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("collection");
        for (Object object : jsonArray) {
            JSONObject nextReviewJson = (JSONObject) object;
            addReview(collection, nextReviewJson);
        }
    }

    // MODIFIES: collection
    // EFFECTS: parses review from jsonObject, then adds the review to collection
    private void addReview(ReviewCollection collection, JSONObject jsonObject) {
        String workTitle = jsonObject.getString("work title");
        String reviewTitle = jsonObject.getString("review title");
        int rating = jsonObject.getInt("rating");
        Review reviewToAdd = new Review(workTitle, reviewTitle, rating);
        addWorkCreators(reviewToAdd, jsonObject);
        addReviewText(reviewToAdd, jsonObject);
        collection.addReview(reviewToAdd);
    }

    // MODIFIES: review
    // EFFECTS: parses work creators from jsonObject, then adds the work creators to review
    private void addWorkCreators(Review review, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("work creators");
        for (Object object : jsonArray) {
            JSONObject nextWorkCreatorJson = (JSONObject) object;
            String workCreator = nextWorkCreatorJson.getString("name");
            review.addWorkCreator(workCreator);
        }
    }

    // MODIFIES: review
    // EFFECTS: parses review text from jsonObject, then adds the review text to review
    private void addReviewText(Review review, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("review text");
        for (Object object : jsonArray) {
            JSONObject nextParagraphJson = (JSONObject) object;
            String paragraph = nextParagraphJson.getString("paragraph");
            review.addParagraphToReviewText(paragraph);
        }
    }


}
