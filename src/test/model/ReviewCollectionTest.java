package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ReviewCollectionTest {

    private ReviewCollection collection;
    private Review review1 = new Review("The Great Gatsby", "The Great Gatsby left me wondering why I read it.", 5);
    private Review review2 = new Review("Persona 5", "The game I have wanted to play", 9);

    @BeforeEach
    public void runBefore() {
        collection = new ReviewCollection();
    }

    @Test
    public void testAddingAndRemovingReviews() {
        collection.addReview(review2);
        assertEquals(review2, collection.getReviewAt(0));
        assertEquals(1, collection.getReviewTitlesList().size());

        collection.removeReview(review2);
        assertEquals(0, collection.getReviewTitlesList().size());
    }

    @Test
    public void testGetReviewTitlesList() {
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

}
