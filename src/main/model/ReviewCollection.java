package model;

import java.util.ArrayList;

public class ReviewCollection {

    private ArrayList<Review> collection; // a list of the reviews in the collection

    /*
     * EFFECTS: sets the list of reviews in collections as an empty list of reviews
     *
     */
    public ReviewCollection() {
        collection = new ArrayList<>();
    }

    /*
     * REQUIRES: r.getReviewTitle must not be the same as the title of any review in the collection
     * MODIFIES: this
     * EFFECTS: adds a review at the end of the list of reviews in the collection
     *
     */
    public void addReview(Review r) {
        collection.add(r);
    }

    /*
     * REQUIRES: revTitle must be the title of a review in the collection
     * MODIFIES: this
     * EFFECTS: removes the review with the title revTitle from the collection
     *          if there is such a review, and returns whether the removal was successful
     *
     */
    public boolean deleteReview(String revTitle) {
        for (int i = 0; i < collection.size(); i++) {
            if (revTitle.equals(collection.get(i).getReviewTitle())) {
                collection.remove(i);
                return true;
            }
        }
        return false;
    }

    /*
     * EFFECTS: returns a list of the titles of all the review in the collection
     *
     */
    public ArrayList<String> getReviewTitlesList() {
        ArrayList<String> titleList =  new ArrayList<>();
        for (Review review : collection) {
            titleList.add(review.getReviewTitle());
        }

        return titleList;
    }
}
