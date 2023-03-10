package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.WritableToJson;

import java.util.ArrayList;

/*
 * Represents a collection of an arbitrary number of reviews.
 *
 * (Referenced WorkRoom class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 *      toJsonObject() is modeled after toJson()
 *      collectionReviewsToJsonArray() is modeled after thingiesToJson()
 * )
 */

public class ReviewCollection implements WritableToJson {

    private ArrayList<Review> collection; // a list of the reviews in the collection

    /*
     * EFFECTS: sets the list of reviews in the collection to an empty list
     */
    public ReviewCollection() {
        collection = new ArrayList<>();
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a review at the end of the list of reviews in the collection
     *          and logs the addition of the review
     */
    public void addReview(Review review) {
        collection.add(review);
        EventLog.getInstance().logEvent(new Event("Review '" + review.getReviewTitle() + "' added to collection."));
    }

    /*
     * REQUIRES: review must be a review in the collection
     * MODIFIES: this
     * EFFECTS: removes review from the collection
     *          and logs the removal of the review
     */
    public void removeReview(Review review) {
        collection.remove(review);
        EventLog.getInstance().logEvent(new Event("Review '" + review.getReviewTitle() + "' removed from collection."));
    }

    /*
     * EFFECTS: returns a list of the titles of all the reviews in the collection
     */
    public ArrayList<String> getReviewTitlesList() {
        ArrayList<String> titleList =  new ArrayList<>();
        for (Review review : collection) {
            titleList.add(review.getReviewTitle());
        }

        return titleList;
    }

    /*
     * REQUIRES: index >= 0
     * EFFECTS: if there is a review at position index in the collection, counting from 0,
     *          returns that review, otherwise returns null
     */
    public Review getReviewAt(int index) {
        if (index < collection.size()) {
            return collection.get(index);
        } else {
            return null;
        }
    }


    /*
     * EFFECTS: returns this as JSON object
     */
    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reviews", collectionReviewsToJsonArray());
        return jsonObject;
    }

    /*
     * EFFECTS: returns reviews in the review collection as a JSON array
     */
    private JSONArray collectionReviewsToJsonArray() {
        JSONArray jsonArray = new JSONArray();

        for (Review r : collection) {
            jsonArray.put(r.toJsonObject());
        }

        return jsonArray;
    }

}
