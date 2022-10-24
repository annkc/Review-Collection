package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(review.getWorkTitle(), jsonObject.get("work title"));
        assertEquals(review.getReviewTitle(), jsonObject.get("review title"));
        assertEquals(review.getRating(), jsonObject.get("rating"));

        assertEquals(review.workCreatorsToJsonArray(), jsonObject.get("work creators"));
        assertEquals(review.reviewTextToJsonArray(), jsonObject.get("review text"));

    }

    @Test
    public void testWorkCreatorsToJsonArray() {
        review.addWorkCreator("F. Scott Fitzgerald");
        JSONArray jsonArray = review.workCreatorsToJsonArray();
        int index = 0;
        for (Object json : jsonArray) {
            JSONObject temp = new JSONObject();
            temp.put("name", review.getWorkCreators().get(index));
            assertEquals(temp, json);
            index++;
        }

    }

    @Test
    public void testReviewTextToJsonArray() {
        review.addParagraphToReviewText("I knew of the Great Gatsby before I had to read it.");
        JSONArray jsonArray = review.reviewTextToJsonArray();
        int index = 0;
        for (Object json : jsonArray) {
            JSONObject temp = new JSONObject();
            temp.put("paragraph", review.getReviewText().get(index));
            assertEquals(temp, json);
            index++;
        }
    }


}