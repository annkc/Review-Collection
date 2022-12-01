package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for ReviewCollection

public class ReviewCollectionTest {

    private ReviewCollection collection;
    private Review review1;
    private Review review2;

    @BeforeEach
    public void runBefore() {
        collection = new ReviewCollection();
        review1 = new Review("The Great Gatsby", "The Great Gatsby left me wondering why I read it.", 5);
        review2 = new Review("Persona 5", "The game I have wanted to play", 9);
    }

    @Test
    public void testAddingThenRemovingAReview() {
        collection.addReview(review2);
        assertEquals(review2, collection.getReviewAt(0));
        assertEquals(1, collection.getReviewTitlesList().size());

        collection.removeReview(review2);
        assertEquals(0, collection.getReviewTitlesList().size());

        ArrayList<Event> events = new ArrayList<>();
        for (Event event : EventLog.getInstance()) {
            events.add(event);
        }

        assertEquals("Review 'Persona 5' added to collection.", events.get(0).getDescription());
        assertEquals("Review 'Persona 5' removed from collection.", events.get(1).getDescription());
    }

    @Test
    public void testGetReviewTitlesList() {
        assertEquals(0, collection.getReviewTitlesList().size());

        collection.addReview(review1);
        collection.addReview(review2);

        assertEquals("The Great Gatsby left me wondering why I read it.", collection.getReviewTitlesList().get(0));
        assertEquals("The game I have wanted to play", collection.getReviewTitlesList().get(1));
        assertEquals(2, collection.getReviewTitlesList().size());
    }

    @Test
    public void testGetReviewAt() {
        collection.addReview(review1);
        collection.addReview(review2);

        assertEquals(review1, collection.getReviewAt(0));
        assertEquals(review2, collection.getReviewAt(1));
        assertEquals(null, collection.getReviewAt(2));

    }


    @Test
    public void testToJsonObject() {
        JSONObject jsonObject = collection.toJsonObject();

        JSONArray listOfReviewsJson = jsonObject.getJSONArray("reviews");
        int index = 0;
        for (Object json : listOfReviewsJson) {
            assertEquals(collection.getReviewAt(index).toJsonObject(), json);
            index++;
        }

    }

}
