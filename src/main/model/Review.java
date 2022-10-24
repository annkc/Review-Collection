package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.WritableToJson;

import java.util.ArrayList;

/*
 * Represents a review of a work, including the name of the work being
 * reviewed, the creators of the work, the title of the review, the body text
 * of the review, and a rating score out of 10.
 *
 * (Referenced the WorkRoom class in the JsonSerializationDemo project)
 */

public class Review implements WritableToJson {

    public static final int RATING_TOTAL = 10;      // total which the review rating is out of

    private String workTitle;                       // title of the work being reviewed
    private ArrayList<String> workCreators;         // names of creators of the work (eg. authors, directors, etc)
    private String reviewTitle;                     // title of the review
    private int rating;                             // whole number rating of the work out of RATING_TOTAL
    private ArrayList<String> reviewText;           // paragraphs that make up body text of the review



    /*
     * EFFECTS: title of the work being reviewed is set to  workTitle;
     * 		    title of the review is set to reviewTitle; the rating
     *          out of the total is set to rating
     */
    public Review(String workTitle, String reviewTitle, int rating) {
        this.workTitle = workTitle;
        this.reviewTitle = reviewTitle;
        this.rating = rating;
        workCreators = new ArrayList<>();
        reviewText = new ArrayList<>();
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public ArrayList<String> getWorkCreators() {
        return workCreators;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public ArrayList<String> getReviewText() {
        return reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setWorkTitle(String medTitle) {
        workTitle = medTitle;
    }

    public void setReviewTitle(String revTitle) {
        reviewTitle = revTitle;
    }

    /*
     * REQUIRES: r >= 0 AND r <= and RATING_TOTAL
     * MODIFIES: this
     * EFFECTS: the rating is set to r
     */
    public void setRating(int r) {
        rating = r;
    }

    /*
     * REQUIRES: workCreator.length() > 0
     * MODIFIES: this
     * EFFECTS: the name of a creator, workCreator, of the work being reviewed
     *          is added to the list of creators
     */
    public void addWorkCreator(String workCreator) {
        workCreators.add(workCreator);
    }

    /*
     * MODIFIES: this
     * EFFECTS: the list of work creators is cleared
     */
    public void clearWorkCreators() {
        workCreators = new ArrayList<>();
    }

    /*
     * REQUIRES: paragraph.length() > 0
     * MODIFIES: this
     * EFFECTS: adds a paragraph to the body text of the review
     *
     */
    public void addParagraphToReviewText(String paragraph) {
        reviewText.add(paragraph);
    }

    /*
     * MODIFIES: this
     * EFFECTS: the review's body text is cleared
     */
    public void clearReviewText() {
        reviewText = new ArrayList<>();
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("work title", workTitle);
        jsonObject.put("review title", reviewTitle);
        jsonObject.put("rating", rating);
        jsonObject.put("work creators", workCreatorsToJsonArray());
        jsonObject.put("review text", reviewTextToJsonArray());

        return jsonObject;
    }


    /*
     * EFFECTS: returns work creators in the review as a JSON array
     */
    private JSONArray workCreatorsToJsonArray() {
        JSONArray jsonArray = new JSONArray();
        for (String wc : workCreators) {
            JSONObject jsonObjectToAdd = new JSONObject();
            jsonObjectToAdd.put("name", wc);
            jsonArray.put(jsonObjectToAdd);
        }

        return jsonArray;
    }

    /*
     * EFFECTS: returns review text in the review as a JSON array
     */
    private JSONArray reviewTextToJsonArray() {
        JSONArray jsonArray = new JSONArray();
        for (String paragraph : reviewText) {
            JSONObject jsonObjectToAdd = new JSONObject();
            jsonObjectToAdd.put("paragraph", paragraph);
            jsonArray.put(jsonObjectToAdd);
        }

        return jsonArray;
    }

}
