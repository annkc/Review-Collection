package model;

import java.util.ArrayList;

public class Review {

    private static final int RATING_TOTAL = 10;

    private String mediaTitle;
    private ArrayList<String> mediaContributors;
    private String reviewTitle;
    private ArrayList<String> reviewText;
    private int rating;
    private boolean isRated;


    public Review() {
        mediaTitle = "";
        reviewTitle = "";
        isRated = false;
    }

    public void setMediaTitle(String medTitle) {
        mediaTitle = medTitle;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void addMediaContributor(String mediaContributor) {
        mediaContributors.add(mediaContributor);
    }

    public ArrayList<String> getMediaContributors() {
        return mediaContributors;
    }

    public void setReviewTitle(String revTitle) {
        reviewTitle = revTitle;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void addParagraphToReviewText(String paragraph) {
        reviewText.add(paragraph);
    }

    public ArrayList<String> getReviewText() {
        return reviewText;
    }


    // REQUIRES: r >= 0 AND r <= 10
    public void setRating(int r) {
        rating = r;
        isRated = true;
    }

    public int getRating() {
        return rating;
    }



}
