package model;

import java.util.ArrayList;

/*
 * Represents a collection of an arbitrary number of reviews.
 *
 */

public class ReviewCollection {

    private ArrayList<Review> collection; // a list of the reviews in the collection

    /*
     * EFFECTS: sets the list of reviews in the collection to an empty list
     *
     */
    public ReviewCollection() {
        collection = new ArrayList<>();
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a review at the end of the list of reviews in the collection
     *
     */
    public void addReview(Review review) {
        collection.add(review);
    }

    /*
     * REQUIRES: review must be a review in the collection
     * MODIFIES: this
     * EFFECTS: removes review from the collection
     *
     */
    public void removeReview(Review review) {
        collection.remove(review);
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

    /*
     * REQUIRES: index >= 0
     * EFFECTS: returns the review at position index in the collection, counting from 0
     *
     */
    public Review getReviewAt(int index) {
        if (index < collection.size()) {
            return collection.get(index);
        } else {
            return null;
        }
    }
}
