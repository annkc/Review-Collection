package model;

import java.util.ArrayList;

/*
 * Represents a review of a piece of media, including the name of the media being
 * reviewed, the contributors to the media, the title of the review, the body text
 * of the review, and a rating score out of 10.
 *
 */

public class Review {

    private static final int RATING_TOTAL = 10;     // total which the review rating is out of

    private String mediaTitle;                      // title of the media being reviewed
    private ArrayList<String> mediaContributors;    // names of contributors to the media (eg authors, directors, etc)
    private String reviewTitle;                     // title of the review
    private int rating;                             // rating of the media out of RATING_TOTAL
    private ArrayList<String> reviewText;           // paragraphs that make up body text of the review



    /*
     * REQUIRES: mediaTitle.length() > 0 AND reviewTitle.length() > 0
     * EFFECTS: title of the media being reviewed is set to medTitle;
     * 		    title of the review is set to revTitle; the rating
     *          out of the total is set to rating
     */
    public Review(String medTitle, String revTitle, int rating) {
        mediaTitle = medTitle;
        reviewTitle = revTitle;
        this.rating = rating;
        mediaContributors = new ArrayList<>();
        reviewText = new ArrayList<>();
    }

    /*
     * REQUIRES: medTitle.length() > 0
     * MODIFIES: this
     * EFFECTS: the media's title is set to medTitle
     *
     */
    public void setMediaTitle(String medTitle) {
        mediaTitle = medTitle;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    /*
     * REQUIRES: mediaContributor.length() > 0
     * MODIFIES: this
     * EFFECTS: the name of a contributor, mediaContributor, of the media being reviewed
     *          is added to the list of contributors
     */
    public void addMediaContributor(String mediaContributor) {
        mediaContributors.add(mediaContributor);
    }

    /*
     * REQUIRES: mediaContributor must be an element in the list of contributors to the reviewed media
     * MODIFIES: this
     * EFFECTS: the name of a contributor, mediaContributor, of the media being reviewed
     *          is removed from the list of contributors
     */
    public void removeMediaContributor(String mediaContributor) {
        mediaContributors.remove(mediaContributor);
    }

    public ArrayList<String> getMediaContributors() {
        return mediaContributors;
    }

    /*
     * REQUIRES: revTitle.length() > 0
     * MODIFIES: this
     * EFFECTS: the review title is set to revTitle
     *
     */
    public void setReviewTitle(String revTitle) {
        reviewTitle = revTitle;
    }

    public String getReviewTitle() {
        return reviewTitle;
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

    public ArrayList<String> getReviewText() {
        return reviewText;
    }


    /*
     * REQUIRES: r >= 0 AND r <= 10
     * MODIFIES: this
     * EFFECTS: the rating is set to r
     *
     */
    public void setRating(int r) {
        rating = r;
    }

    public int getRating() {
        return rating;
    }

}
