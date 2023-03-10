package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Review

class ReviewTest {
    private Review review;

    @BeforeEach
    public void runBefore() {
        review = new Review("The Great Gatsby", "The Great Gatsby left me wondering why I read it.", 5);
    }

    @Test
    public void testAddingThenClearingWorkCreators() {
        review.addWorkCreator("F. Scott Fitzgerald");
        review.addWorkCreator("Maxwell Perkins");
        assertEquals("F. Scott Fitzgerald", review.getWorkCreators().get(0));
        assertEquals("Maxwell Perkins", review.getWorkCreators().get(1));
        assertEquals(2, review.getWorkCreators().size());

        review.clearWorkCreators();
        assertEquals(0, review.getWorkCreators().size());
    }

    @Test
    public void testAddingThenClearingReviewText() {
        String paragraph1 = "I knew of the Great Gatsby before I had to read it.";
        String paragraph2 = "I can't say that I would have read it if not for school.";
        String paragraph3 = "I can't say I enjoyed i. It wasn't bad, but it wasn't good.";

        review.addParagraphToReviewText(paragraph1);
        review.addParagraphToReviewText(paragraph2);
        review.addParagraphToReviewText(paragraph3);
        assertEquals("I knew of the Great Gatsby before I had to read it.", review.getReviewText().get(0));
        assertEquals("I can't say that I would have read it if not for school.", review.getReviewText().get(1));
        assertEquals("I can't say I enjoyed i. It wasn't bad, but it wasn't good.", review.getReviewText().get(2));

        review.clearReviewText();
        assertEquals(0, review.getReviewText().size());

    }

    @Test
    public void testSetters() {
        review.setReviewTitle("My favourite book");
        review.setRating(10);
        review.setWorkTitle("Watchmen");

        assertEquals("My favourite book", review.getReviewTitle());
        assertEquals(10, review.getRating());
        assertEquals("Watchmen", review.getWorkTitle());

    }

    @Test
    public void toJsonObject() {
        JSONObject jsonObject = review.toJsonObject();
        review.addWorkCreator("F. Scott Fitzgerald");
        review.addParagraphToReviewText("I knew of the Great Gatsby before I had to read it.");

        assertEquals(review.getWorkTitle(), jsonObject.getString("work title"));
        assertEquals(review.getReviewTitle(), jsonObject.getString("review title"));
        assertEquals(review.getRating(), jsonObject.getInt("rating"));

        JSONArray jsonArrayWorkCreators = jsonObject.getJSONArray("work creators");
        for (Object json : jsonArrayWorkCreators) {
            JSONObject jsonObjectWorkCreator = (JSONObject) json;
            assertEquals("F. Scott Fitzgerald", jsonObjectWorkCreator.getString("name"));
        }

        JSONArray jsonArrayReviewText = jsonObject.getJSONArray("review text");
        for (Object json : jsonArrayReviewText) {
            JSONObject jsonObjectParagraph = (JSONObject) json;
            assertEquals("I knew of the Great Gatsby before I had to read it.", jsonObjectParagraph.getString("paragraph"));
        }

    }

}