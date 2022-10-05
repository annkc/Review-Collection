package model;

import java.util.ArrayList;

public class ReviewCollection {
    private ArrayList<Review> collection;


    public ReviewCollection() {
        collection = new ArrayList<>();
    }

    public void addReview(Review r) {
        collection.add(r);
    }

    public void deleteReview(String revTitle) {
        for (int i = 0; i < collection.size(); i++) {
            if (revTitle.equals(collection.get(i).getReviewTitle())) {
                collection.remove(i);
                break;
            }
        }
    }

    public ArrayList<String> getReviewTitlesList() {
        ArrayList<String> titleList =  new ArrayList<>();
        for (int i = 0; i < collection.size(); i++) {
            titleList.add(collection.get(i).getReviewTitle());
        }

        return titleList;
    }
}
