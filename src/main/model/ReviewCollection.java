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
     * REQUIRES: index must be less than the number of reviews in the
     * MODIFIES: this
     * EFFECTS: removes the review at position index, counting from zero,
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
     * REQUIRES: i >= 0 AND i must be less than the number of reviews in the collection
     * EFFECTS: returns the review at position i in the list of reviews, counting from 0
     *
     */
    public Review getReviewAt(int i) {
        return collection.get(i);
    }

    /*
     * EFFECTS: returns index of review with the review title reviewTitle if it is in the
     *          collection, and returns -1 if it is not in the collection
     *
     */
    public int findReview(String reviewTitle) {
        ArrayList<String> reviewTitles = getReviewTitlesList();
        for (int i = 0; i < reviewTitles.size(); i++) {
            if (reviewTitle.equals(reviewTitles.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
